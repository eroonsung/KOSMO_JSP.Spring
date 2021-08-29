package com.naver.erp;

import java.util.Map;

public interface LoginDAO {
	//***********************************************
	int getLogin_idCnt(Map<String,String> id_pwd_map);
}
