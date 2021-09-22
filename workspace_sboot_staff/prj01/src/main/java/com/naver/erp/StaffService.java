package com.naver.erp;

import org.springframework.web.multipart.MultipartFile;

public interface StaffService {
 
	int insertStaff(StaffDTO staffDTO, MultipartFile multi) throws Exception;

	int updateStaff(StaffDTO staffDTO, MultipartFile multi) throws Exception;

	int deleteStaff(StaffDTO staffDTO);




}
