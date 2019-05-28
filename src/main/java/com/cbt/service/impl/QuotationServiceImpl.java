package com.cbt.service.impl;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cbt.dao.QuotationDao;
import com.cbt.dao.QuotationProductionDao;
import com.cbt.dao.SaleCustomerDao;
import com.cbt.entity.AmountUnit;
import com.cbt.entity.QuotationBean;
import com.cbt.entity.QuotationProcessInfo;
import com.cbt.entity.QuotationProductionBean;
import com.cbt.entity.QuotationProductionPriceBean;
import com.cbt.entity.QuotationRemarkTemplate;
import com.cbt.entity.SaleCustomer;
import com.cbt.entity.SaleOrder;
import com.cbt.print.QuotationBlowPrintTemplate;
import com.cbt.print.QuotationExtrusionPrintTemplate;
import com.cbt.print.QuotationInjectatePrintTemplate;
import com.cbt.print.QuotationMeltmoldPrintTemplate;
import com.cbt.print.QuotationNewPressPrintTemplate;
import com.cbt.print.QuotationNormalPrintTemplate;
import com.cbt.print.QuotationPressPrintTemplate;
import com.cbt.print.QuotationPrintTemplate;
import com.cbt.print.QuotationRotatePrintTemplate;
import com.cbt.print.QuotationSandcastingPrintTemplate;
import com.cbt.print.QuotationUptakePrintTemplate;
import com.cbt.service.QuotationService;
import com.cbt.util.DateFormat;
import com.cbt.util.WebCookie;

/**
 * 报价单service层
 * 
 * @author polo
 * @time 2017.5.24
 */
@Service
public class QuotationServiceImpl implements QuotationService {

	@Autowired
	private QuotationDao quotationDao;
	@Autowired
	private QuotationProductionDao quotationProductionDao;
	@Autowired
	private SaleCustomerDao saleCustomerDao;

	/**
	 * 管理员，报价单列表查询
	 * 
	 * @author tb
	 * @time 2017.3.28
	 * @param user
	 *            用户名或用户id beginDate 开始时间 endDate 结束时间 page 查询的页数
	 */
	@Override
	public List<QuotationBean> queryForListAdmin(String user, String beginDate,
			String endDate, Integer page, String factoryId) {
		List<QuotationBean> list = quotationDao.queryForListAdmin(user,
				beginDate, endDate, page, factoryId);
		if (list.size() < 1) {
			return list;
		}
		return list;
	}

	/**
	 * 管理员,查询报价单总条数
	 * 
	 * @author tb
	 * @time 2017.3.28
	 * @param user
	 *            用户名或用户id beginDate 开始时间 endDate 结束时间
	 */
	@Override
	public int queryCountForPageAdmin(String user, String beginDate,
			String endDate, String factoryId) {
		return quotationDao.queryCountForPageAdmin(user, beginDate, endDate,
				factoryId);
	}

	/**
	 * 普通用户,报价单列表查询
	 * 
	 * @author tb
	 * @time 2017.3.28
	 * @param user
	 *            用户名或用户id beginDate 开始时间 endDate 结束时间 page 查询的页数
	 */
	@Override
	public List<QuotationBean> queryForList(String user, String begin,
			String end, Integer page, String userName, String factoryId) {
		List<QuotationBean> list = quotationDao.queryForList(user, begin, end,
				page, userName, factoryId);
		if (list.size() < 1) {
			return list;
		}
		return list;
	}

	/**
	 * 普通用户,查询报价单总条数
	 * 
	 * @author tb
	 * @time 2017.3.28
	 * @param user
	 *            用户名或用户id beginDate 开始时间 endDate 结束时间
	 */
	@Override
	public int queryCountForPage(String user, String begin, String end,
			String userName, String factoryId) {

		return quotationDao.queryCountForPage(user, begin, end, userName,
				factoryId);
	}

	/**
	 * 获取所有的货币单元
	 * 
	 * @author tb
	 * @time 2017.3.30
	 */
	@Override
	public List<AmountUnit> queryCurrencyList() {
		return quotationDao.queryCurrencyList();
	}

	@Transactional
	@Override
	public void insertTempRemark(QuotationRemarkTemplate quotationRemarkTemplate) {
		quotationDao.insertTempRemark(quotationRemarkTemplate);

	}

	@Transactional
	@Override
	public void updateTempRemark(QuotationRemarkTemplate quotationRemarkTemplate) {
		quotationDao.updateTempRemark(quotationRemarkTemplate);

	}

	@Override
	public QuotationRemarkTemplate queryByRemarkId(Integer RemarkId) {
		return quotationDao.queryByRemarkId(RemarkId);

	}

	@Override
	public void deleteTempRemarkById(Integer RemarkId) {
		quotationDao.deleteTempRemarkById(RemarkId);

	}

	@Override
	public List<QuotationRemarkTemplate> queryTempRemarkByFactoryId(
			String factoryId) {
		return quotationDao.queryTempRemarkByFactoryId(factoryId);
	}

	/**
	 * 保存报价单
	 */
	@Transactional
	@Override
	public void insertQuotation(HttpServletRequest request,
			QuotationBean quotationBean, String productList) throws Exception {

		quotationDao.insertQuotation(quotationBean);
		int count = quotationDao.queryCurrentDateCount() + 1;
		int date = DateFormat.currentDateFormat();
		String quotationId = date + "Q" + count;
		// 批量插入Quotation_Production_info
		if (productList.endsWith(",")) {
			if (productList.length() > 1) {
				productList = productList
						.substring(0, productList.length() - 1);

				String[] split = productList.split(",");
				for (int i = 0; i < split.length; i++) {
					String[] split2 = split[i].split("%%");
					QuotationProductionBean p = new QuotationProductionBean();
					String productName = null;
					if (!(split2[0].replaceFirst(".", "") == null || ""
							.equals(split2[0].replaceFirst(".", "")))) {
						productName = split2[0].replaceFirst(".", "");
					}
					Double moldPrice = 0.00;
					if (!(split2[1].replaceFirst(".", "") == null || ""
							.equals(split2[1].replaceFirst(".", "")))) {
						moldPrice = Double.parseDouble(split2[1].replaceFirst(
								".", ""));
					}
					String material = null;
					if (!(split2[2].replaceFirst(".", "") == null || ""
							.equals(split2[2].replaceFirst(".", "")))) {
						material = split2[2].replaceFirst(".", "");
					}
					String productNotes = null;
					if (!(split2[5].replaceFirst(".", "") == null || ""
							.equals(split2[5].replaceFirst(".", "")))) {
						productNotes = split2[5].replaceFirst(".", "");
					}else{
						productNotes = "";
					}
					p.setMaterial(material);
					p.setMoldPrice(moldPrice);
					p.setProductName(productName);
					p.setProductNotes(productNotes);
					p.setQuotationInfoId(quotationBean.getId());
					quotationProductionDao.insertQuotationProduction(p);

					String quantityList = "";
					if (!(split2[4].replaceFirst(".", "") == null || ""
							.equals(split2[4].replaceFirst(".", "")))) {
						quantityList = split2[4].replaceFirst(".", "");
					}
					String priceList = "";
					List<QuotationProductionPriceBean> quotationProductionPriceBeans = new ArrayList<QuotationProductionPriceBean>();
					if (!(split2[3].replaceFirst(".", "") == null || ""
							.equals(split2[3].replaceFirst(".", "")))) {
						priceList = split2[3].replaceFirst(".", "");
						String[] split3 = priceList.split("&");
						String[] split4 = quantityList.split("&");

						for (int j = 0; j < split3.length; j++) {
							QuotationProductionPriceBean quotationProductionPriceBean = new QuotationProductionPriceBean();
							Double unitPrice = Double.parseDouble(split3[j]);
							Integer quantity = Integer.parseInt(split4[j]);
							quotationProductionPriceBean
									.setProductName(productName);
							quotationProductionPriceBean
									.setProductPrice(unitPrice);
							quotationProductionPriceBean.setQuantity(quantity);
							quotationProductionPriceBean.setProductionInfoId(p
									.getId());
							quotationProductionPriceBeans
									.add(quotationProductionPriceBean);
						}
						quotationProductionDao
								.insertQuotationPriceBatch(quotationProductionPriceBeans);
					}

				}
			}
		}

		// 更新总价格和pdf下载路径
		Double minPriceAmount = 0.00;
		Double maxPriceAmount = 0.00;
		List<QuotationProductionBean> list = quotationProductionDao
				.queryByQuotationInfoId(quotationBean.getId());
		for (QuotationProductionBean quotationProductionBean : list) {
			List<QuotationProductionPriceBean> priceBeans = quotationProductionDao
					.queryByProductionId(quotationProductionBean.getId());
			quotationProductionBean
					.setQuotationProductionPriceBeanList(priceBeans);
			Map<String, Double> maxMinPrice = quotationProductionDao
					.queryMinMaxPriceByProductId(quotationProductionBean
							.getId());
			Double minPrice = 0.0;
			Double maxPrice = 0.0;
			if (!(maxMinPrice == null || maxMinPrice.size() == 0)) {
				minPrice = maxMinPrice.get("minPrice");
				maxPrice = maxMinPrice.get("maxPrice");
			}
			minPriceAmount += minPrice;
			maxPriceAmount += maxPrice;
		}

		// 判断是否显示重量、工艺生成
		String path = null;

		switch (quotationBean.getPdfType()) {

		case 8:
			path = QuotationInjectatePrintTemplate.printExcel(
					request,
					request.getSession().getServletContext()
							.getRealPath(File.separator), quotationBean, list);
			break;

		case 13:
			path = QuotationNewPressPrintTemplate.printExcel(
					request,
					request.getSession().getServletContext()
							.getRealPath(File.separator), quotationBean, list);
			break;
		case 9:

			path = QuotationBlowPrintTemplate.printExcel(
					request,
					request.getSession().getServletContext()
							.getRealPath(File.separator), quotationBean, list);
			break;
		case 10:

			path = QuotationExtrusionPrintTemplate.printExcel(
					request,
					request.getSession().getServletContext()
							.getRealPath(File.separator), quotationBean, list);
			break;

		case 11:

			path = QuotationMeltmoldPrintTemplate.printExcel(
					request,
					request.getSession().getServletContext()
							.getRealPath(File.separator), quotationBean, list);
			break;

		case 12:

			path = QuotationSandcastingPrintTemplate.printExcel(
					request,
					request.getSession().getServletContext()
							.getRealPath(File.separator), quotationBean, list);
			break;

		default:
			path = QuotationNormalPrintTemplate.printExcel(
					request,
					request.getSession().getServletContext()
							.getRealPath(File.separator), quotationBean, list);
			break;

		}
		quotationBean.setQuotationPath(path);
		quotationBean.setQuotationId(quotationId);

		if (minPriceAmount.equals(maxPriceAmount)) {
			quotationBean.setQuotationPriceRange(maxPriceAmount.toString());
		} else {
			quotationBean.setQuotationPriceRange(minPriceAmount + "~"
					+ maxPriceAmount);
		}
		quotationDao.updateQuotation(quotationBean);

	}

	/**
	 * 删除报价单 并且删除关联表（product price表）
	 */
	@Transactional
	@Override
	public void deleteQuotation(Integer id) throws Exception {
		List<QuotationProductionBean> products = quotationProductionDao
				.queryByQuotationInfoId(id);
		for (QuotationProductionBean quotationProductionBean : products) {
			quotationProductionDao
					.deletePriceByProductionId(quotationProductionBean.getId());
		}
		quotationProductionDao.deletePriceByQuotationId(id);
		quotationDao.deleteQuotation(id);

	}

	/**
	 * 根据id查询报价单
	 */
	@Override
	public QuotationBean queryById(Integer id) {
		return quotationDao.queryById(id);
	}

	/**
	 * 更新报价单数据
	 */
	@Transactional
	@Override
	public void updateQuotationAndProduct(HttpServletRequest request,
			QuotationBean quotationBean, String productList,String type) throws Exception {

		// List<QuotationProductionBean> products =
		// quotationProductionDao.queryByQuotationInfoId(quotationBean.getId());
		// for (QuotationProductionBean quotationProductionBean : products) {
		// quotationProductionDao.deletePriceByProductionId(quotationProductionBean.getId());
		// }
		// quotationProductionDao.deletePriceByQuotationId(quotationBean.getId());

		// 批量插入Quotation_Production_info
		if (productList.endsWith(",,")) {
			if (productList.length() > 2) {
				productList = productList
						.substring(0, productList.length() - 2);

				String[] split = productList.split(",,");
				for (int i = 0; i < split.length; i++) {
					String[] split2 = split[i].split("%%");
					QuotationProductionBean p = new QuotationProductionBean();
					Integer productId = null;
					if (!(split2[0].replaceFirst(".", "") == null || ""
							.equals(split2[0].replaceFirst(".", "")))) {
						productId = Integer.parseInt(split2[0].replaceFirst(
								".", ""));
						p = quotationProductionDao
								.queryProductionById(productId);
					}
					String productName = null;
					if (!(split2[1].replaceFirst(".", "") == null || ""
							.equals(split2[1].replaceFirst(".", "")))) {
						productName = split2[1].replaceFirst(".", "");
						p.setProductName(productName);
					}
					String materialName = null;
					if (!(split2[2].replaceFirst(".", "") == null || ""
							.equals(split2[2].replaceFirst(".", "")))) {
						materialName = split2[2].replaceFirst(".", "");
						p.setMaterial(materialName);
					}
					Double weight = 0.00;
					if (!(split2[3].replaceFirst(".", "") == null || ""
							.equals(split2[3].replaceFirst(".", "")))) {
						weight = Double.parseDouble(split2[3].replaceFirst(".",
								""));
					}
					String weightUnit = null;
					if (!(split2[4].replaceFirst(".", "") == null || "".equals(split2[4].replaceFirst(".", "")))) {
						weightUnit = split2[4].replaceFirst(".","");
					}
					Double moldPrice = 0.00;
					if (!(split2[5].replaceFirst(".", "") == null || ""
							.equals(split2[5].replaceFirst(".", "")))) {
						moldPrice = Double.parseDouble(split2[5].replaceFirst(
								".", ""));
						p.setMoldPrice(moldPrice);
					}
					Double moldProfitRate = 0.00;
					if (!(split2[6].replaceFirst(".", "") == null || ""
							.equals(split2[6].replaceFirst(".", "")))) {
						moldProfitRate = Double.parseDouble(split2[6]
								.replaceFirst(".", ""));
						p.setMoldProfitRate(moldProfitRate / 100);
					}
					String productNotes = null;
					if (!(split2[13].replaceFirst(".", "") == null || ""
							.equals(split2[13].replaceFirst(".", "")))) {
						productNotes = split2[13].replaceFirst(".", "");
						p.setProductNotes(productNotes);
					}
					Double productProfitRate = 0.00;
					if (!(split2[14].replaceFirst(".", "") == null || ""
							.equals(split2[14].replaceFirst(".", "")))) {
						productProfitRate = Double.parseDouble(split2[14].replaceFirst(".", ""));
						p.setProductProfitRate(productProfitRate/100);
					}
					quotationProductionDao.updateQuotationProduction(p);

					// 更新工艺
					String processes = "";
					String processIds = "";
					List<QuotationProcessInfo> quotationProcessInfos = new ArrayList<QuotationProcessInfo>();
					if (!(split2[8].replaceFirst(".", "") == null || ""
							.equals(split2[8].replaceFirst(".", "")))) {
						processIds = split2[8].replaceFirst(".", "");
						processes = split2[7].replaceFirst(".", "");
						String[] split7 = processIds.split("&");
						String[] split8 = processes.split("&");
						for (int j = 0; j < split7.length; j++) {
							QuotationProcessInfo processInfo = quotationProductionDao
									.queryProcessById(Integer
											.parseInt(split7[j]));
							processInfo.setProcessName(split8[j]);
							quotationProcessInfos.add(processInfo);
						}
						quotationProductionDao
								.updateProcessBatch(quotationProcessInfos);
					}

					// 更新价格
					String quantityList = "";
					if (!(split2[10].replaceFirst(".", "") == null || ""
							.equals(split2[10].replaceFirst(".", "")))) {
						quantityList = split2[10].replaceFirst(".", "");
					}
					String priceList = "";
					String totalProfitRateList = "";
					String priceIdList = "";
					List<QuotationProductionPriceBean> quotationProductionPriceBeans = new ArrayList<QuotationProductionPriceBean>();
					if (!(split2[11].replaceFirst(".", "") == null || ""
							.equals(split2[11].replaceFirst(".", "")))) {
						priceIdList = split2[12].replaceFirst(".", "");
						priceList = split2[9].replaceFirst(".", "");
						totalProfitRateList = split2[11].replaceFirst(".", "");
						String[] split3 = priceList.split("&");
						String[] split4 = quantityList.split("&");
						String[] split5 = priceIdList.split("&");
						String[] split6 = totalProfitRateList.split("&");

						for (int j = 0; j < split5.length; j++) {
							QuotationProductionPriceBean quotationProductionPriceBean = quotationProductionDao
									.queryByPriceId(Integer.parseInt(split5[j]));
							Double unitPrice = Double.parseDouble(split3[j]);
							Integer quantity = Integer.parseInt(split4[j]);
							Double totalProfitRate = Double
									.parseDouble(split6[j]);
							quotationProductionPriceBean
									.setProductName(productName);
							quotationProductionPriceBean
									.setProductPrice(unitPrice);
							quotationProductionPriceBean.setQuantity(quantity);
							quotationProductionPriceBean
									.setMaterialWeight(weight);
							quotationProductionPriceBean
							.setWeightUnit(weightUnit);
							quotationProductionPriceBean
									.setTotalProfitRate(totalProfitRate / 100);
							quotationProductionPriceBeans
									.add(quotationProductionPriceBean);
						}
						quotationProductionDao
								.updateQuotationPriceBatch(quotationProductionPriceBeans);
					}

				}
			}

		}

		// 更新价格和PDF下载路径
		Double minPriceAmount = 0.00;
		Double maxPriceAmount = 0.00;
		List<QuotationProductionBean> list = quotationProductionDao
				.queryByQuotationInfoId(quotationBean.getId());
		for (QuotationProductionBean quotationProductionBean : list) {
			List<QuotationProductionPriceBean> priceBeans = quotationProductionDao
					.queryByProductionId(quotationProductionBean.getId());
			List<QuotationProcessInfo> processes = null;
			if (priceBeans != null && priceBeans.size() != 0) {
				processes = quotationProductionDao
						.queryProcessByProductionId(priceBeans.get(0).getId());
			}
			quotationProductionBean
					.setQuotationProductionPriceBeanList(priceBeans);
			if (processes != null && !"".equals(processes)) {
				quotationProductionBean.setProcessInfos(processes);
			}

			Map<String, Double> maxMinPrice = quotationProductionDao
					.queryMinMaxPriceByProductId(quotationProductionBean
							.getId());
			Double minPrice = 0.0;
			Double maxPrice = 0.0;
			if (!(maxMinPrice == null || maxMinPrice.size() == 0)) {
				minPrice = maxMinPrice.get("minPrice");
				maxPrice = maxMinPrice.get("maxPrice");
			}
			minPriceAmount += minPrice;
			maxPriceAmount += maxPrice;
		}

		// 判断是否显示重量、工艺生成
		String path = null;

		switch (quotationBean.getPdfType()) {

		case 8:
			path = QuotationInjectatePrintTemplate.printExcel(
					request,
					request.getSession().getServletContext()
							.getRealPath(File.separator), quotationBean, list);
			break;

		case 13:
			path = QuotationNewPressPrintTemplate.printExcel(
					request,
					request.getSession().getServletContext()
							.getRealPath(File.separator), quotationBean, list);
			break;
		case 9:

			path = QuotationBlowPrintTemplate.printExcel(
					request,
					request.getSession().getServletContext()
							.getRealPath(File.separator), quotationBean, list);
			break;
		case 10:

			path = QuotationExtrusionPrintTemplate.printExcel(
					request,
					request.getSession().getServletContext()
							.getRealPath(File.separator), quotationBean, list);
			break;

		case 11:

			path = QuotationMeltmoldPrintTemplate.printExcel(
					request,
					request.getSession().getServletContext()
							.getRealPath(File.separator), quotationBean, list);
			break;

		case 12:

			path = QuotationSandcastingPrintTemplate.printExcel(
					request,
					request.getSession().getServletContext()
							.getRealPath(File.separator), quotationBean, list);
			break;

		default:
			path = QuotationNormalPrintTemplate.printExcel(
					request,
					request.getSession().getServletContext()
							.getRealPath(File.separator), quotationBean, list);
			break;

		}
        if(StringUtils.isNotBlank(path)){
        	if(type.equals("excel")){
        		path = FilenameUtils.removeExtension(path)+".xls";
        	}
        }
		quotationBean.setQuotationPath(path);
		if (minPriceAmount.equals(maxPriceAmount)) {
			quotationBean.setQuotationPriceRange(new BigDecimal(maxPriceAmount)
					.setScale(0, BigDecimal.ROUND_HALF_UP).toString());
		} else {
			quotationBean.setQuotationPriceRange(new BigDecimal(minPriceAmount)
					.setScale(0, BigDecimal.ROUND_HALF_UP).toString()
					+ "~"
					+ new BigDecimal(maxPriceAmount).setScale(0,
							BigDecimal.ROUND_HALF_UP).toString());
		}
		quotationDao.updateQuotation(quotationBean);
	}

	@Transactional
	@Override
	public void insertQuotation(QuotationBean quotationBean,
			QuotationProductionBean productionBean,
			QuotationProductionPriceBean priceBean, List<SaleCustomer> list2,
			List<SaleOrder> list3, List<QuotationProcessInfo> processInfos)
			throws Exception {

		Integer quotationInfoId = null;
		QuotationBean quotationBean1 = quotationDao
				.queryByProjectId(quotationBean.getProjectId());
		if (quotationBean1 == null || "".equals(quotationBean1)) {
			quotationDao.insertQuotation(quotationBean);
			int count = quotationDao.queryCurrentDateCount() + 1;
			int date = DateFormat.currentDateFormat();
			String quotationId = date + "Q" + count;
			quotationBean.setQuotationId(quotationId);
			quotationDao.updateQuotation(quotationBean);
			quotationInfoId = quotationBean.getId();
		} else {
			quotationBean1.setCurrency(quotationBean.getCurrency());
			quotationBean1.setCreateTime(quotationBean.getCreateTime());
			quotationBean1.setExchangeRateCNY(quotationBean
					.getExchangeRateCNY());
			quotationBean1.setExchangeRateEUR(quotationBean
					.getExchangeRateEUR());
			quotationBean1.setExchangeRateGBP(quotationBean
					.getExchangeRateGBP());
			quotationBean1.setQuotationDate(quotationBean.getQuotationDate());
			quotationBean1.setQuotationPriceRange(quotationBean
					.getQuotationPriceRange());
			quotationDao.updateQuotation(quotationBean1);
			quotationInfoId = quotationBean1.getId();

		}
		productionBean.setQuotationInfoId(quotationInfoId);
		quotationProductionDao.insertQuotationProduction(productionBean);
		priceBean.setProductionInfoId(productionBean.getId());
		quotationProductionDao.insertQuotationPrice(priceBean);
		for (QuotationProcessInfo quotationProcessInfo : processInfos) {
			quotationProcessInfo.setQuotationPriceId(priceBean.getId());
		}

		if (list2.size() != 0) {
			saleCustomerDao.insertBatch(list2);
		}
		if (list3.size() != 0) {
			saleCustomerDao.insertSaleOrderBatch(list3);
		}
		if (processInfos.size() != 0) {
			quotationProductionDao.insertQuotationProcessBatch(processInfos);
		}

	}

	@Override
	public QuotationBean queryByProjectId(String projectId) {
		return quotationDao.queryByProjectId(projectId);
	}

	@Override
	public void updateQuotation(QuotationBean quotationBean) {
		quotationDao.updateQuotation(quotationBean);

	}

	@Override
	public Map<String, Double> queryMaxMinPrice(Integer quotationInfoId) {
		return quotationDao.queryMaxMinPrice(quotationInfoId);
	}

	@Transactional
	@Override
	public List<QuotationRemarkTemplate> updateTempRemark(
			QuotationRemarkTemplate quotationRemarkTemplate,
			QuotationRemarkTemplate quotationRemarkTemplate1, String factoryId) {
		quotationDao.updateTempRemark(quotationRemarkTemplate);
		quotationDao.updateTempRemark(quotationRemarkTemplate1);
		return quotationDao.queryTempRemarkByFactoryId(factoryId);
	}

	/**
	 * 改变金额单元后 更新金额
	 */
	@Transactional
	@Override
	public void updateQuotation(HttpServletRequest request,
			QuotationBean quotationBean, String currency) throws Exception {
		Double exchangeRate = 1.0;
		Double currentRate = 1.0;
		String currentCurrency = quotationBean.getCurrency();
		switch (currency) {
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

		switch (currentCurrency) {
		case "USD":
			currentRate = 1.0;
			break;
		case "CNY":
			currentRate = quotationBean.getExchangeRateCNY();
			break;
		case "EUR":
			currentRate = quotationBean.getExchangeRateEUR();
			break;
		case "GBP":
			currentRate = quotationBean.getExchangeRateGBP();
			break;
		}

		quotationBean.setCurrency(currency);
		Double minPriceAmount = 0.0;
		Double maxPriceAmount = 0.0;
		List<QuotationProductionBean> list = quotationProductionDao
				.queryByQuotationInfoId(quotationBean.getId());

		for (QuotationProductionBean quotationProductionBean : list) {
			List<QuotationProductionPriceBean> priceBeans = quotationProductionDao
					.queryByProductionId(quotationProductionBean.getId());

			for (QuotationProductionPriceBean quotationProductionPriceBean : priceBeans) {
				quotationProductionPriceBean
						.setMaterialPrice(quotationProductionPriceBean
								.getMaterialPrice()
								/ currentRate
								* exchangeRate);
				quotationProductionPriceBean
						.setProcessPrice(quotationProductionPriceBean
								.getProcessPrice() / currentRate * exchangeRate);
				quotationProductionPriceBean
						.setProductPrice(quotationProductionPriceBean
								.getProductPrice() / currentRate * exchangeRate);
				// List<QuotationProcessInfo> processInfos =
				// quotationProductionDao.queryProcessByProductionId(quotationProductionPriceBean.getId());
				// for (QuotationProcessInfo quotationProcessInfo :
				// processInfos) {
				// quotationProcessInfo.setProcessPrice(quotationProcessInfo.getProcessPrice()
				// / currentRate * exchangeRate);
				// quotationProcessInfo.setProcessFactoryPrice(quotationProcessInfo.getProcessFactoryPrice()
				// / currentRate * exchangeRate);
				// }
				// quotationProductionDao.updateProcessBatch(processInfos);
			}

			quotationProductionDao.updateQuotationPriceBatch(priceBeans);
			Map<String, Double> maxMinPrice = quotationProductionDao
					.queryMinMaxPriceByProductId(quotationProductionBean
							.getId());
			Double minPrice = 0.0;
			Double maxPrice = 0.0;
			if (!(maxMinPrice == null || maxMinPrice.size() == 0)) {
				minPrice = maxMinPrice.get("minPrice");
				maxPrice = maxMinPrice.get("maxPrice");
			}
			minPriceAmount += minPrice;
			maxPriceAmount += maxPrice;

			quotationProductionBean
					.setMaterialUnitPrice(quotationProductionBean
							.getMaterialUnitPrice()
							/ currentRate
							* exchangeRate);
			quotationProductionBean.setMoldPrice(quotationProductionBean
					.getMoldPrice() / currentRate * exchangeRate);
			Double moldFactoryPrice = quotationProductionBean.getMoldFactoryPrice() / currentRate * exchangeRate;
			moldFactoryPrice = new BigDecimal(moldFactoryPrice).setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue();
			quotationProductionBean.setMoldFactoryPrice(moldFactoryPrice);
			quotationProductionBean.setFactoryPrice(quotationProductionBean
					.getFactoryPrice() / currentRate * exchangeRate);
			
			quotationProductionDao.updateQuotationProduction(quotationProductionBean);
		}
		if (minPriceAmount.equals(maxPriceAmount)) {
			quotationBean.setQuotationPriceRange(maxPriceAmount.toString());
		} else {
			quotationBean.setQuotationPriceRange(minPriceAmount + "~"
					+ maxPriceAmount);
		}
		quotationDao.updateQuotation(quotationBean);

	}

	@Override
	public Integer queryMaxRemarkIndex() {
		Integer index = quotationDao.queryMaxRemarkIndex();
		if (index == null) {
			index = 0;
		}
		return index;
	}

}
