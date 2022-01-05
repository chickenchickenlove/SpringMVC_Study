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

22.01.02
- (수강) 서블릿 필터를 활용한 인증 체크, 요청 로그
- (수강) 스프링 인터셉터를 활용한 인증 체크, 요청 로그
- (수강) ArgumentResolver 직접 구현한 인증 체크
- (수강) 서블릿 예외처리 - 오류 화면 제공, 오류 페이지 작동 원리, 필터, 인터셉터
- (수강) 스프링부트를 활용한 오류페이지 제공
- (코드) 회원 가입 기능 구현
- (코드) 직접 생성한 쿠키를 통한 로그인 기능 및 유지 구현
- (코드) UUID를 이용한 쿠키 생성하고 이를 통한 로그인 기능 및 유지 구현
- (코드) HttpSession을 활용한 로그인 기능 및 유지 구현

22.01.03
- (코드) 서블릿 필터로 로그 필터 구현 
- (코드) 서블릿 필터로 로그인 체크 기능 구현
- (코드) 스프링 인터셉터로 로그 필터 구현
- (코드) 스프링 인터셉터로 로그인 체크 기능 구현
- (코드) Argument Resolver로 직접 구현한 인증 체크

22.01.04
- (코드) 서블릿 필터로 로거, 로그인 체크 기능 구현 복습
- (코드) 스프링 인터셉터로 로거, 로그인 체크 기능 구현 복습
- (코드) Argument Resolver로 직접 구현한 인증 체크 복습
- (코드) ErrorPage를 통한 User 지정 Error 페이지 구현
- (코드) 서블릿 필터, 인터셉터에서 Error 발생 시 Skip 구현
- (코드) BasicErrorController를 활용한 Error 페이지 사용

22.01.05
- (강의) 스프링 부트 기본 오류 API 처리 수강
- (강의) HandlerExceptionResolver에 대한 개념 및 활용 수강
- (강의) 스프링이 제공하는 ExceptionResolver를 직접 구현하여 오류 처리하는 방법 수강
- (강의) @ExceptionHandler를 활용한 오류 처리 수강
- (강의) @ControllerAdvice로 관심사 분리를 통해 코드 리팩토링 수강
