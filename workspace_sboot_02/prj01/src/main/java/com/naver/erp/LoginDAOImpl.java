package com.naver.erp;

import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class LoginDAOImpl implements LoginDAO{
	//++++++++++++++++++++++++++++++++++++++++++++++++++
	//속성변수 sqlSession을 선언하고, [SqlSessionTemplate 객체]를 생성해 저장
	//++++++++++++++++++++++++++++++++++++++++++++++++++
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	//***********************************************
	//로그인 아이디와 암호 존재의 개수를 리턴하는 메소드 선언
	//***********************************************
	public int getLogin_idCnt(Map<String, String> id_pwd_map) {
		System.out.println("		LoginDAOImpl.getLogin_idCnt 메소드 시작");
		int login_idCnt = this.sqlSession.selectOne(
				"com.naver.erp.LoginDAO.getLogin_idCnt"
				, id_pwd_map				
		);
		System.out.println("		LoginDAOImpl.getLogin_idCnt 메소드 완료");
		return login_idCnt;
	}
}
