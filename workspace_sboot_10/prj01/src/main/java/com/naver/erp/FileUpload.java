package com.naver.erp;

import java.io.File;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

public class FileUpload {
	//==========================================
	private MultipartFile multi;
	
	private String newFileName;
	
	//==========================================
	//생성자 만들면서 동시에 새로운 파일 이름 얻기
	public FileUpload(MultipartFile multi) {
		this.multi = multi;
		
		//만약 업로드된 파일이 존재하면
		if(multi!=null && multi.isEmpty()==false) {
			//업로드한 파일의 원래 파일명 얻기. 파일명에는 확장자가 포함됨
			String oriFileName = multi.getOriginalFilename();
			
			//업로드한 파일의 파일 확장자 얻기
			String file_extension = oriFileName.substring( oriFileName.lastIndexOf(".")+1 );
			
			// 고유한 새 파일명 얻기. 파일명에는 파일 확장자 포함함
			// 시간을 사용하면 겹치지 않는 고유한(중복되지 않는) 새이름을 만들 수 있음
			// 이 또한 겹칠 수 있기 때문에 고유한 이름을 얻을 수 있는 자바의 클래스 사용
			newFileName = UUID.randomUUID()+"."+file_extension; 			
		}
	}
	
	//==========================================
	public String getNewFileName() {
		
		return newFileName;
	}
	
	//------------------------------------------
	public String getNewFileName(boolean flag) {
			return flag ? newFileName : null;
		}
	
	//------------------------------------------
	public void uploadFile( String uploadDir ) throws Exception {
		
		if(multi!=null && multi.isEmpty()==false && newFileName!=null && newFileName.length()>0) {
			//새 파일을 생성하기. File 객체를 생성하면 새 파일을 생성할 수 있음
			
			File file = new File(uploadDir + newFileName);// 파일 경로를 포함한 생성 파일명
			//업로드한 파일을 새 파일에 전송하여 덮어쓰기
			multi.transferTo(file);
		}
		
	}
	
	//------------------------------------------
	public void deleteFile( String filePath ) {
		
		File file = new File(filePath);
		file.delete();
		
	}
	
	//------------------------------------------
	public void deleteFile( String filePath, boolean flag ) {
		if(flag==true) {
			deleteFile( filePath );
		}
	}
}


/*
 	FileUpload fileUpload = new FileUpload(multi);
 	
 	board.setPic( fileUpload.getNewFileName() );
 	
 	//DB연동 코드
 	 
 	fileUpload.uploadFile();
 	
 	//fileUpload.deleteFile();
 
 */
