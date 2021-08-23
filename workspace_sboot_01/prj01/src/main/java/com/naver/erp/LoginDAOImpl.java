package com.naver.erp;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.mybatis.spring.SqlSessionTemplate;

@Repository
public class LoginDAOImpl implements LoginDAO{
	@Autowired
	private SqlSessionTemplate sqlSession;

	public int getLogin_idCnt( Map<String,String> id_pwd_map) {
		int login_idCnt = this.sqlSession.selectOne(
				"com.naver.erp.LoginDAO.getLogin_idCnt"
				, id_pwd_map
				);
		return login_idCnt;
	}
}

