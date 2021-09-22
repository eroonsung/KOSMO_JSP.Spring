package com.naver.erp;

import java.io.File;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class StaffServiceImpl implements StaffService{

	@Autowired
	private StaffDAO staffDAO;

	String uploadDir = "C:\\eroonsung\\workspace_sboot_staff\\prj01\\src\\main\\resources\\static\\resources\\img\\";

	@Override
	public int insertStaff(StaffDTO staffDTO, MultipartFile multi) throws Exception {
		System.out.println("StaffServiceImpl.insertStaff 시작");
			FileUpload fileUpload = new FileUpload(multi);
			staffDTO.setPic(fileUpload.getNewFileName());
		
			int insertStaffCnt = this.staffDAO.insertStaff(staffDTO);
			
			int insertStaffSkillCnt = this.staffDAO.insertStaffSkill(staffDTO);		
			
			fileUpload.uploadFile(uploadDir);
			
			System.out.println("StaffServiceImpl.insertStaff 완료");
			System.out.println("insertStaffCnt : "+insertStaffCnt);
			
			return insertStaffCnt;
	}
	
	//================================================================================
	@Override
	public int updateStaff(StaffDTO staffDTO, MultipartFile multi) throws Exception {
		System.out.println("StaffServiceImpl.updateStaff 시작");
	int staffCnt = this.staffDAO.getStaffCnt(staffDTO);
		// 게시판이 존재하지 않으면 -1을 리턴
	if(staffCnt==0) {return -1;}
	
	FileUpload fileUpload = new FileUpload(multi);
	String newFileName = null;
	String is_del = staffDTO.getIs_del();
	
	newFileName = fileUpload.getNewFileName(is_del==null);
	staffDTO.setPic(newFileName);
	
	String pic = this.staffDAO.getPic(staffDTO);
	
	int updateCnt = this.staffDAO.updateStaff(staffDTO);
	
	int deleteSkillCnt = this.staffDAO.deleteSkill(staffDTO);
	System.out.println(staffDTO.getSkill_codeList());
	int updateStaffSkillCnt = this.staffDAO.updateStaffSkill(staffDTO);	
	
	if(is_del==null) {
		// 두번째 인자가 true일 경우에만 지워짐
		fileUpload.deleteFile(
				uploadDir+pic
				, multi!=null && multi.isEmpty()==false && pic!=null && pic.length()>0);
		fileUpload.uploadFile(uploadDir);
	}else {
		fileUpload.deleteFile(uploadDir+pic, pic!=null && pic.length()>0);
	}
	System.out.println("StaffServiceImpl.updateStaff 완료");
	return updateCnt;
	}

	//================================================================================
	@Override
	public int deleteStaff(StaffDTO staffDTO) {
		int staffCnt = this.staffDAO.getStaffCnt(staffDTO);
		// 게시판이 존재하지 않으면 -1을 리턴
		if(staffCnt==0) {return -1;}
		
		String pic = this.staffDAO.getPic(staffDTO);
		
		int deleteSkillCnt = this.staffDAO.deleteSkill(staffDTO);
		
		int deleteCnt = this.staffDAO.deleteStaff(staffDTO);
		
		if(pic!=null && pic.length()>0) {
			File file = new File(uploadDir+pic);
			file.delete();
		}
		
		return deleteCnt;
	}
}

	
	

