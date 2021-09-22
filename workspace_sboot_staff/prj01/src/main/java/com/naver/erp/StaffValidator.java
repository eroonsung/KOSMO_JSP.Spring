package com.naver.erp;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;


//MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
// BoardDTO 객체에 저장된 데이터의 유효성 체크할 BoardValidator 클래스 선언하기
//MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
	// Validator : 스프링이 제공하는 인터페이스 중 하나
public class StaffValidator implements Validator {

	//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
	//  유효성 체크할 객체의 클래스 타입 정보 얻어 리턴하기
	//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
	@Override
	public boolean supports(Class<?> arg0) {
		return StaffDTO.class.isAssignableFrom(arg0);  // 검증할 객체의 클래스 타입 정보
	}

	//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
	//  유효성 체크할 메소드 선언하기
	//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
	@Override
	public void validate(
		// Object객체 사용 -> 뭐가 들어올 지 모를 때/ 뭐가 리턴될 지 모를 때
		Object obj          // DTO 객체 저장 매개변수 => BoardDTO boardDTO 의 조상
		, Errors errors     // 유효성 검사 시 발생하는 에러를 관리하는 Errors 객체 저장 매개변수 => BindingResult bindingResult의 조상
	){
		try {
			//nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn
			// 유효성 체크할 DTO 객체 얻기
			//nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn
			// 자기 자료형으로 복구시키는 이유
				//: 부모 자료형으로 쓰면 본인이 가지고 있는 고유 메서드를 호출 불가능하기 때문
			StaffDTO dto = (StaffDTO)obj; //DTO 객체의 형변환

			//        String sWriter = dto.getWriter();
			//        if(sWriter == null || sWriter.trim().isEmpty()) {
			//            System.out.println("Writer is null or empty");
			//            errors.rejectValue("writer", "trouble");
			//        }


			//nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn
			//nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn
			// ValidationUtils 클래스의 rejectIfEmptyOrWhitespace 메소드 호출하여
			//		BoardDTO 객체의 속성변수명 writer 이 비거나(null값도 포함) 공백으로 구성되어 있으면
			//		경고 메시지를 Errors 객체에 저장하기
			// static이 붙어 객체 생성 없이 사용할 수 있는 클래스 : ValidationUtils
			//nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn
			ValidationUtils.rejectIfEmptyOrWhitespace(
				errors                       // Errors 객체(실제로는 BindingResult객체)
				, "staff_name"                   // BoardDTO 객체의 속성변수명
				, "직원명을 입력하세요."         // BoardDTO 객체의 속성변수명이 비거나 공백으로 구성되어 있을때 경고 문구
			);
			String staff_name = dto.getStaff_name();
			if( Pattern.matches("[ㄱ-ㅎ|가-힣|a-z|A-Z]+", staff_name)==false ) {
				errors.rejectValue("staff_name", "직원명에는 한글과 영문만 입력할 수 있습니다.");
			}

			//nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn
			//nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn
			// ValidationUtils 클래스의 rejectIfEmptyOrWhitespace 메소드 호출하여
			//		BoardDTO 객체의 속성변수명 writer 이 비거나 공백으로 구성되어 있으면
			//		경고 메시지를 Errors 객체에 저장하기
			//nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn
			ValidationUtils.rejectIfEmptyOrWhitespace(
				errors                       // Errors 객체
				, "jumin_no1"                   // BoardDTO 객체의 속성변수명
				, "주민번호를 입력하세요."         // BoardDTO 객체의 속성변수명이 비거나 공백으로 구성되어 있을때 경고 문구
			);
			String jumin_no1 = dto.getJumin_no1();
			if( Pattern.matches("[0-9]+", jumin_no1)==false ) {
				errors.rejectValue("jumin_no1", "주민번호에는 숫자만 입력하세요.");
			}
			//nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn
			//nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn
			// ValidationUtils 클래스의 rejectIfEmptyOrWhitespace 메소드 호출하여
			//		BoardDTO 객체의 속성변수명 writer 이 비거나 공백으로 구성되어 있으면
			//		경고 메시지를 Errors 객체에 저장하기
			//nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn
			ValidationUtils.rejectIfEmptyOrWhitespace(
				errors                       // Errors 객체
				, "jumin_no2"                   // BoardDTO 객체의 속성변수명
				, "주민번호를 입력하세요."        // BoardDTO 객체의 속성변수명이 비거나 공백으로 구성되어 있을때 경고 문구
			);
			String jumin_no2 = dto.getJumin_no2();
			if( Pattern.matches("[0-9]+", jumin_no2)==false ) {
				errors.rejectValue("jumin_no2", "주민번호에는 숫자만 입력하세요.");
			}
			
			int religion_code = dto.getReligion_code();
			if(religion_code==0) {
				errors.rejectValue("religion_code", "종교를 선택하세요.");
			}
			
			int school_code = dto.getSchool_code();
			if(school_code<1) {
				errors.rejectValue("school_code", "학력을 선택하세요.");
			}

			/*
			int skill_code = dto.getSkill_code();
			if(skill_code<1) {
				errors.rejectValue("skill_code", "기술을 선택하세요.");
			}
			*/
			List<String> skill_codeList = dto.getSkill_codeList();
			if(skill_codeList==null) {
				errors.rejectValue("skill_codeList", "기술을 선택하세요.");
			}
			
			ValidationUtils.rejectIfEmptyOrWhitespace(
					errors                       // Errors 객체
					, "graduate_day_ofYear"                   // BoardDTO 객체의 속성변수명
					, "졸업일을 선택하세요."        // BoardDTO 객체의 속성변수명이 비거나 공백으로 구성되어 있을때 경고 문구
				);
			ValidationUtils.rejectIfEmptyOrWhitespace(
					errors                       // Errors 객체
					, "graduate_day_ofMonth"                   // BoardDTO 객체의 속성변수명
					, "졸업일을 선택하세요."        // BoardDTO 객체의 속성변수명이 비거나 공백으로 구성되어 있을때 경고 문구
				);
			ValidationUtils.rejectIfEmptyOrWhitespace(
					errors                       // Errors 객체
					, "graduate_day_ofDate"                   // BoardDTO 객체의 속성변수명
					, "졸업일을 선택하세요."        // BoardDTO 객체의 속성변수명이 비거나 공백으로 구성되어 있을때 경고 문구
				);
			

			String year = dto.getGraduate_day_ofYear();
			String month = dto.getGraduate_day_ofMonth();
			String date = dto.getGraduate_day_ofDate();
			
			String checkDateStr =  year+"-"+month+"-"+date;
			if(checkDate(checkDateStr)==false) {
				errors.rejectValue("graduate_day_ofDate", "잘못된 날짜입니다.");
			}
			
			
		}catch(Exception ex) {
			System.out.println( "StaffValidator.validate 메소드 실행 시 예외발생!" );
		}
	}
	
    public static boolean checkDate(String checkDate) {
        try {
            SimpleDateFormat dateFormatParser = new SimpleDateFormat("yyyy-MM-dd"); //검증할 날짜 포맷 설정
            dateFormatParser.setLenient(false); //false일경우 처리시 입력한 값이 잘못된 형식일 시 오류가 발생
            dateFormatParser.parse(checkDate); //대상 값 포맷에 적용되는지 확인
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}

