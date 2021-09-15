package com.naver.erp;

import org.springframework.web.multipart.MultipartFile;

public interface EmpService {

	int updateEmp(EmpDTO empDTO, MultipartFile multi) throws Exception;

	int deleteEmp(EmpDTO empDTO);

	int insertEmp(EmpDTO empDTO, MultipartFile multi) throws Exception;

}
