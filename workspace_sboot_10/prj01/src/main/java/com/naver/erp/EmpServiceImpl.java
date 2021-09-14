package com.naver.erp;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class EmpServiceImpl implements EmpService{
	@Autowired
	private EmpDAO empDAO;
	
	String uploadDir = "C:\\eroonsung\\workspace_sboot_10\\prj01\\src\\main\\resources\\static\\resources\\img\\";

	public int updateEmp(EmpDTO empDTO,  MultipartFile multi) throws Exception {
		int empCnt = this.empDAO.getEmpCnt(empDTO);
		// 게시판이 존재하지 않으면 -1을 리턴
		if(empCnt==0) {return -1;}
		
		//-------------------------------------------------
		//삭제 체크 여부 구하기
		//만약에 삭제가 체크되어 있지 않으면
		//-------------------------------------------------
		String is_del = empDTO.getIs_del();
		//-------------------------------------------------
		//업로드한 파일의 새로운 이름 정하기 => DB연동하기 전에 이름을 먼저 바꿔야 함
		//-------------------------------------------------
		//업로드한 파일의 새 파일명을 저장할 변수 선언하기. 파일명에는 확장자가 포함됨
		String newFileName = null;
		//만약 업로드된 파일이 있으면
		if(multi!=null && multi.isEmpty()==false && is_del==null) {
			//업로드한 파일의 원래 파일명 얻기. 파일명에는 확장자가 포함됨
			String oriFileName = multi.getOriginalFilename();
			//업로드한 파일의 파일 확장자 얻기
			String file_extension = oriFileName.substring( oriFileName.lastIndexOf(".")+1 );
			
			// 고유한 새 파일명 얻기. 파일명에는 파일 확장자 포함함
			// 시간을 사용하면 겹치지 않는 고유한(중복되지 않는) 새이름을 만들 수 있음
			// 이 또한 겹칠 수 있기 때문에 고유한 이름을 얻을 수 있는 자바의 클래스 사용
			newFileName = UUID.randomUUID()+"."+file_extension; 
			//boardDTO 안에 newFileName을 저장함
			empDTO.setPic(newFileName);
		}
		
		
		
		String pic = this.empDAO.getPic(empDTO);
		
		int updateCnt = this.empDAO.updateEmp(empDTO);
		
		
		//-------------------------------------------------
		//파일업로드 하기
		//업로드 된 파일을 두고 새로운 파일을 만든 후 업로드된 파일을 그대로 오려내서 새로운 파일에 덮어쓰기(stream)
		//-------------------------------------------------
		//is_del이 체크되어 있지 않으면
		if(is_del==null) {
			if(multi!=null && multi.isEmpty()==false) {
				//새 파일을 생성하기. File 객체를 생성하면 새 파일을 생성할 수 있음
				
				//-------------------------------------------------
				//기존 이미지 파일 삭제하기
				//-------------------------------------------------
				if(pic!=null && pic.length()>0) {
					File file = new File(uploadDir+pic);
					file.delete();
				}
				
				File file = new File(uploadDir+newFileName);// 파일 경로를 포함한 생성 파일명
				//업로드한 파일을 새 파일에 전송하여 덮어쓰기
				multi.transferTo(file);
			}
		}	
		//is_del이 체크되어 있으면
		else {
			//-------------------------------------------------
			//기존 이미지 파일 삭제하기
			//-------------------------------------------------
			// board테이블에 이미지 이름이 있으면 파일 삭제하기
			if(pic!=null && pic.length()>0) {
				File file = new File(uploadDir+pic);
				file.delete();
			}
		}
		return updateCnt;
	}
	
	public int deleteEmp(EmpDTO empDTO) {
		int empCnt = this.empDAO.getEmpCnt(empDTO);
		if(empCnt==0) {return -1;}
		
		String pic = this.empDAO.getPic(empDTO);
		
		int updateMgrEmpNoCnt = this.empDAO.updateMgrEmpNo(empDTO);
		int updateCusEmpNoCnt = this.empDAO.updateCusEmpNo(empDTO);
		int deleteCnt = this.empDAO.deleteEmp(empDTO);
		
		//-------------------------------------------------
		//기존 이미지 파일 삭제하기
		//-------------------------------------------------
		if(pic!=null && pic.length()>0) {
			File file = new File(uploadDir+pic);
			file.delete();
		}
		
		return deleteCnt;
	}

	public int insertEmp(EmpDTO empDTO, MultipartFile multi) throws Exception {
		
		//-------------------------------------------------
		//업로드한 파일의 새로운 이름 정하기 => DB연동하기 전에 이름을 먼저 바꿔야 함
		//-------------------------------------------------
		//업로드한 파일의 새 파일명을 저장할 변수 선언하기. 파일명에는 확장자가 포함됨
		String newFileName = null;
		//만약 업로드된 파일이 있으면
		if(multi!=null && multi.isEmpty()==false) {
			//업로드한 파일의 원래 파일명 얻기. 파일명에는 확장자가 포함됨
			String oriFileName = multi.getOriginalFilename();
			//업로드한 파일의 파일 확장자 얻기
			String file_extension = oriFileName.substring( oriFileName.lastIndexOf(".")+1 );
			
			// 고유한 새 파일명 얻기. 파일명에는 파일 확장자 포함함
			// 시간을 사용하면 겹치지 않는 고유한(중복되지 않는) 새이름을 만들 수 있음
			// 이 또한 겹칠 수 있기 때문에 고유한 이름을 얻을 수 있는 자바의 클래스 사용
			newFileName = UUID.randomUUID()+"."+file_extension; 
			//boardDTO 안에 newFileName을 저장함
			empDTO.setPic(newFileName);
		}
		
		int empRegCnt = this.empDAO.insertEmp(empDTO);
		
		//-------------------------------------------------
		//파일업로드 하기
		//업로드 된 파일을 두고 새로운 파일을 만든 후 업로드된 파일을 그대로 오려내서 새로운 파일에 덮어쓰기(stream)
		//-------------------------------------------------
		if(multi!=null && multi.isEmpty()==false) {
			//새 파일을 생성하기. File 객체를 생성하면 새 파일을 생성할 수 있음
			
			File file = new File(uploadDir+newFileName);// 파일 경로를 포함한 생성 파일명
			//업로드한 파일을 새 파일에 전송하여 덮어쓰기
			multi.transferTo(file);
		}

		return empRegCnt;
	}
	

	
}
