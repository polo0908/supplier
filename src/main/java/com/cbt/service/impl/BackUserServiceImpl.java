package com.cbt.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbt.dao.BackUserDao;
import com.cbt.entity.BackUser;
import com.cbt.service.BackUserService;

@Service
public class BackUserServiceImpl implements BackUserService {

	@Autowired
	private BackUserDao backUserDao;

	@Override
	public List<BackUser> queryBackUserList(String info, Integer start, Integer pageSize,String factoryId) {
		return backUserDao.queryBackUserList(info, start, pageSize,factoryId);
	}

	@Override
	public Integer queryBackUserTotal(String info,String factoryId) {
		return backUserDao.queryBackUserTotal(info,factoryId);
	}

	@Override
	public BackUser queryBackUserById(Integer id) {
		return backUserDao.queryBackUserById(id);
	}

	
	@Transactional
	@Override
	public void insertBackUser(BackUser backUser) {
		backUserDao.insertBackUser(backUser);
		String backUserId = "";
		 if(!(backUser.getId()== null || "".equals(backUser.getId()))){
			   Integer id = 1000 + backUser.getId();
			   backUserId = "b"+ id;
			   backUser.setBackUserid(backUserId);
			   backUserDao.updateBackUser(backUser);
         }
	}

	@Override
	public void updateBackUser(BackUser backUser) {
		backUserDao.updateBackUser(backUser);
	}

	@Override
	public void deleteBackUserById(Integer id) {
		backUserDao.deleteBackUserById(id);
	}

	@Override
	public BackUser login(String username, String pwd) throws Exception {
		// 入口参数检查
		if (username == null || username.trim().isEmpty()) {
			throw new Exception("username can not be empty");
		}
		if (pwd == null || pwd.trim().isEmpty()) {
			throw new Exception("password can not be empty");
		}
		// 从业务层查询用户信息
		BackUser user = backUserDao.queryBackUserByNameAndPsw(username,pwd);
		if (user == null) {
			throw new Exception("This user does not exist");
		}
		return user;
	}

	@Override
	public BackUser queryBackUserByName(String userName) {
		BackUser user = backUserDao.queryBackUserByName(userName);
		return user;
	}

	@Override
	public BackUser queryBackUserByUserId(String userId) {
		BackUser user = backUserDao.queryBackUserByUserId(userId);
		return user;
	}

}
