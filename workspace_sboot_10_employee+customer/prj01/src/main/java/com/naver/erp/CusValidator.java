package com.naver.erp;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

//MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
// BoardDTO 객체에 저장된 데이터의 유효성 체크할 BoardValidator 클래스 선언하기
//MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
	// Validator : 스프링이 제공하는 인터페이스 중 하나
public class CusValidator implements Validator {

	//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
	//  유효성 체크할 객체의 클래스 타입 정보 얻어 리턴하기
	//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
	@Override
	public boolean supports(Class<?> arg0) {
		return CusDTO.class.isAssignableFrom(arg0);  // 검증할 객체의 클래스 타입 정보
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
			CusDTO dto = (CusDTO)obj; //DTO 객체의 형변환

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
				, "cus_name"                   // BoardDTO 객체의 속성변수명
				, "고객명 입력요망"         // BoardDTO 객체의 속성변수명이 비거나 공백으로 구성되어 있을때 경고 문구
			);
			//위에서 경고 메시지가 들어가면 아래 코드를 실행해도 메시지가 저장되지 ㅇ낳음
			//nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn
			// BoardDTO 객체의 속성변수명 "writer" 저장된 데이터의 길이가 10자 보다 크면
			// Errors 객체에 속성변수명 "writer" 과 경고 메시지 저장하기
			//nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn
			String cus_name = dto.getCus_name();
			// writer가 null이면 .length()메소드를 호출할 수 없기 때문에 writer!=null조건을 먼저 써줘야함
			if( cus_name!=null && cus_name.length() >10) {
				errors.rejectValue(
						"cus_name" // 속성변수명
						, "고객명은 공백없이 10자이하 입니다." //경고 메시지
				);
			}


			//nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn
			//nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn
			// ValidationUtils 클래스의 rejectIfEmptyOrWhitespace 메소드 호출하여
			//		BoardDTO 객체의 속성변수명 writer 이 비거나 공백으로 구성되어 있으면
			//		경고 메시지를 Errors 객체ㅇ에 저장하기
			//nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn
			ValidationUtils.rejectIfEmptyOrWhitespace(
				errors                    // Errors 객체
				, "jumin_num"                 // BoardDTO 객체의 속성변수명
				, "주민번호 입력요망"         // BoardDTO 객체의 속성변수명이 비거나 공백으로 구성되어 있을때 경고 문구
			);
			//nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn
			// BoardDTO 객체의 속성변수명 "content" 저장된 데이터의 길이가 30자 보다 크면
			// Errors 객체에 속성변수명 "content" 과 경고 메시지 저장하기
			//nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn
			String jumin_num = dto.getJumin_num();
			if( jumin_num!=null && jumin_num.length() >14) {
				errors.rejectValue("jumin_num", "주민번호는 '-'없이 13자입니다.");
			}


			//nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn
			//nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn
			// ValidationUtils 클래스의 rejectIfEmptyOrWhitespace 메소드 호출하여
			//		BoardDTO 객체의 속성변수명 writer 이 비거나 공백으로 구성되어 있으면
			//		경고 메시지를 Errors 객체ㅇ에 저장하기
			//nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn
			ValidationUtils.rejectIfEmptyOrWhitespace(
				errors                    // Errors 객체
				, "tel_num"                 // BoardDTO 객체의 속성변수명
				, "전화번호 입력요망"         // BoardDTO 객체의 속성변수명이 비거나 공백으로 구성되어 있을때 경고 문구
			);
			//nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn
			// BoardDTO 객체의 속성변수명 "content" 저장된 데이터의 길이가 30자 보다 크면
			// Errors 객체에 속성변수명 "content" 과 경고 메시지 저장하기
			//nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn
			String tel_num = dto.getTel_num();
			if( tel_num!=null && tel_num.length() >12) {
				errors.rejectValue("tel_num", "전화번호는 '-'없이 11자입니다.");
			}
			//nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn
			//nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn
			// ValidationUtils 클래스의 rejectIfEmptyOrWhitespace 메소드 호출하여
			//		BoardDTO 객체의 속성변수명 writer 이 비거나 공백으로 구성되어 있으면
			//		경고 메시지를 Errors 객체에 저장하기
			//nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn
			ValidationUtils.rejectIfEmptyOrWhitespace(
				errors                       // Errors 객체
				, "emp_no"                   // BoardDTO 객체의 속성변수명
				, "담당직원 입력요망"         // BoardDTO 객체의 속성변수명이 비거나 공백으로 구성되어 있을때 경고 문구
			);
			//nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn
			// BoardDTO 객체의 속성변수명 "subject" 저장된 데이터의 길이가 20자 보다 크면
			// Errors 객체에 속성변수명 "subject" 과 경고 메시지 저장하기
			//nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn
			int emp_no = dto.getEmp_no();
			if(emp_no!=0 && emp_no>99) {
				errors.rejectValue("emp_no", "담당직원 번호는 100 이하 입니다.");
			}

		}catch(Exception ex) {
			System.out.println( "BoardValidator.validate 메소드 실행 시 예외발생!" );
		}
	}
    
}
