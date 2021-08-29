package com.naver.erp;

import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class BoardValidator implements Validator {

	//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
	//  유효성 체크할 객체의 클래스 타입 정보 얻어 리턴하기
	//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
	@Override
	public boolean supports(Class<?> arg0) {
		return BoardDTO.class.isAssignableFrom(arg0);
	}

	//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
	//  유효성 체크할 메소드 선언하기
	//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
	@Override
	public void validate(Object obj, Errors errors) {
		try {
			BoardDTO dto = (BoardDTO)obj;
			
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "writer", "작성자명 입력요망");
			String writer = dto.getWriter();
			if( writer!=null && writer.length() >10) {
				errors.rejectValue("writer", "작성자명은 공백없이 10자이하 입니다.");
			}
			
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "subject", "제목 입력요망");
			String subject = dto.getSubject();
			if( subject!=null && subject.length() >20) {
				errors.rejectValue("subject", "제목은 20자이하 입니다.");
				
			ValidationUtils.rejectIfEmptyOrWhitespace(errors , "email", "이메일 입력요망");
			
			ValidationUtils.rejectIfEmptyOrWhitespace(errors , "content", "내용 입력요망");
			String content = dto.getContent();
			if( content!=null && content.length() >300) {
				errors.rejectValue("content", "내용은 공백없이 300자이하 입니다.");
			}
			
			ValidationUtils.rejectIfEmptyOrWhitespace(errors , "pwd", "암호 입력요망");
			String pwd = dto.getPwd();
			if( Pattern.matches("^[0-9]{4}$", pwd)==false ) {
				errors.rejectValue("pwd", "암호는 숫자 4자리 입니다. 재입력 요망");
			}
			}
		}catch(Exception e) {
			System.out.println( "BoardValidator.validate 메소드 실행 시 예외발생!" );
		}
	}
    
}
