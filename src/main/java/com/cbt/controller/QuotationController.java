package com.cbt.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.net.QuotedPrintableCodec;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cbt.entity.AmountUnit;
import com.cbt.entity.BackUser;
import com.cbt.entity.MessageCenter;
import com.cbt.entity.OrderMessage;
import com.cbt.entity.QuotationBean;
import com.cbt.entity.QuotationProcessInfo;
import com.cbt.entity.QuotationProductionBean;
import com.cbt.entity.QuotationProductionPriceBean;
import com.cbt.entity.QuotationRemarkTemplate;
import com.cbt.entity.User;
import com.cbt.page.PageHelper;
import com.cbt.service.AmountUnitService;
import com.cbt.service.BackUserService;
import com.cbt.service.MessageCenterService;
import com.cbt.service.OrderMessageService;
import com.cbt.service.QuotationProductionService;
import com.cbt.service.QuotationService;
import com.cbt.service.UserService;
import com.cbt.util.Base64Encode;
import com.cbt.util.DateFormat;
import com.cbt.util.ImageCompressUtil;
import com.cbt.util.Md5Util;
import com.cbt.util.NbMailAddressUtil;
import com.cbt.util.OperationFileUtil;
import com.cbt.util.SecurityHelper;
import com.cbt.util.SplitPage3;
import com.cbt.util.UploadAndDownloadPathUtil;
import com.cbt.util.WebCookie;

@Controller
public class QuotationController {

	private static final Log LOG = LogFactory.getLog(QuotationController.class);
	private static final int PICTURE = 2;
	private static final int UNREAD_STATUS = 0;
	private static final int READ_STATUS = 1;
	private static final int FACTORY_MESSAGE = 2;
	private static final int CUSTOMER_MESSAGE = 1;
	private static final String QUOTATION_MESSAGE_TITLE = "Quotation Message";
	private static final int MESSAGE_QUOTATION_TYPE = 4;

	@Resource
	private UserService userService;

	@Autowired
	private QuotationService quotationService;

	@Resource
	private BackUserService backUserService;

	@Resource
	private QuotationProductionService quotationProductionService;

	@Resource
	private OrderMessageService orderMessageService;

	@Resource
	private MessageCenterService messageCenterService;

	@Resource
	private AmountUnitService amountUnitService;

	/**
	 * 跳转新建报价页面
	 * 
	 * @author polo 2017年5月23日
	 *
	 */
	@RequestMapping("/toCreateQuotation.do")
	public String toCreateQuotation(HttpServletRequest request) {

		try {
			String userid = request.getParameter("userid");
			String salesId = WebCookie.getUserId(request);
			String factoryId = WebCookie.getFactoryId(request);
			BackUser backUser = backUserService.queryBackUserByUserId(salesId);
			Integer permission = backUser.getPermission();
			if (permission == null || "".equals(permission)) {
				permission = 2;
			}
			List<QuotationRemarkTemplate> tempRemarks = quotationService
					.queryTempRemarkByFactoryId(factoryId);
			List<User> list = null;
			// 检查用户权限如果有管理员权限，查询工厂所有用户数据
			if (permission == 1) {
				list = userService.queryUserBySalesIdAdmin(salesId, factoryId);
			} else {
				list = userService.queryUserBySalesId(salesId, factoryId);
			}

			request.setAttribute("userid", userid);
			request.setAttribute("tempRemarks", tempRemarks);
			request.setAttribute("userInfo", list);
			request.setAttribute("backUser", backUser);

		} catch (Exception e) {
			LOG.error(">>>>>>>>>>>>>>>>>QuotationController_toCreateQuotation_exception--:--"
					+ e);
			e.printStackTrace();
		}

		return "quotation.jsp";
	}

	/**
	 * 查询报价单
	 * 
	 * @author polo
	 * @time 2017.5.22
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryAllQuotation.do")
	public String queryAllQuotation(HttpServletRequest request) {
		String page_str = request.getParameter("page");
		String user = request.getParameter("user");
		String begin = request.getParameter("beginDate");
		String end = request.getParameter("endDate");
		String factoryId = WebCookie.getFactoryId(request);
		if (factoryId == null || "".equals(factoryId)) {
			throw new RuntimeException("未获取到工厂ID");
		}
		Integer page = 1;
		try {
			if (page_str != null && page_str.trim() != null) {
				page = Integer.parseInt(page_str);
			}
			if (page < 1) {
				page = 1;
			}
			// 权限控制如果permission是1则显示全部;
			String salesId = WebCookie.getUserId(request);
			BackUser backUser = backUserService.queryBackUserByUserId(salesId);
			Integer permission = backUser.getPermission();
			List<QuotationBean> list = null;
			Integer count = 0;
			List<Integer> unReadMessages = new ArrayList<Integer>();
			// 显示全部（管理员查询）
			if (permission == 1) {
				list = quotationService.queryForListAdmin(user, begin, end,
						(page - 1) * 10, factoryId);
				count = quotationService.queryCountForPageAdmin(user, begin,
						end, factoryId);
			} else {
				// 非管理员
				list = quotationService.queryForList(user, begin, end,
						(page - 1) * 10, backUser.getUserName(), factoryId);
				count = quotationService.queryCountForPage(user, begin, end,
						backUser.getUserName(), factoryId);
			}
			for (QuotationBean quotationBean : list) {
				int unReadMessage = messageCenterService
						.totalUnReadQuotationMessage(quotationBean.getId());
				unReadMessages.add(unReadMessage);
				List<QuotationProductionBean> products = quotationProductionService
						.queryByQuotationInfoId(quotationBean.getId());
				String productNames = "";
				if (!(products == null || products.size() == 0)) {
					int tl = products.size();
					if (tl != 0) {
						for (int i = 0; i < tl; i++) {
							/*
							 * 去除时间节点，获取图纸名
							 */
							String dt = products.get(i).getProductName();
							if (!(dt == null || "".equals(dt.trim()))) {
								// 获取图纸名显示、只显示两个，超过后以...显示
								if (i <= 1) {
									productNames += dt + ",";
								} else if (i == 2) {
									productNames = productNames.substring(0,
											productNames.length() - 1);
									productNames = productNames + "...";
								}

							}

						}
						if (productNames.endsWith(",")) {
							productNames = productNames.substring(0,
									productNames.length() - 1);
						}
						quotationBean.setProductNames(productNames);
					}

				}
			}
			SplitPage3.buildPager(request, count, 10, page);

			request.setAttribute("unReadMessages", unReadMessages);
			request.setAttribute("quotationInfos", list);

		} catch (Exception e) {
			LOG.error(">>>>>>>>>>>>>>>>>QuotationController_queryAllQuotation_exception--:--"
					+ e);
			e.printStackTrace();
		}
		return "quotationList.jsp";
	}

	/**
	 * 获取货币单元
	 * 
	 * @author tb
	 * @time 2017.3.30
	 */
	@RequestMapping("/queryCurrencyList.do")
	@ResponseBody
	public PageHelper queryCurrencyList() {
		PageHelper result = new PageHelper();
		result.setOk(false);
		try {
			List<AmountUnit> amountUnitList = quotationService
					.queryCurrencyList();
			result.setObj(amountUnitList);
			result.setOk(true);
		} catch (Exception e) {
			LOG.error(">>>>>>>>>>>>>>>>>QuotationController_queryCurrencyList_exception--:--"
					+ e);
		}
		return result;
	}

	/**
	 * 保存模板信息
	 * 
	 * @author polo 2017年5月24日
	 *
	 */
	@ResponseBody
	@RequestMapping("/saveTempRemark.do")
	public JsonResult<QuotationRemarkTemplate> saveTempRemark(
			HttpServletRequest request, HttpServletResponse response) {

		QuotationRemarkTemplate tempRemark = new QuotationRemarkTemplate();
		try {
			String factoryId = WebCookie.getFactoryId(request);
			if (factoryId == null || "".equals(factoryId)) {
				throw new RuntimeException("未获取到工厂ID");
			}
			String remark = request.getParameter("remark");
			Integer textOrPicture = null;
			StringUtils.isBlank(request.getParameter("textOrPicture"));
			textOrPicture = Integer.parseInt(request
					.getParameter("textOrPicture"));
			tempRemark.setFactoryId(factoryId);
			tempRemark.setRemark(remark);
			tempRemark.setTextOrPicture(textOrPicture);

			int remarkIndex = quotationService.queryMaxRemarkIndex();
			tempRemark.setRemarkIndex(remarkIndex);

			quotationService.insertTempRemark(tempRemark);

			return new JsonResult<QuotationRemarkTemplate>(tempRemark);
		} catch (NumberFormatException e) {
			LOG.error(">>>>>>>>>>>>>>>>>QuotationController_saveTempRemark_exception--:--"
					+ e);
			e.printStackTrace();
			return new JsonResult<QuotationRemarkTemplate>(1, "保存失败");
		}

	}

	/**
	 * 删除模板
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/deleteTempById.do")
	public JsonResult<List<QuotationRemarkTemplate>> deleteTempById(
			HttpServletRequest request, HttpServletResponse response) {

		try {
			StringUtils.isBlank(request.getParameter("tempRemarkId"));
			Integer tempRemarkId = Integer.parseInt(request
					.getParameter("tempRemarkId"));
			quotationService.deleteTempRemarkById(tempRemarkId);
			String factoryId = WebCookie.getFactoryId(request);
			List<QuotationRemarkTemplate> remarks = quotationService
					.queryTempRemarkByFactoryId(factoryId);
			return new JsonResult<List<QuotationRemarkTemplate>>(remarks);

		} catch (NumberFormatException e) {
			LOG.error(">>>>>>>>>>>>>>>>>QuotationController_deleteTempById_exception--:--"
					+ e);
			e.printStackTrace();
			return new JsonResult<List<QuotationRemarkTemplate>>(1, "删除失败");
		}

	}

	/**
	 * 上传图片信息（图片模板）
	 * 
	 * @author polo 2017年5月25日
	 * @throws Exception
	 *
	 */
	@ResponseBody
	@RequestMapping("/saveTemplatePicture.do")
	public JsonResult<Map<String, Object>> saveTemplatePicture(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		QuotationRemarkTemplate tempRemark = new QuotationRemarkTemplate();
		try {
			String factoryId = WebCookie.getFactoryId(request);
			if (factoryId == null || "".equals(factoryId)) {
				throw new RuntimeException("未获取到工厂ID");
			}

			tempRemark.setTextOrPicture(PICTURE);

			StringUtils.isBlank(request.getParameter("pictureIndex"));
			Integer pictureIndex = Integer.parseInt(request
					.getParameter("pictureIndex"));
			String path = UploadAndDownloadPathUtil.getRemakPicturePath()
					+ factoryId;
			File file = new File(path);
			if (!file.exists() && !file.isDirectory()) {
				file.mkdir();
			}
			// 调用保存文件的帮助类进行保存文件(文件上传，form表单提交)
			Map<String, String> multiFileUpload = OperationFileUtil
					.multiFileUpload1(request, path + File.separator);
			String changePhotoName = null;
			String changePhotoNameCompress = null;
			String drawingName = request.getParameter("drawingName");
			if (!(drawingName == null || "".equals(drawingName))) {
				changePhotoName = multiFileUpload.get(drawingName);
				changePhotoNameCompress = OperationFileUtil
						.changeFilenameZip(changePhotoName);
				ImageCompressUtil.saveMinPhoto(path + File.separator
						+ changePhotoName, path + File.separator
						+ changePhotoNameCompress, 139, 0.9d);
			}
			if (pictureIndex == 1) {
				tempRemark.setPicturePath1(UploadAndDownloadPathUtil
						.getStaticRemakPicturePath()
						+ factoryId
						+ "/"
						+ changePhotoName);
				tempRemark.setPicturePath1Compress(UploadAndDownloadPathUtil
						.getStaticRemakPicturePath()
						+ factoryId
						+ "/"
						+ changePhotoNameCompress);
			} else if (pictureIndex == 2) {
				tempRemark.setPicturePath2(UploadAndDownloadPathUtil
						.getStaticRemakPicturePath()
						+ factoryId
						+ "/"
						+ changePhotoName);
				tempRemark.setPicturePath2Compress(UploadAndDownloadPathUtil
						.getStaticRemakPicturePath()
						+ factoryId
						+ "/"
						+ changePhotoNameCompress);
			} else if (pictureIndex == 3) {
				tempRemark.setPicturePath3(UploadAndDownloadPathUtil
						.getStaticRemakPicturePath()
						+ factoryId
						+ "/"
						+ changePhotoName);
				tempRemark.setPicturePath3Compress(UploadAndDownloadPathUtil
						.getStaticRemakPicturePath()
						+ factoryId
						+ "/"
						+ changePhotoNameCompress);
			} else if (pictureIndex == 4) {
				tempRemark.setPicturePath4(UploadAndDownloadPathUtil
						.getStaticRemakPicturePath()
						+ factoryId
						+ "/"
						+ changePhotoName);
				tempRemark.setPicturePath4Compress(UploadAndDownloadPathUtil
						.getStaticRemakPicturePath()
						+ factoryId
						+ "/"
						+ changePhotoNameCompress);
			}

			Integer templateRemarkId = null;
			Map<String, Object> map = new HashMap<String, Object>();
			if (!(request.getParameter("templateRemarkId") == null || ""
					.equals(request.getParameter("templateRemarkId")))) {
				templateRemarkId = Integer.parseInt(request
						.getParameter("templateRemarkId"));
				QuotationRemarkTemplate remark = quotationService
						.queryByRemarkId(templateRemarkId);
				if (!(remark == null || "".equals(remark))) {
					if (pictureIndex == 1) {
						remark.setPicturePath1(UploadAndDownloadPathUtil
								.getStaticRemakPicturePath()
								+ factoryId
								+ "/"
								+ changePhotoName);
						remark.setPicturePath1Compress(UploadAndDownloadPathUtil
								.getStaticRemakPicturePath()
								+ factoryId
								+ "/"
								+ changePhotoNameCompress);
					} else if (pictureIndex == 2) {
						remark.setPicturePath2(UploadAndDownloadPathUtil
								.getStaticRemakPicturePath()
								+ factoryId
								+ "/"
								+ changePhotoName);
						remark.setPicturePath2Compress(UploadAndDownloadPathUtil
								.getStaticRemakPicturePath()
								+ factoryId
								+ "/"
								+ changePhotoNameCompress);
					} else if (pictureIndex == 3) {
						remark.setPicturePath3(UploadAndDownloadPathUtil
								.getStaticRemakPicturePath()
								+ factoryId
								+ "/"
								+ changePhotoName);
						remark.setPicturePath3Compress(UploadAndDownloadPathUtil
								.getStaticRemakPicturePath()
								+ factoryId
								+ "/"
								+ changePhotoNameCompress);
					} else if (pictureIndex == 4) {
						remark.setPicturePath4(UploadAndDownloadPathUtil
								.getStaticRemakPicturePath()
								+ factoryId
								+ "/"
								+ changePhotoName);
						remark.setPicturePath4Compress(UploadAndDownloadPathUtil
								.getStaticRemakPicturePath()
								+ factoryId
								+ "/"
								+ changePhotoNameCompress);
					}
					quotationService.updateTempRemark(remark);
					map.put("picturePath",
							UploadAndDownloadPathUtil
									.getStaticRemakPicturePath()
									+ factoryId
									+ "/" + changePhotoNameCompress);
					map.put("templateRemarkId", templateRemarkId);
				}
				return new JsonResult<Map<String, Object>>(map);
			} else {
				int remarkIndex = quotationService.queryMaxRemarkIndex();
				tempRemark.setRemarkIndex(remarkIndex);
				quotationService.insertTempRemark(tempRemark);
				templateRemarkId = tempRemark.getId();
				map.put("picturePath",
						UploadAndDownloadPathUtil.getStaticRemakPicturePath()
								+ factoryId + "/" + changePhotoNameCompress);
				map.put("templateRemarkId", templateRemarkId);
				return new JsonResult<Map<String, Object>>(map);
			}

		} catch (NumberFormatException e) {
			LOG.error(">>>>>>>>>>>>>>>>>QuotationController_saveTemplatePicture_exception--:--"
					+ e);
			e.printStackTrace();
			return new JsonResult<Map<String, Object>>(1, "保存失败");
		}

	}

	/**
	 * 保存模板信息(图片模板保存)
	 * 
	 * @author polo 2017年5月25日
	 * @throws IOException
	 * @throws IllegalStateException
	 *
	 */
	@ResponseBody
	@RequestMapping("/saveTemplatePictures.do")
	public JsonResult<QuotationRemarkTemplate> saveTemplatePictures(
			HttpServletRequest request, HttpServletResponse response) {

		try {
			String factoryId = WebCookie.getFactoryId(request);
			if (factoryId == null || "".equals(factoryId)) {
				throw new RuntimeException("未获取到工厂ID");
			}
			StringUtils.isBlank(request.getParameter("templateRemarkId"));
			Integer templateRemarkId = Integer.parseInt(request
					.getParameter("templateRemarkId"));
			QuotationRemarkTemplate tempRemark = quotationService
					.queryByRemarkId(templateRemarkId);
			String remark = "";
			if (!(tempRemark.getPicturePath1() == null || "".equals(tempRemark
					.getPicturePath1()))) {
				remark += "<img class='z-temp-img' src="
						+ tempRemark.getPicturePath1Compress() + ">";
			}
			if (!(tempRemark.getPicturePath2() == null || "".equals(tempRemark
					.getPicturePath2()))) {
				remark += "<img class='z-temp-img' src="
						+ tempRemark.getPicturePath2Compress() + ">";
			}
			if (!(tempRemark.getPicturePath3() == null || "".equals(tempRemark
					.getPicturePath3()))) {
				remark += "<img class='z-temp-img' src="
						+ tempRemark.getPicturePath3Compress() + ">";
			}
			if (!(tempRemark.getPicturePath4() == null || "".equals(tempRemark
					.getPicturePath4()))) {
				remark += "<img class='z-temp-img' src="
						+ tempRemark.getPicturePath4Compress() + ">";
			}
			tempRemark.setRemark(remark);
			tempRemark.setFactoryId(factoryId);
			quotationService.updateTempRemark(tempRemark);

			return new JsonResult<QuotationRemarkTemplate>(tempRemark);
		} catch (NumberFormatException e) {
			LOG.error(">>>>>>>>>>>>>>>>>QuotationController_saveTemplatePictures_exception--:--"
					+ e);
			e.printStackTrace();
			return new JsonResult<QuotationRemarkTemplate>(1, "生成失败");
		}

	}

	/**
	 * 当上传图纸后未保存，删除图片模板
	 * 
	 * @author polo 2017年5月25日
	 * @throws IOException
	 * @throws IllegalStateException
	 *
	 */
	@RequestMapping("/deleteTemplatePictures.do")
	public void deleteTemplatePictures(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			if (!(request.getParameter("templateRemarkId") == null || ""
					.equals(request.getParameter("templateRemarkId")))) {
				Integer templateRemarkId = Integer.parseInt(request
						.getParameter("templateRemarkId"));
				quotationService.deleteTempRemarkById(templateRemarkId);
			}
		} catch (NumberFormatException e) {
			LOG.error(">>>>>>>>>>>>>>>>>QuotationController_deleteTemplatePictures_exception--:--"
					+ e);
			e.printStackTrace();
		}
	}

	/**
	 * 根据ID查询
	 * 
	 * @author polo 2017年5月25日
	 *
	 */
	@ResponseBody
	@RequestMapping("/queryByTempRemarkId.do")
	public JsonResult<QuotationRemarkTemplate> queryByTempRemarkId(
			HttpServletRequest request, HttpServletResponse response) {

		try {
			QuotationRemarkTemplate tempRemark = new QuotationRemarkTemplate();
			if (!(request.getParameter("tempRemarkId") == null || ""
					.equals(request.getParameter("tempRemarkId")))) {
				Integer tempRemarkId = Integer.parseInt(request
						.getParameter("tempRemarkId"));
				tempRemark = quotationService.queryByRemarkId(tempRemarkId);
			}
			return new JsonResult<QuotationRemarkTemplate>(tempRemark);
		} catch (NumberFormatException e) {
			LOG.error(">>>>>>>>>>>>>>>>>QuotationController_queryByTempRemarkId_exception--:--"
					+ e);
			e.printStackTrace();
			return new JsonResult<QuotationRemarkTemplate>(1, "生成失败");
		}

	}

	/**
	 * 保存报价单
	 * 
	 * @author polo 2017年5月25日
	 *
	 */
	@ResponseBody
	@RequestMapping("/saveQuotation.do")
	public JsonResult<Integer> saveQuotation(HttpServletRequest request,
			HttpServletResponse response) {

		try {
			QuotationBean quotation = new QuotationBean();
			String factoryId = WebCookie.getFactoryId(request);
			if (factoryId == null || "".equals(factoryId)) {
				throw new RuntimeException("未获取到工厂ID");
			}
			String projectId = request.getParameter("projectId");
			String quotationSubject = request.getParameter("projectSubject");
			String quotationDate = request.getParameter("quotationDate");
			String currency = request.getParameter("currency");
			String customerName = request.getParameter("customerName");
			String incoTerm = request.getParameter("incoTerm");
			String quoter = request.getParameter("quoter");
			String email = request.getParameter("email");
			String tel = request.getParameter("tel");
			String tempRemark = request.getParameter("tempRemark");
			String remarkImg = request.getParameter("remarkImg");
			String imgNames = request.getParameter("imgNames");
			String productList = request.getParameter("productList");
			String customerId = request.getParameter("customerId");
			Integer quotationValidity = null;
			if (!(request.getParameter("quotationValidity") == null || ""
					.equals(request.getParameter("quotationValidity")))) {
				quotationValidity = Integer.parseInt(request
						.getParameter("quotationValidity"));
			}

			quotation.setProjectId(projectId);
			quotation.setUserId(customerId);
			quotation.setCurrency(currency);
			quotation.setCustomerName(customerName);
			quotation.setFactoryId(factoryId);
			quotation.setIncoTerm(incoTerm);
			quotation.setQuotationDate(quotationDate);
			quotation.setQuotationSubject(quotationSubject);
			quotation.setQuotationValidity(quotationValidity);
			quotation.setQuoter(quoter);
			quotation.setQuoterEmail(email);
			quotation.setQuoterTel(tel);
			quotation.setRemark(tempRemark);
			quotation.setRemarkImg(remarkImg);
			quotation.setCreateTime(DateFormat.format());
			quotation.setUpdateTime(DateFormat.format());
			quotation.setImgNames(imgNames);
			quotation.setReadStatus(UNREAD_STATUS);

			quotationService.insertQuotation(request, quotation, productList);
			return new JsonResult<Integer>(0, "success", quotation.getId());
		} catch (NumberFormatException e) {
			e.printStackTrace();
			LOG.error(">>>>>>>>>>>>>>>>>QuotationController_saveQuotation_exception--:--"
					+ e);
			return new JsonResult<Integer>(1, "保存失败");
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(">>>>>>>>>>>>>>>>>QuotationController_saveQuotation_exception--:--"
					+ e);
			return new JsonResult<Integer>(1, "保存失败");
		}

	}

	/**
	 * 删除报价单
	 * 
	 * @author polo 2017年5月26日
	 *
	 */
	@ResponseBody
	@RequestMapping("/deleteQuotation.do")
	public JsonResult<String> deleteQuotation(HttpServletRequest request,
			HttpServletResponse response) {

		try {
			if (StringUtils.isBlank(request.getParameter("quotationInfoId"))) {
				throw new RuntimeException("未获取到报价单信息");
			}
			Integer quotationInfoId = Integer.parseInt(request
					.getParameter("quotationInfoId"));
			quotationService.deleteQuotation(quotationInfoId);

			return new JsonResult<String>(0, "删除成功");
		} catch (NumberFormatException e) {
			LOG.error(">>>>>>>>>>>>>>>>>QuotationController_deleteQuotation_exception--:--"
					+ e);
			e.printStackTrace();
			return new JsonResult<String>(1, "保存失败");
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(">>>>>>>>>>>>>>>>>QuotationController_deleteQuotation_exception--:--"
					+ e);
			return new JsonResult<String>(1, "保存失败");
		}
	}

	/**
	 * 跳转修改报价单界面
	 * 
	 * @author polo 2017年5月26日
	 *
	 */
	@RequestMapping("/toModifyQuotation.do")
	public String toModifyQuotation(HttpServletRequest request,
			HttpServletResponse response) {

		try {
			String salesId = WebCookie.getUserId(request);
			String userInfo = request.getParameter("userInfo");
			if (StringUtils.isNotBlank(userInfo)) {
				// 保存cookie token
				String now = String.valueOf(System.currentTimeMillis());
				String token = Md5Util.encoder(now);
				Cookie tokenCookie = new Cookie("token1", now + "|" + token);
				tokenCookie.setPath("/");
				response.addCookie(tokenCookie);

				String decrypt = Base64Encode.getFromBase64(userInfo);
				String[] strs = decrypt.split("&");
				salesId = strs[0];
				Integer permission = Integer.parseInt(strs[2]);
				if (permission == null) {
					permission = 0;
				}

				// 客户登录信息存放到session
				HttpSession session = request.getSession();
				session.setAttribute("backuser",
						SecurityHelper.encrypt("backuser", decrypt));
				session.setMaxInactiveInterval(60 * 60 * 24 * 365);

				session.setAttribute("permission", permission);
				session.setMaxInactiveInterval(60 * 60 * 24 * 365);

				// 存放cookie
				Cookie userCookie = new Cookie("backuser",
						SecurityHelper.encrypt("backuser", decrypt));
				userCookie.setPath("/");
				userCookie.setMaxAge(60 * 60 * 24 * 365);
				response.addCookie(userCookie);
				// 存放用户权限
				Cookie pCookie = new Cookie("permission", permission.toString());
				userCookie.setPath("/");
				userCookie.setMaxAge(60 * 60 * 24 * 365);
				response.addCookie(pCookie);

			}
			String factoryId = WebCookie.getFactoryId(request);
			BackUser backUser = backUserService.queryBackUserByUserId(salesId);
			if (backUser == null || "".equals(backUser)) {
				return "login.jsp";
			}
			Integer permission = backUser.getPermission();
			if (permission == null || "".equals(permission)) {
				permission = 2;
			}
			List<QuotationRemarkTemplate> tempRemarks = quotationService
					.queryTempRemarkByFactoryId(factoryId);
			List<User> list = null;
			// 检查用户权限如果有管理员权限，查询工厂所有用户数据
			if (permission == 1) {
				list = userService.queryUserBySalesIdAdmin(salesId, factoryId);
			} else {
				list = userService.queryUserBySalesId(salesId, factoryId);
			}

			StringUtils.isBlank(request.getParameter("quotationInfoId"));
			String id = Base64Encode.getFromBase64(request
					.getParameter("quotationInfoId"));
			Integer quotationInfoId = Integer.parseInt(id);
			QuotationBean quotationBean = quotationService
					.queryById(quotationInfoId);
			if (quotationBean == null || "".equals(quotationBean)) {
				return "quotation_edit.jsp";
			}
			List<QuotationProductionBean> quotationProductionBeans = quotationProductionService
					.queryByQuotationInfoId(quotationInfoId);
			List<List<QuotationProductionPriceBean>> productionPrices = new ArrayList<List<QuotationProductionPriceBean>>();
			List<List<QuotationProcessInfo>> processInfos = new ArrayList<List<QuotationProcessInfo>>();
			for (QuotationProductionBean quotationProductionBean : quotationProductionBeans) {
				List<QuotationProductionPriceBean> productionPrice = quotationProductionService
						.queryByProductionId(quotationProductionBean.getId());
				if (productionPrice != null && productionPrice.size() != 0) {
					List<QuotationProcessInfo> processInfo = quotationProductionService
							.queryProcessByProductionId(productionPrice.get(0)
									.getId());
					processInfos.add(processInfo);
				}
				productionPrices.add(productionPrice);
			}
			List<AmountUnit> amountUnitList = quotationService
					.queryCurrencyList();
			// List<OrderMessage> messages =
			// orderMessageService.queryByQuotationInfoId(quotationInfoId);
			// for (OrderMessage orderMessage : messages) {
			// String email = orderMessage.getLoginEmail();
			// email =
			// email.replaceAll("(\\w?)(\\w+)(\\w)(@\\w+\\.[a-z]+(\\.[a-z]+)?)",
			// "$1****$3$4");
			// orderMessage.setLoginEmail(email);
			// if(orderMessage.getTargetPriceReply() == 2){
			// String attachmentPath = orderMessage.getAttachmentPath();
			// if(!(attachmentPath == null || "".equals(attachmentPath))){
			// File tempFile =new File(attachmentPath.trim());
			// String name = tempFile.getName();
			// String[] split = name.split("&");
			// if(split.length != 0){
			// String fileName =
			// split[0]+"."+FilenameUtils.getExtension(attachmentPath);
			// orderMessage.setAttachmentPath(fileName);
			// }
			// }
			// }
			//
			// }
			Double exchangeRate = 0.0;
			String currency = quotationBean.getCurrency();
			if (!(currency == null || "".equals(currency))) {
				if ("USD".equals(currency)) {
					exchangeRate = 1.0;
				} else if ("CNY".equals(currency)) {
					exchangeRate = quotationBean.getExchangeRateCNY();
				} else if ("EUR".equals(currency)) {
					exchangeRate = quotationBean.getExchangeRateEUR();
				} else if ("GBP".equals(currency)) {
					exchangeRate = quotationBean.getExchangeRateGBP();
				}
			}
			request.setAttribute("exchangeRate", exchangeRate);
			request.setAttribute("amountUnitList", amountUnitList);
			request.setAttribute("tempRemarks", tempRemarks);
			request.setAttribute("userInfo", list);
			request.setAttribute("quotationBean", quotationBean);
			request.setAttribute("quotationProductionBeans",
					quotationProductionBeans);
			request.setAttribute("productionPrices", productionPrices);
			request.setAttribute("processInfos", processInfos);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			LOG.error(">>>>>>>>>>>>>>>>>QuotationController_toModifyQuotation_exception--:--"
					+ e);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "quotation_edit.jsp";
	}

	/**
	 * 修改报价单
	 * 
	 * @author polo 2017年5月25日
	 *
	 */
	@ResponseBody
	@RequestMapping("/modifyQuotation.do")
	public JsonResult<Integer> modifyQuotation(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, String> map = null;

		try {

			if (StringUtils.isBlank(request.getParameter("quotationInfoId"))) {
				throw new RuntimeException("未获取到报价单信息");
			}

			String pdfForm = request.getParameter("pdfForm");
			String[] pdfMessage = pdfForm.split("&");

			if (pdfMessage != null && pdfMessage.length > 0) {
				map = new HashMap<String, String>();
				for (int k = 0; k < pdfMessage.length; k++) {
					String[] temp = pdfMessage[k].split("=");
					map.put(temp[0], temp[1]);
				}
			}
			
			

			Integer quotationInfoId = Integer.parseInt(request
					.getParameter("quotationInfoId"));
			QuotationBean quotation = quotationService
					.queryById(quotationInfoId);
			String factoryId = WebCookie.getFactoryId(request);
			if (factoryId == null || "".equals(factoryId)) {
				throw new RuntimeException("未获取到工厂ID");
			}
			String quotationSubject = request.getParameter("projectSubject");
			String quotationDate = request.getParameter("quotationDate");
			String currency = request.getParameter("currency");
			String incoTerm = request.getParameter("incoTerm");
			String tel = request.getParameter("tel");
			String tempRemark = request.getParameter("tempRemark");
			String remarkImg = request.getParameter("remarkImg");
			String imgNames = request.getParameter("imgNames");
			String productList = request.getParameter("productList");
			String type = request.getParameter("type");
			Integer weightStatus = 0;
			if (StringUtils.isNotBlank(request.getParameter("weightStatus"))) {
				weightStatus = Integer.parseInt(request
						.getParameter("weightStatus"));
			}
			Integer processStatus = 0;
			if (StringUtils.isNotBlank(request.getParameter("processStatus"))) {
				processStatus = Integer.parseInt(request
						.getParameter("processStatus"));
			}
			Integer quotationValidity = null;
			if (!(request.getParameter("quotationValidity") == null || ""
					.equals(request.getParameter("quotationValidity")))) {
				quotationValidity = Integer.parseInt(request
						.getParameter("quotationValidity"));
			}
			quotation.setCurrency(currency);
			quotation.setFactoryId(factoryId);
			quotation.setIncoTerm(incoTerm);
			quotation.setQuotationDate(quotationDate);
			quotation.setQuotationSubject(quotationSubject);
			quotation.setQuotationValidity(quotationValidity);
			quotation.setQuoterTel(tel);
			quotation.setRemark(tempRemark);
			quotation.setRemarkImg(remarkImg);
			quotation.setCreateTime(DateFormat.format());
			quotation.setUpdateTime(DateFormat.format());
			quotation.setImgNames(imgNames);
			quotation.setWeightStatus(weightStatus);
			quotation.setProcessStatus(processStatus);
			
			quotation.setPdfType(Integer.parseInt(map.get("processCode")));

			quotation.setCheckTab(map);
			quotationService.updateQuotationAndProduct(request, quotation,
					productList,type);
			return new JsonResult<Integer>(0, "success", quotationInfoId);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			LOG.error(">>>>>>>>>>>>>>>>>QuotationController_modifyQuotation_exception--:--"
					+ e);
			return new JsonResult<Integer>(1, "保存失败");
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(">>>>>>>>>>>>>>>>>QuotationController_modifyQuotation_exception--:--"
					+ e);
			return new JsonResult<Integer>(1, "保存失败");
		}

	}

	/**
	 * 更新产品价格
	 */
	@ResponseBody
	@RequestMapping("/updateQuotationPrice.do")
	public JsonResult<List<QuotationProductionBean>> updateQuotationPrice(
			HttpServletRequest request, HttpServletResponse response) {

		try {
			if (StringUtils.isBlank(request.getParameter("quotationInfoId"))) {
				throw new RuntimeException("未获取到报价单信息");
			}
			Integer quotationInfoId = Integer.parseInt(request
					.getParameter("quotationInfoId"));

			if (StringUtils.isBlank(request.getParameter("productId"))) {
				throw new RuntimeException("未获取到产品信息");
			}
			Integer productId = Integer.parseInt(request
					.getParameter("productId"));
			QuotationProductionBean quotationProductionBean = quotationProductionService
					.queryProductionById(productId);

			if (!(request.getParameter("productName") == null || ""
					.equals(request.getParameter("productName")))) {
				quotationProductionBean.setProductName(request
						.getParameter("productName"));
			}
			if (!(request.getParameter("moldPrice") == null || ""
					.equals(request.getParameter("moldPrice")))) {
				quotationProductionBean.setMoldPrice(Double.parseDouble(request
						.getParameter("moldPrice")));
			}
			if (!(request.getParameter("moldProfitRate") == null || ""
					.equals(request.getParameter("moldProfitRate")))) {
				quotationProductionBean.setMoldProfitRate(Double
						.parseDouble(request.getParameter("moldProfitRate")));
			}
			if (!(request.getParameter("material") == null || "".equals(request
					.getParameter("material")))) {
				quotationProductionBean.setMaterial(request
						.getParameter("material"));
			}
			if (!(request.getParameter("remark") == null || "".equals(request
					.getParameter("remark")))) {
				quotationProductionBean.setProductNotes(request
						.getParameter("remark"));
			}
			String priceList = request.getParameter("priceList");
			List<QuotationProductionBean> list = quotationProductionService
					.updateQuotationProduction(quotationInfoId, priceList,
							quotationProductionBean);

			return new JsonResult<List<QuotationProductionBean>>(list);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			LOG.error(">>>>>>>>>>>>>>>>>QuotationController_updateQuotationPrice_exception--:--"
					+ e);
			return new JsonResult<List<QuotationProductionBean>>(1, "未查询到数据");
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(">>>>>>>>>>>>>>>>>QuotationController_updateQuotationPrice_exception--:--"
					+ e);
			return new JsonResult<List<QuotationProductionBean>>(1, "更新失败");
		}

	}

	/**
	 * 调整利润率时计算价格
	 */
	@ResponseBody
	@RequestMapping("/queryQuotationUnitPrice.do")
	public JsonResult<Map<String,Double>> queryQuotationUnitPrice(
			HttpServletRequest request, HttpServletResponse response) {

		try {
			if (StringUtils.isBlank(request.getParameter("productId"))) {
				throw new RuntimeException("未获取到产品信息");
			}
			Integer productId = Integer.parseInt(request.getParameter("productId"));
//			List<QuotationProductionPriceBean> priceBeans = quotationProductionService.queryByProductionId(productId);
			QuotationProductionBean quotationProductionBean = quotationProductionService.queryProductionById(productId);
			QuotationBean quotationBean = quotationService.queryById(quotationProductionBean.getQuotationInfoId());
			Double moldPrice = 0.0;
			if (!(request.getParameter("moldPrice") == null || ""
					.equals(request.getParameter("moldPrice")))) {
				moldPrice = Double.parseDouble(request
						.getParameter("moldPrice"));
			}

			Double productProfitRate = 0.0;
			if (!(request.getParameter("productProfitRate") == null || ""
					.equals(request.getParameter("productProfitRate")))) {
				productProfitRate = Double.parseDouble(request
						.getParameter("productProfitRate"));
			}
			Integer quantity = 1;
			if (!(request.getParameter("quantity") == null || "".equals(request.getParameter("quantity")))) {
				quantity = Integer.parseInt(request.getParameter("quantity"));
			}
			Double moldFactoryPrice = quotationProductionBean.getMoldFactoryPrice();
			Double factoryPrice = quotationProductionBean.getFactoryPrice();
			Double unitPrice = factoryPrice * (1 + productProfitRate/100);
			
			Double exchangeRate = 1.0;
			String currentCurrency = quotationBean.getCurrency();
			switch (currentCurrency) {
			case "USD":
				exchangeRate = 1.0;
				break;
			case "CNY":
				exchangeRate = quotationBean.getExchangeRateCNY();
				break;
			case "EUR":
				exchangeRate = quotationBean.getExchangeRateEUR();
				break;
			case "GBP":
				exchangeRate = quotationBean.getExchangeRateGBP();
				break;
			}
//			moldPrice = new BigDecimal(moldPrice).divide(new BigDecimal(exchangeRate), 4 , BigDecimal.ROUND_UP).doubleValue();
			
			Double changePrice = (moldPrice + unitPrice * quantity) - (moldFactoryPrice + factoryPrice * quantity);
			unitPrice = new BigDecimal(unitPrice).setScale(3,BigDecimal.ROUND_HALF_UP).doubleValue();
            Double totalRate = changePrice/(moldFactoryPrice + factoryPrice * quantity);
            totalRate = new BigDecimal(totalRate).multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue(); 			
			
            
            Map<String,Double> map = new HashMap<String, Double>();
            map.put("totalRate", totalRate);
            map.put("unitPrice", unitPrice);
            
            return new JsonResult<Map<String,Double>>(map);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			LOG.error(">>>>>>>>>>>>>>>>>QuotationController_queryQuotationUnitPrice_exception--:--"
					+ e);
			return new JsonResult<Map<String,Double>>(1, "未查询到数据");
		}

	}

	/**
	 * 调整模具价格
	 */
	@ResponseBody
	@RequestMapping("/queryMoldPrice.do")
	public JsonResult<Map<String, Object>> queryMoldPrice(
			HttpServletRequest request, HttpServletResponse response) {

		try {
			if (StringUtils.isBlank(request.getParameter("productId"))) {
				throw new RuntimeException("未获取到产品信息");
			}
			Integer productId = Integer.parseInt(request
					.getParameter("productId"));
			QuotationProductionBean quotationProductionBean = quotationProductionService
					.queryProductionById(productId);
			
            QuotationBean quotationBean = quotationService.queryById(quotationProductionBean.getQuotationInfoId());
			
			Double moldProfitRate = 0.0;
			if (!(request.getParameter("moldProfitRate") == null || ""
					.equals(request.getParameter("moldProfitRate")))) {
				moldProfitRate = Double.parseDouble(request
						.getParameter("moldProfitRate"));
			}

			Double moldFactoryPrice = quotationProductionBean
					.getMoldFactoryPrice();
			Double productFactoryPrice = quotationProductionBean
					.getFactoryPrice();
			Double moldPrice = new BigDecimal(moldFactoryPrice)
					.multiply(new BigDecimal(1 + moldProfitRate / 100))
					.setScale(5, BigDecimal.ROUND_HALF_UP).doubleValue();
            
			Double exchangeRate = 1.0;
			String currentCurrency = quotationBean.getCurrency();
			switch (currentCurrency) {
			case "USD":
				exchangeRate = 1.0;
				break;
			case "CNY":
				exchangeRate = quotationBean.getExchangeRateCNY();
				break;
			case "EUR":
				exchangeRate = quotationBean.getExchangeRateEUR();
				break;
			case "GBP":
				exchangeRate = quotationBean.getExchangeRateGBP();
				break;
			}
			moldPrice = new BigDecimal(moldPrice).setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue();
			
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("productFactoryPrice", productFactoryPrice);
			map.put("moldFactoryPrice", moldFactoryPrice);
			map.put("moldPrice", moldPrice);
			return new JsonResult<Map<String, Object>>(map);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			LOG.error(">>>>>>>>>>>>>>>>>QuotationController_queryMoldPrice_exception--:--"
					+ e);
			return new JsonResult<Map<String, Object>>(1, "未查询到数据");
		}

	}

	/**
	 * 报价单添加产品
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addQuotationProduct.do")
	public JsonResult<List<QuotationProductionBean>> addQuotationProduct(
			HttpServletRequest request, HttpServletResponse response) {

		try {
			QuotationProductionBean quotationProductionBean = new QuotationProductionBean();
			if (StringUtils.isBlank(request.getParameter("quotationInfoId"))) {
				throw new RuntimeException("未获取到报价单信息");
			}
			Integer quotationInfoId = Integer.parseInt(request
					.getParameter("quotationInfoId"));
			quotationProductionBean.setQuotationInfoId(quotationInfoId);
			if (!(request.getParameter("productName") == null || ""
					.equals(request.getParameter("productName")))) {
				quotationProductionBean.setProductName(request
						.getParameter("productName"));
			}
			if (!(request.getParameter("moldPrice") == null || ""
					.equals(request.getParameter("moldPrice")))) {
				quotationProductionBean.setMoldPrice(Double.parseDouble(request
						.getParameter("moldPrice")));
			}
			if (!(request.getParameter("material") == null || "".equals(request
					.getParameter("material")))) {
				quotationProductionBean.setMaterial(request
						.getParameter("material"));
			}
			if (!(request.getParameter("remark") == null || "".equals(request
					.getParameter("remark")))) {
				quotationProductionBean.setProductNotes(request
						.getParameter("remark"));
			}
			String priceList = request.getParameter("priceList");
			List<QuotationProductionBean> list = quotationProductionService
					.insertQuotationProduction(quotationInfoId, priceList,
							quotationProductionBean);

			return new JsonResult<List<QuotationProductionBean>>(list);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			LOG.error(">>>>>>>>>>>>>>>>>QuotationController_addQuotationProduct_exception--:--"
					+ e);
			return new JsonResult<List<QuotationProductionBean>>(1, "未查询到数据");
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(">>>>>>>>>>>>>>>>>QuotationController_addQuotationProduct_exception--:--"
					+ e);
			return new JsonResult<List<QuotationProductionBean>>(1, "更新失败");
		}

	}

	/**
	 * 报价单添加产品
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/deleteQuotationProduct.do")
	public JsonResult<String> deleteQuotationProduct(
			HttpServletRequest request, HttpServletResponse response) {

		try {
			if (StringUtils.isBlank(request.getParameter("quotationInfoId"))) {
				throw new RuntimeException("未获取到报价单信息");
			}
			Integer quotationInfoId = Integer.parseInt(request
					.getParameter("quotationInfoId"));
			if (StringUtils.isBlank(request.getParameter("productId"))) {
				throw new RuntimeException("未获取到产品信息");
			}
			Integer productId = Integer.parseInt(request
					.getParameter("productId"));
			quotationProductionService.deleteProductionById(productId,
					quotationInfoId);

			return new JsonResult<String>(0, "删除成功");
		} catch (NumberFormatException e) {
			e.printStackTrace();
			LOG.error(">>>>>>>>>>>>>>>>>QuotationController_addQuotationProduct_exception--:--"
					+ e);
			return new JsonResult<String>(1, "删除失败");
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(">>>>>>>>>>>>>>>>>QuotationController_addQuotationProduct_exception--:--"
					+ e);
			return new JsonResult<String>(1, "删除失败");
		}

	}

	/**
	 * 更新价格状态
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/updatePriceStatus.do")
	public JsonResult<String> updatePriceStatus(HttpServletRequest request,
			HttpServletResponse response) {

		try {
			if (StringUtils.isBlank(request.getParameter("productId"))) {
				throw new RuntimeException("未获取到产品信息");
			}
			Integer productId = Integer.parseInt(request
					.getParameter("productId"));
			QuotationProductionBean quotationProductionBean = quotationProductionService
					.queryProductionById(productId);
			quotationProductionBean.setPriceStatus(1);
			quotationProductionBean.setUpdateTime(DateFormat.format());
			quotationProductionService
					.updateQuotationProduction(quotationProductionBean);
			return new JsonResult<String>(0, "更新成功", "");
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return new JsonResult<String>(1, "更新失败");
		}

	}

	/**
	 * 保存留言消息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/saveQuotationMessage.do")
	public JsonResult<OrderMessage> saveQuotationMessage(
			HttpServletRequest request, HttpServletResponse response) {

		try {
			OrderMessage orderMessage = new OrderMessage();
			MessageCenter messageCenter = new MessageCenter();

			if (StringUtils.isBlank(request.getParameter("quotationId"))) {
				throw new RuntimeException("未获取到报价单信息");
			}
			Integer quotationId = Integer.parseInt(request
					.getParameter("quotationId"));
			String messageDetails = request.getParameter("messageDetails");
			String factoryId = request.getParameter("factoryId");
			String userId = request.getParameter("userId");
			StringUtils.isBlank(request.getParameter("targetPriceReply"));
			Integer targetPriceReply = Integer.parseInt(request
					.getParameter("targetPriceReply"));
			String attachmentPath = request.getParameter("attachmentPath");
			String salesId = WebCookie.getUserId(request);

			orderMessage.setMessageDetails(messageDetails);
			orderMessage.setMessageSendTime(DateFormat.format());
			orderMessage.setReadStatus(UNREAD_STATUS);
			orderMessage.setCustomerOrFactory(FACTORY_MESSAGE);
			orderMessage.setTargetPriceReply(targetPriceReply);
			orderMessage.setFactoryId(factoryId);
			orderMessage.setPicStatus(0);
			orderMessage.setSalesId(salesId);
			orderMessage.setAttachmentPath(attachmentPath);

			messageCenter.setFactoryId(factoryId);
			messageCenter.setMessageTitle(QUOTATION_MESSAGE_TITLE);
			messageCenter.setMessageType(MESSAGE_QUOTATION_TYPE);
			messageCenter.setUserid(userId);
			messageCenter.setQuotationInfoId(quotationId);

			orderMessage = messageCenterService.insertQuotaionMessage(
					messageCenter, orderMessage);

			return new JsonResult<OrderMessage>(orderMessage);

		} catch (NumberFormatException e) {
			e.printStackTrace();
			LOG.error(">>>>>>>>>>>>>>>>>QuotationController_saveQuotationMessage_exception--:--"
					+ e);
			return new JsonResult<OrderMessage>(0, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(">>>>>>>>>>>>>>>>>QuotationController_saveQuotationMessage_exception--:--"
					+ e);
			return new JsonResult<OrderMessage>(0, e.getMessage());
		}
	}

	/**
	 * 跳转发送邮件界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/toSendQuotation.do")
	public String toSendQuotation(HttpServletRequest request,
			HttpServletResponse response) {

		try {
			String quotationAttr = null;
			if (StringUtils.isBlank(request.getParameter("quotationInfoId"))) {
				throw new RuntimeException("未获取到报价单信息");
			}
			Integer quotationInfoId = Integer.parseInt(request
					.getParameter("quotationInfoId"));
			QuotationBean quotationBean = quotationService
					.queryById(quotationInfoId);

			String userId = request.getParameter("userId");
			User user = userService.queryByUserId(userId);
			if (user == null || "".equals(user)) {
				LOG.info("未获取到客户信息");
			} else {
				String loginEmail = user.getLoginEmail();
				if (StringUtils.isBlank(loginEmail)) {
					throw new RuntimeException("未获取到客户邮箱");
				}
				String pwd = user.getPwd();
				String token = loginEmail + "&" + pwd;
				String encryptTxt = "";
				encryptTxt = Base64Encode.getBase64(token);
				quotationAttr = NbMailAddressUtil.getQuotationAttr()
						+ "?userInfo=" + encryptTxt;
			}

			request.setAttribute("quotationAttr", quotationAttr);
			request.setAttribute("quotationId", quotationInfoId);
			request.setAttribute("userId", userId);
			request.setAttribute("userId", userId);
			request.setAttribute("projectId", quotationBean.getProjectId());

		} catch (NumberFormatException e) {
			e.printStackTrace();
			LOG.error(">>>>>>>>>>>>>>>>>QuotationController_toSendQuotation_exception--:--"
					+ e);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(">>>>>>>>>>>>>>>>>QuotationController_toSendQuotation_exception--:--"
					+ e);
		}

		return "quotationEmail.jsp";
	}

	/**
	 * 根据报价表id查询留言消息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/queryQuotationMessageById.do")
	public String queryQuotationMessageById(HttpServletRequest request,
			HttpServletResponse response) {

		try {
			Integer quotationInfoId = null;
			if (!(StringUtils.isBlank(request.getParameter("quotationInfoId")))) {
				String id = Base64Encode.getFromBase64(request
						.getParameter("quotationInfoId"));
				quotationInfoId = Integer.parseInt(id);
			} else {
				throw new RuntimeException("报价单id为空");
			}
			QuotationBean quotationBean = quotationService
					.queryById(quotationInfoId);
			List<OrderMessage> messages = orderMessageService
					.queryByQuotationInfoId(quotationInfoId);
			for (OrderMessage orderMessage : messages) {
				String email = orderMessage.getLoginEmail();
				email = email.replaceAll(
						"(\\w?)(\\w+)(\\w)(@\\w+\\.[a-z]+(\\.[a-z]+)?)",
						"$1****$3$4");
				orderMessage.setLoginEmail(email);

				if (orderMessage.getTargetPriceReply() == 2) {
					String attachmentPath = orderMessage.getAttachmentPath();
					if (!(attachmentPath == null || "".equals(attachmentPath))) {
						File tempFile = new File(attachmentPath.trim());
						String name = tempFile.getName();
						String[] split = name.split("&");
						if (split.length != 0) {
							String fileName = split[0]
									+ "."
									+ FilenameUtils
											.getExtension(attachmentPath);
							orderMessage.setAttachmentPath(fileName);
						}
					}
				}
				if (orderMessage.getCustomerOrFactory() == CUSTOMER_MESSAGE) {
					orderMessage.setReadStatus(READ_STATUS);
					orderMessage.setMessageReadTime(DateFormat.format());
				}
			}
			request.setAttribute("quotationBean", quotationBean);
			request.setAttribute("messages", messages);
			orderMessageService.updateReadStatus(messages);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			LOG.error(">>>>>>>>>>>>>>>>>QuotationController_queryQuotationMessageById_exception--:--"
					+ e);
		}
		return "quotation_message.jsp";
	}

	/**
	 * 主要用于报价列表界面发送报价邮件
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/sendQuotationEmail.do")
	public JsonResult<Map<Object, Object>> sendQuotationEmail(
			HttpServletRequest request, HttpServletResponse response) {

		try {
			if (StringUtils.isBlank(request.getParameter("quotationInfoId"))) {
				throw new RuntimeException("未获取到报价单信息");
			}
			Integer quotationInfoId = Integer.parseInt(request
					.getParameter("quotationInfoId"));
			QuotationBean quotationBean = quotationService
					.queryById(quotationInfoId);

			String userId = quotationBean.getUserId();
			User user = userService.queryByUserId(userId);
			if (user == null || "".equals(user)) {
				return new JsonResult<Map<Object, Object>>(2, "客户未录入");
			}
			String loginEmail = user.getLoginEmail();
			if (StringUtils.isBlank(loginEmail)) {
				return new JsonResult<Map<Object, Object>>(2, "客户邮箱为空");
			}
			String pwd = user.getPwd();
			String token = loginEmail + "&" + pwd;
			String encryptTxt = "";
			encryptTxt = Base64Encode.getBase64(token);

			String quotationAttr = NbMailAddressUtil.getQuotationAttr()
					+ "?userInfo=" + encryptTxt;

			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put("projectId", quotationBean.getProjectId());
			map.put("quotationAttr", quotationAttr);
			map.put("userId", userId);
			String quotationPath = quotationBean.getQuotationPath();
			File file = new File(quotationPath);
			// String realPath =
			// request.getSession().getServletContext().getRealPath("/");
			String pdfPath = "67.198.209.91:8099" + "/static_img/quotation/"
					+ quotationBean.getFactoryId() + "/" + file.getName();
			map.put("pdfPath", URLEncoder.encode(pdfPath, "utf-8"));

			return new JsonResult<Map<Object, Object>>(map);

		} catch (NumberFormatException e) {
			e.printStackTrace();
			LOG.error(">>>>>>>>>>>>>>>>>QuotationController_sendQuotationEmail_exception--:--"
					+ e);
			return new JsonResult<Map<Object, Object>>(1, "未获取到报价单");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			LOG.error(">>>>>>>>>>>>>>>>>QuotationController_sendQuotationEmail_exception--:--"
					+ e);
			return new JsonResult<Map<Object, Object>>(1, "未获取到报价单");
		}

	}

	/**
	 * 选择货币(已存在的报价单)
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/selectCurrencyEdit.do")
	public JsonResult<Double> selectCurrencyEdit(HttpServletRequest request,
			HttpServletResponse response) {

		try {
			String currency = request.getParameter("currency");
			Double exchangeRate = 0.00;
			if (StringUtils.isBlank(request.getParameter("quotationInfoId"))) {
				throw new RuntimeException("未获取到报价单信息");
			}
			Integer quotationInfoId = Integer.parseInt(request
					.getParameter("quotationInfoId"));
			QuotationBean quotationBean = quotationService
					.queryById(quotationInfoId);
			if ("USD".equals(currency)) {
				exchangeRate = 1.0;
			} else if ("CNY".equals(currency)) {
				exchangeRate = quotationBean.getExchangeRateCNY();
			} else if ("EUR".equals(currency)) {
				exchangeRate = quotationBean.getExchangeRateEUR();
			} else if ("GBP".equals(currency)) {
				exchangeRate = quotationBean.getExchangeRateGBP();
			}

			quotationService.updateQuotation(request, quotationBean, currency);
			return new JsonResult<Double>(exchangeRate);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(">>>>>>>>>>>>>>>>>QuotationController_selectCurrencyEdit_exception--:--"
					+ e);
			return new JsonResult<Double>(1, "查询失败");
		}

	}

	/**
	 * 选择货币(新建报价单)
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/selectCurrency.do")
	public JsonResult<Double> selectCurrency(HttpServletRequest request,
			HttpServletResponse response) {

		try {
			String currency = request.getParameter("currency");
			Double exchangeRate = 0.00;

			if ("USD".equals(currency)) {
				exchangeRate = 1.0;
			} else if ("CNY".equals(currency)) {
				exchangeRate = amountUnitService
						.queryByCurrencyShorthand("CNY").getExchangeRate();
			} else if ("EUR".equals(currency)) {
				exchangeRate = amountUnitService
						.queryByCurrencyShorthand("EUR").getExchangeRate();
			} else if ("GBP".equals(currency)) {
				exchangeRate = amountUnitService
						.queryByCurrencyShorthand("GBP").getExchangeRate();
			}

			return new JsonResult<Double>(exchangeRate);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(">>>>>>>>>>>>>>>>>QuotationController_selectCurrency_exception--:--"
					+ e);
			return new JsonResult<Double>(1, "查询失败");
		}

	}

	/**
	 * 更新报价备注排序
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/updateIndex.do")
	public JsonResult<List<QuotationRemarkTemplate>> updateIndex(
			HttpServletRequest request, HttpServletResponse response) {

		try {
			Integer remarkId = null;
			if (!(StringUtils.isBlank(request.getParameter("remarkId")))) {
				remarkId = Integer.parseInt(request.getParameter("remarkId"));
			}
			Integer remarkId1 = null;
			if (!(StringUtils.isBlank(request.getParameter("remarkId1")))) {
				remarkId1 = Integer.parseInt(request.getParameter("remarkId1"));
			}
			Integer currentIndex = null;
			if (!(StringUtils.isBlank(request.getParameter("currentIndex")))) {
				currentIndex = Integer.parseInt(request
						.getParameter("currentIndex"));
			}
			Integer index = null;
			if (!(StringUtils.isBlank(request.getParameter("index")))) {
				index = Integer.parseInt(request.getParameter("index"));
			}
			QuotationRemarkTemplate quotationRemarkTemplate = quotationService
					.queryByRemarkId(remarkId);
			quotationRemarkTemplate.setRemarkIndex(currentIndex);
			QuotationRemarkTemplate quotationRemarkTemplate1 = quotationService
					.queryByRemarkId(remarkId1);
			quotationRemarkTemplate1.setRemarkIndex(index);

			String factoryId = WebCookie.getFactoryId(request);
			List<QuotationRemarkTemplate> remarks = quotationService
					.updateTempRemark(quotationRemarkTemplate,
							quotationRemarkTemplate1, factoryId);

			return new JsonResult<List<QuotationRemarkTemplate>>(remarks);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(">>>>>>>>>>>>>>>>>QuotationController_updateIndexTime_exception--:--"
					+ e);
			return new JsonResult<List<QuotationRemarkTemplate>>(1, "查询失败");
		}

	}

	/**
	 * 查询产品价格详情
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryPriceDetails.do")
	public JsonResult<Map<String, Object>> queryPriceDetails(
			HttpServletRequest request, HttpServletResponse response) {

		try {
			Integer priceId = null;
			Integer productId = null;
			if (!(StringUtils.isBlank(request.getParameter("priceId")))) {
				priceId = Integer.parseInt(request.getParameter("priceId"));
			} else {
				return new JsonResult<Map<String, Object>>(1, "未获取到价格表ID");
			}
			if (!(StringUtils.isBlank(request.getParameter("productId")))) {
				productId = Integer.parseInt(request.getParameter("productId"));
			} else {
				return new JsonResult<Map<String, Object>>(1, "未获取到产品表ID");
			}
			QuotationProductionBean quotationProductionBean = quotationProductionService
					.queryProductionById(productId);
			List<QuotationProcessInfo> list = quotationProductionService
					.queryProcessByProductionId(priceId);
			QuotationProductionPriceBean priceBean = quotationProductionService
					.queryByPriceId(priceId);
			Double weight = priceBean.getMaterialWeight()
					* priceBean.getQuantity();
			weight = new BigDecimal(weight).setScale(4,
					BigDecimal.ROUND_HALF_UP).doubleValue();
			priceBean.setMaterialWeight(weight);
			Double materialUnitPrice = quotationProductionBean
					.getMaterialUnitPrice()
					* (1 + priceBean.getMaterialProfitRate());
			materialUnitPrice = new BigDecimal(materialUnitPrice).setScale(4,
					BigDecimal.ROUND_HALF_UP).doubleValue();
			quotationProductionBean.setMaterialUnitPrice(materialUnitPrice);

			Map<String, Object> map = new HashMap<String, Object>();

			map.put("productionBean", quotationProductionBean);
			map.put("processList", list);
			map.put("priceBean", priceBean);
			return new JsonResult<Map<String, Object>>(map);

		} catch (NumberFormatException e) {
			e.printStackTrace();
			return new JsonResult<Map<String, Object>>(1, "获取失败");
		}

	}

	/**
	 * 根据id删除工艺
	 * @Title updateViewStatus 
	 * @Description TODO
	 * @param request
	 * @param response
	 * @return
	 * @return JsonResult<String>
	 */
	@ResponseBody
	@RequestMapping("/deleteProcess.do")
	public JsonResult<String> deleteProcess(HttpServletRequest request,HttpServletResponse response) {
		Integer processId;
		try {
			if(StringUtils.isNotBlank(request.getParameter("processId"))){
				processId = Integer.parseInt(request.getParameter("processId"));
				quotationProductionService.deleteProcess(processId);
				return new JsonResult<String>(0, "删除成功");
			}else{
				return new JsonResult<String>(1, "未获取到ID");
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return new JsonResult<String>(1, "未获取到ID");
		}
		
		
	}
	
	
	
	
	/**
	 * 更新重量、工艺显示状态
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/updateViewStatus.do")
	public JsonResult<String> updateViewStatus(HttpServletRequest request,
			HttpServletResponse response) {

		try {
			Integer quotationInfoId = null;
			if (!(StringUtils.isBlank(request.getParameter("quotationInfoId")))) {
				quotationInfoId = Integer.parseInt(request
						.getParameter("quotationInfoId"));
			} else {
				return new JsonResult<String>(1, "未获取到报价单号");
			}
			QuotationBean quotationBean = quotationService
					.queryById(quotationInfoId);
			if (StringUtils.isNotBlank(request.getParameter("type"))) {
				Integer status = 0;
				if (StringUtils.isNotBlank(request.getParameter("status"))) {
					status = Integer.parseInt(request.getParameter("status"));
				}
				String type = request.getParameter("type");
				if ("1".endsWith(type)) {
					quotationBean.setWeightStatus(status);
					quotationService.updateQuotation(quotationBean);
				} else if ("2".equals(type)) {
					quotationBean.setProcessStatus(status);
					quotationService.updateQuotation(quotationBean);
				}
			}
			return new JsonResult<String>(0, "更新成功");
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return new JsonResult<String>(1, "查询失败");
		}

	}

	/**
	 * 上传pdf报告
	 * 
	 * @Title uploadQuotationPdf
	 * @Description TODO
	 * @param request
	 * @param response
	 * @return
	 * @return JsonResult<String>
	 */
	@ResponseBody
	@RequestMapping("/uploadQuotationPdf.do")
	public JsonResult<String> uploadQuotationPdf(HttpServletRequest request,
			HttpServletResponse response) {

		try {
			if (StringUtils.isBlank(request.getParameter("quotationInfoId"))) {
				throw new RuntimeException("未获取到报价单信息");
			} else {
				Integer quotationInfoId = Integer.parseInt(request
						.getParameter("quotationInfoId"));
				QuotationBean quotationBean = quotationService
						.queryById(quotationInfoId);
				String path = UploadAndDownloadPathUtil.getQuotationPath()
						+ quotationBean.getFactoryId() + File.separator;
				File file = new File(path);
				if (!file.exists() && !file.isDirectory()) {
					file.mkdir();
				}
				// 根据文件名获取上传文件的位置 数据库保存原始文件名称
				Map<String, String> multiFileUpload = OperationFileUtil.multiFileUpload(request, path);
				String filePath = "";
				if (!(multiFileUpload == null || multiFileUpload.size() == 0)) {					
		 			 Set<String> keySet = multiFileUpload.keySet();
		 			    for (String key : keySet) {	
		 				 filePath = multiFileUpload.get(key);  
		 				}
					quotationBean.setQuotationPath(filePath);
					quotationBean.setUpdateTime(DateFormat.format());
					quotationService.updateQuotation(quotationBean);
				}
				return new JsonResult<String>(0, "更新成功");
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return new JsonResult<String>(1, "未获取到数据");
		} catch (IllegalStateException e) {
			e.printStackTrace();
			return new JsonResult<String>(1, "上传失败");
		} catch (IOException e) {
			e.printStackTrace();
			return new JsonResult<String>(1, "上传失败");
		}

	}

}
