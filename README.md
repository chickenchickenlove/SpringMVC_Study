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
- (코드) BindingResult를 이용한 Validation 정리 (https://ojt90902.tistory.com/672)

22.01.01
- (코드) BindingResult + new FieldError, ObjectError를 활용한 에러 처리.
- (코드) FieldError, ObjectError를 활용했을 때 Thymeleaf로 동적으로 에러 메세지 처리하기.
- (코드) BindinResult + rejectValue , reject의 MessageCodesResolver를 활용한 동적 메세지 출력하기.
- (정리) Bean Validation 관련 정리 (https://ojt90902.tistory.com/673)

22.01.02
- (수강) 서블릿 필터를 활용한 인증 체크, 요청 로그
- (수강) 스프링 인터셉터를 활용한 인증 체크, 요청 로그
- (수강) ArgumentResolver 직접 구현한 인증 체크
- (수강) 서블릿 예외처리 - 오류 화면 제공, 오류 페이지 작동 원리, 필터, 인터셉터
- (수강) 스프링부트를 활용한 오류페이지 제공
- (정리) 로그인 기능 구현 관련 정리 (https://ojt90902.tistory.com/674)
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
- (강의) Spring Converter 개념 수강
- (강의) Spring Converter를 Conversion Service에 등록해서 사용하는 방법
- (강의) Converter를 SpringBoot를 활용해서 적용하기
- (강의) ViewTemplate에 Converter 적용하기
- (강의) Formatter 개념 수강
- (강의) Formatter를 지원하는 컨버전 서비스 사용해보기
- (강의) Formatter를 활용한 테스트 코드 테스트
- (강의) Formatter 적용 및 SpringBoot를 활용해서 적용하기
- (강의) 서블릿 기술을 이용한 파일 업로드 구현
- (강의) 스프링 기술을 이용한 파일 업로드 구현
- (강의) 스프링 기술을 이용한 파일 업로드, 다운로드의 웹개발 구현

22.01.06
- (정리) Servlet Error Page, Spring이 제공하는 Error Page 정리 (https://ojt90902.tistory.com/676)
- (코드) Error 유발 컨트롤러 구현해보기
- (코드) Servlet의 WebCustomizer를 이용한 ErrorPage 및 Error Controller 구현
- (코드) Spring MVC의 BasicErrorController를 이용한 Error Page 구현
- (코드) Exception 상황 발생 시, Servlet Filter Skip 구현
- (코드) Exception 상황 발생 시, Spring Interceptor Skip 
- (정리) HTTp APi 예외 처리 관련 개념 및 코드 정리(https://ojt90902.tistory.com/677)
- (코드) HTTP API 예외 처리에 대한 개론 정리
- (코드) Servlet의 ErrorPageController + produce Type을 통한 JSON HTTP API 예외 구현
- (코드) Spring의 HandlerException Resolver를 이용한 JSON HTTP API 예외 구현
- (코드) Spring의 HandlerException Resolver를 이용하여 Exception 삭제 후, 정상 처리하기
- (코드) Spring MVC의 ResponseExceptionResolver를 이용한 Exception Catch 및 처리 구현
- (코드) Spring MVC의 DefaultExceptionREsolver를 이용한 Type Mismatch Exception Catch 및 처리 구현
- (코드) Spring MVC의 ExceptionHandlerResolver를 활용한 유연한 Exception 처리 
- (코드) ControllerAdvice를 활용한 ExceptionHandlerResolver와 다른 Controller의 분리 코드 리팩토링 처리


22.01.07
- (정리) Converter, Formatter의 개념 및 사용 방법 정리 (https://ojt90902.tistory.com/679)
- (코드) Primitive Type Converter 구현 및 적용
- (코드) Primitive ↔ User Object Type Converter 구현 및 적용 + TEST 코드 작성
- (코드) User Define Converter Spring MVC 등록 및 사용. 타임리프 th:field와 함께 적용 
- (코드) User Define Formmater 구현 + TEST 코드 작성
- (코드) User Define Formater Spring MVC 등록 및 사용. 타임리프 th:field와 함께 적용 
- (코드) 어노테이션 기반 @NumberFormat, @DateTimeFormat 사용. 타임리프 th:field와 함께 적용

22.01.08
- (정리) Spring을 이용한 파일, 이미지 업로드 / 다운로드 (https://ojt90902.tistory.com/681)
- (코드) Servlet의 HttpServeltRequest를 이용한 파일 업로드
- (코드) Spring의 MultipleFile을 이용한 파일 업로드
- (코드) 다중 이미지, 싱글 파일 업로드 구현
- (코드) 다중 이미지 다운로드(화면 렌더링) + 싱글 파일 다운로드 구현 


#22.02.18
- (정리) 타임리프 정적 리소스 / 컨트롤러 맵핑 개념 정리(https://ojt90902.tistory.com/732)
