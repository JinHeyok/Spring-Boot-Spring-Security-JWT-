# 스프링 시큐리티와 JWT를 사용한 사용자 인증 예제 프로젝트
### 이 프로젝트는 아래 내용을 구현한 예제입니다.
- 블로그의 [[Spring Security] Spring Security와 JWT를 사용하여 사용자 인증 구현하기(Spring Boot 3.0.0 이상)](https://colabear754.tistory.com/171)에 작성한 내용을 Java로 구현한 프로젝트
- 스프링 시큐리티를 통해 비밀번호를 암호화하여 DB에 저장
- 스프링 시큐리티를 통해 DB에 저장된 사용자의 계정과 비밀번호로 로그인
- JWT를 사용하여 로그인한 사용자에게 토큰 발급
- 인가된 토큰의 권한에 따라 API 접근 권한 제어


### 환경 정보
- Java 17
- Spring Boot 3.0.6
- Spring Security
- Springdoc 2.1.0
- Spring Data JPA
- My SQL
- Gradle 7.6.1
- Lombok

### 추가 구성
- UserDetails, UserDetailsService Custome 추가 
- yml -> xml 설정 파일 변경
- JPA 설정 변경 ddl-auto Create -> Update
- Spring Security CORS Config
- S3 Configuration , S3Upload Class Update
- JWT , S3 패키지 분리
- JWT.yml -> application.properties 설정 정보 수정
- AbstractDTO 추상화 클래스 추가로 유연한 Response 응답 추가
- MessageDTO , ErrorMessageDTO , StateDTO 유연한 응답 추가
- 기존에 있던 Update 오류 수정 
- Entity 생성 시 레코드 생성일, 업데이트 컬럼 자동 생성
- Entity 레코드 수정 시 수정날짜 자동 업데이트
- 응답 시 생성 날짜 , 수정 날짜 자동 응답 추가
- 전체 권한 Annotation 추가
- EntityMapperConvertDTO 추가 , 각 Entity 별로 데이터 가공 가능 
- 기존에 불필요한 코드 제거

### Branch dev-Excel
- Excel File Data 가공 구성
- Excel GetValue 함수 리팩토링
```java
    // Excel Read
    implementation 'org.apache.poi:poi:5.2.2'
    implementation 'org.apache.poi:poi-ooxml:5.2.2'
```
### Branch dev-Zoom
- Zoom 미팅 생성 API 구현
```java
    // JSON
    implementation 'org.json:json:20230227'
```