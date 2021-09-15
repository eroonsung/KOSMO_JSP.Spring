package com.naver.erp;

//MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
//공용으로 사용할 문자 데이터를 담는 클래스 만들기
//MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
public interface Info {
//public class Info {
	
	//*************************************************
	// 업로드 파일의 저장경로를 저장하는 속성변수 선언하기
	//	<주의> 경로가 문자열이므로 \를 \\로 써야 함. \\대신에 / 쓰지 말 것
	// 복사한 폴더 경로 마지막 img 뒤에 '\\' 쓰는 것 잊지 말기
	//*************************************************
	public static String board_pic_dir
		="C:\\eroonsung\\workspace_sboot_04_withFileUpload\\prj01\\src\\main\\resources\\static\\resources\\img\\";
	
	public static String naverPath = "naver/";

}


