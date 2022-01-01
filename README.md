# SpringMVC_Study
Spring MVC를 공부하며 복습한 내용을 커밋하고 있습니다.
공부한 내용은 일자 단위로 정리해서 올리고 있습니다.


21.12.28
- Validation → RejectValue, Reject를 활용한 Validation 학습

21.12.29
- Validator implemnts를 통한 Validation 로직 분리 학습
- WebDataBinder, @InitBinder, @Validate를 이용한 Validation 코드 리팩토링. 
- Bean Validation을 활용한 General Field Validation 코드 리팩토링
- Bean Validation을 활용한 Getneral Field Validation + WebDataBinder를 통한 Local Validator 적용으로 GlobalError 검출 리팩토링.

21.12.30 
- (수강) Servlet Http Session을 활용한 로그인 처리 
- Bean Validation : EditForm.html의 Thymeleaf 수정. (Validation 결과 적용)
- Bean Validation : EditForm에 Global Validator + Local Validator 적용.
- Bean Validation : Groups를 적용하여 여러 경계 조건을 여러 Controller에 적용함


21.12.31
- (수강) Servlet Http Session을 활용한 코드 리팩토링
- (수강) Session 정보를 읽어오고 이를 통한 TimeOut 설정으로 로그인 보안 개선 방법
- (수강) 서블릿 필터, 서블릿 필터를 이용한 로깅, 서블릿 필터를 이용한 인증
- (코드) Bean Validation Form 객체를 도입해 Groups를 사용하지 않고 여러 경계 조건을 Valdiation.
- (코드) HttpMessageConverter를 활용한 Validated 처리.

22.01.01
- (코드) BindingResult + new FieldError, ObjectError를 활용한 에러 처리.
- (코드) FieldError, ObjectError를 활용했을 때 Thymeleaf로 동적으로 에러 메세지 처리하기.
- (코드) BindinResult + rejectValue , reject의 MessageCodesResolver를 활용한 동적 메세지 출력하기.
