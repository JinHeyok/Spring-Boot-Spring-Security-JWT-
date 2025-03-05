package com.colabear754.authentication_example_java.handler;

import com.colabear754.authentication_example_java.DTO.ApiResponse;
import com.colabear754.authentication_example_java.enums.ErrorMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.PropertyValueException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.nio.file.NoSuchFileException;
import java.security.GeneralSecurityException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestControllerAdvice
public class ExceptionResponseHandler {

    @RestControllerAdvice
    @Slf4j
    public class ExceptionHandlerResponse {


        /**
         * BadRequestException에 대한 예외를 해당 메소드에서 처리(DB 데이터 또는 잘못된 요청일 경우)
         *
         * @param e BadRequestException
         * @return ResponseEntity<ApiResponse>
         */
        @ExceptionHandler({BadRequestException.class})
        public ResponseEntity<ApiResponse> handlerBadRequestException(BadRequestException e) {
            log.info("BadRequestException : {}", e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ApiResponse.error(e.getMessage()));
        }

        /**
         * AccessDeniedException 대한 예외를 해당 메소드에서 처리 (인가 권한이 없을 경우)
         *
         * @return ResponseEntity<ApiResponse>
         */
        @ExceptionHandler(AccessDeniedException.class)
        public ResponseEntity<ApiResponse> handleAccessDeniedException() {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(ApiResponse.error(ErrorMessage.PERMISSION_NOT_FOUND.getMessage()));
        }

        /**
         * private으로 선언된 멤버에 다른 클래스에서 접근하려고 할 때 발생하는 예외를 해당 메소드에서 처리
         *
         * @param e IllegalAccessException
         * @return ResponseEntity<ApiResponse>
         */
        @ExceptionHandler(IllegalAccessException.class)
        public ResponseEntity<ApiResponse> handleIllegalAccessException(IllegalAccessException e) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ApiResponse.error(e.getMessage()));
        }

        /**
         * 존재하지 않는 API를 요청했을 때 발생하는 예외를 해당 메소드에서 처리
         *
         * @return ResponseEntity<ApiResponse>
         */
        @ExceptionHandler(NoResourceFoundException.class)
        public ResponseEntity<ApiResponse> handlerNoResourceFoundException() {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(ErrorMessage.API_NOT_FOUND.getMessage()));
        }

        /**
         * 잘못된 메서드로 요청했을 경우 발생하는 예외를 해당 메소드에서 처리
         *
         * @return ResponseEntity<ApiResponse>
         */
        @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
        public ResponseEntity<ApiResponse> handlerHttpRequestMethodNotSupportedException() {
            return ResponseEntity
                    .status(HttpStatus.METHOD_NOT_ALLOWED)
                    .body(ApiResponse.error(ErrorMessage.METHOD_NOT_ALLOWED.getMessage()));
        }

        /**
         * JPA 실행 쿼리 , 속성 오류 시 해당 메소드에서 처리
         *
         * @param e JpaSystemException
         * @return ResponseEntity<ApiResponse>
         */
        @ExceptionHandler(JpaSystemException.class)
        public ResponseEntity<ApiResponse> handlerJpaSystemException(JpaSystemException e) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ApiResponse.error(e.getMessage()));
        }

        /**
         * API 파라미터가 없을 때 해당 메소드에서 처리
         *
         * @param e MissingServletRequestParameterException
         * @return ResponseEntity<ApiResponse>
         */
        @ExceptionHandler(MissingServletRequestParameterException.class)
        public ResponseEntity<ApiResponse> handlerMissingServletRequestParameterException(MissingServletRequestParameterException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(ErrorMessage.MISSING_PARAMETER.getMessage() + e.getMessage()));
        }

        /**
         * 파라미터(RequestPart)가 없을 때 해당 메소드에서 처리
         *
         * @param e MissingServletRequestPartException
         * @return ResponseEntity<ApiResponse>
         */
        @ExceptionHandler(MissingServletRequestPartException.class)
        public ResponseEntity<ApiResponse> handlerMissingServletRequestPartException(MissingServletRequestPartException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(ErrorMessage.MISSING_PARAMETER.getMessage() + e.getMessage()));
        }

        /**
         * 주로 JSON,XML 형식의 데이터를 바디의 전송 안할 경우 해당 메소드에서 처리
         *
         * @param e HttpMessageNotReadableException
         * @return ResponseEntity<ApiResponse>
         */
        @ExceptionHandler(HttpMessageNotReadableException.class)
        public ResponseEntity<ApiResponse> handlerHttpMessageNotReadableException(HttpMessageNotReadableException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(ErrorMessage.BODY_DATA_NOT_FOUND.getMessage() + e.getMessage()));
        }

        /**
         * JPA 쿼리 오류 시 해당 메소드에서 처리
         *
         * @param e InvalidDataAccessApiUsageException
         * @return ResponseEntity<ApiResponse>
         */
        @ExceptionHandler(InvalidDataAccessApiUsageException.class)
        public ResponseEntity<ApiResponse> handlerInvalidDataAccessApiUsageException(InvalidDataAccessApiUsageException e) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ApiResponse.error(ErrorMessage.INVALID_DATA_ACCESS_API_ERROR.getMessage() + e.getMessage()));
        }

        /**
         * Mysql Data Truncation 오류 시 지정된 컬럼의 값이 너무 큰 경우 해당 메소드에서 처리
         *
         * @param e MysqlDataTruncation
         * @return ResponseEntity<ApiResponse>
         */
        @ExceptionHandler(MysqlDataTruncation.class)
        public ResponseEntity<ApiResponse> handlerMysqlDataTruncation(MysqlDataTruncation e) {
            Pattern pattern = Pattern.compile("'(.*?)'");
            Matcher matcher = pattern.matcher(e.getMessage());
            String extractedValue = matcher.find() ? matcher.group(1) : "Unknown column";
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ApiResponse.error(ErrorMessage.MYSQL_DATA_TRUNCATION.getMessage() + "\n " +
                            "'" + extractedValue + "' 의 값이 너무 큽니다. \n" +
                            "최대 길이를 확인해주세요."));
        }

        /**
         * SQL Integrity Constraint Violation 오류 시 (데이터베이스 제약조건 위반) 해당 메소드에서 처리
         *
         * @param e SQLIntegrityConstraintViolationException
         * @return ResponseEntity<ApiResponse>
         */
        @ExceptionHandler({SQLIntegrityConstraintViolationException.class})
        public ResponseEntity<ApiResponse> handlerSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e) {
            log.info("==========> SQLIntegrityConstraintViolationException {}", e.getMessage());
            Pattern pattern = Pattern.compile("'(.*?)'");
            Matcher matcher = pattern.matcher(e.getMessage());
            String extractedValue = matcher.find() ? matcher.group(1) : "Unknown column";
            if (e.getMessage().contains("not-null")) {
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(ApiResponse.error(ErrorMessage.SQL_INTEGRITY_CONSTRAINT_VIOLATION.getMessage() + "\n " +
                                "'" + extractedValue + "' 의 값은 필수값입니다. \n" +
                                "데이터를 확인해주세요."));
            } else if (e.getMessage().contains("Duplicate entry")) { // NOTE Unique를 위반할 경우
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(ApiResponse.error(ErrorMessage.SQL_DUPLICATE_ENTRY.getMessage() + "\n " +
                                "'" + extractedValue + "' 의 값은 중복값입니다. \n" +
                                "데이터를 확인해주세요."));
            }
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ApiResponse.error(ErrorMessage.INCORRECT_DATA.getMessage() + "\n " +
                            "'" + extractedValue + "' 의 값이 잘못되었습니다. \n" +
                            "데이터를 확인해주세요."));
        }

        /**
         * PropertyValueException 오류 시 (데이터베이스 제약조건 위반) 해당 메소드에서 처리
         *
         * @param e PropertyValueException
         * @return ResponseEntity<ApiResponse>
         */
        @ExceptionHandler({PropertyValueException.class})
        public ResponseEntity<ApiResponse> handlerPropertyValueException(PropertyValueException e) {
            log.info("==========> handlerPropertyValueException {}", e.getMessage());
            String[] parts = e.getMessage().split(":");
            String splitStr = parts[parts.length - 1];
            String extractedValue = splitStr.split("\\.")[(splitStr.split("\\.")).length - 1]; // NOTE 컬럼명 가져오기
            if (e.getMessage().contains("not-null")) { // NOTE  NOT NULL 일 경우
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(ApiResponse.error(ErrorMessage.SQL_INTEGRITY_CONSTRAINT_VIOLATION.getMessage() + "\n " +
                                "'" + extractedValue + "' 의 값은 필수값입니다. \n" +
                                "데이터를 확인해주세요."));
            } else if (e.getMessage().contains("Duplicate entry")) { // NOTE Unique를 위반할 경우
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(ApiResponse.error(ErrorMessage.SQL_DUPLICATE_ENTRY.getMessage() + "\n " +
                                "'" + extractedValue + "' 의 값은 중복값입니다. \n" +
                                "데이터를 확인해주세요."));
            }
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ApiResponse.error(ErrorMessage.INCORRECT_DATA.getMessage() + "\n " +
                            "'" + extractedValue + "' 의 값이 잘못되었습니다. \n" +
                            "데이터를 확인해주세요."));
        }

        /**
         * URI 문법 오류 시 해당 메소드에서 처리
         *
         * @param e URISyntaxException
         * @return ResponseEntity<ApiResponse>
         */
        @ExceptionHandler(URISyntaxException.class)
        public ResponseEntity<ApiResponse> handlerURISyntaxException(URISyntaxException e) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ApiResponse.error(ErrorMessage.URI_SYNTAX_ERROR.getMessage()));
        }

        /**
         * 파라미터 타입이 일치하지 않을 때 해당 메소드에서 처리
         *
         * @param e MethodArgumentTypeMismatchException
         * @return ResponseEntity<ApiResponse>
         */
        @ExceptionHandler(MethodArgumentTypeMismatchException.class)
        public ResponseEntity<ApiResponse> handlerMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ApiResponse.error(ErrorMessage.METHOD_ARGUMENT_TYPE_MISMATCH.getMessage()));
        }

        /**
         * 미디어 타입이 지원되지 않을 때 해당 메소드에서 처리
         *
         * @param e HttpMediaTypeNotSupportedException
         * @return ResponseEntity<ApiResponse>
         */
        @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
        public ResponseEntity<ApiResponse> handlerHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ApiResponse.error(ErrorMessage.MEDIA_TYPE_NOT_SUPPORTED.getMessage()));
        }


        /**
         * PropertyReferenceException 오류 시 (데이터베이스 제약조건 위반) 해당 메소드에서 처리
         *
         * @param e PropertyReferenceException
         * @return ResponseEntity<ApiResponse>
         */
        @ExceptionHandler(PropertyReferenceException.class)
        public ResponseEntity<ApiResponse> handlerPropertyReferenceException(PropertyReferenceException e) {
            String message = e.getMessage();
            Pattern pattern = Pattern.compile("'(.*?)'");
            Matcher matcher = pattern.matcher(message);
            String propertyName = matcher.find() ? matcher.group(1) : "Unknown Property";
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ApiResponse.error(ErrorMessage.PROPERTY_REFERENCE_ERROR.getMessage() + propertyName));
        }

        /**
         * IllegalArgumentException 오류 시 잘못된 인수가 전달되었을 때 해당 메소드에서 처리
         *
         * @param e IllegalArgumentException
         * @return ResponseEntity<ApiResponse>
         */
        @ExceptionHandler(IllegalArgumentException.class)
        public ResponseEntity<ApiResponse> handlerIllegalArgumentException(IllegalArgumentException e) {
            String message = e.getMessage();
            Pattern pattern = Pattern.compile("\\.([^\\.]+)$");
            Matcher matcher = pattern.matcher(message);
            String propertyName = matcher.find() ? matcher.group(1) : "Unknown Argument";
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ApiResponse.error(ErrorMessage.ILLEGAL_ARGUMENT_ERROR.getMessage() + propertyName));
        }

        /**
         * NoSuchFileException 오류 시 특정 필드를 찾을 수 없을 때 해당 메소드에서 처리
         *
         * @param e NoSuchFileException
         * @return ResponseEntity<ApiResponse>
         */
        @ExceptionHandler(NoSuchFileException.class)
        public ResponseEntity<ApiResponse> handlerNoSuchFileException(NoSuchFileException e) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ApiResponse.error(ErrorMessage.NO_SUCH_FILE_ERROR.getMessage() + e.getMessage()));
        }

        /**
         * InvocationTargetException 오류 시 메소드 호출 오류가 발생했을 때 해당 메소드에서 처리
         *
         * @param e InvocationTargetException
         * @return ResponseEntity<ApiResponse>
         */
        @ExceptionHandler(InvocationTargetException.class)
        public ResponseEntity<ApiResponse> handlerInvocationTargetException(InvocationTargetException e) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ApiResponse.error(ErrorMessage.INVOCATION_TARGET_ERROR.getMessage() + e.getMessage()));
        }

        /**
         * NoSuchMethodException 오류 시 메소드를 찾을 수 없을 때 해당 메소드에서 처리
         *
         * @param e NoSuchMethodException
         * @return ResponseEntity<ApiResponse>
         */
        @ExceptionHandler(NoSuchMethodException.class)
        public ResponseEntity<ApiResponse> handlerNoSuchMethodException(NoSuchMethodException e) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ApiResponse.error(ErrorMessage.NO_SUCH_METHOD_ERROR.getMessage() + e.getMessage()));
        }

        /**
         * GeneralSecurityException 오류 시 보안 관련 오류가 발생했을 때 해당 메소드에서 처리
         *
         * @param e GeneralSecurityException
         * @return ResponseEntity<ApiResponse>
         */
        @ExceptionHandler(GeneralSecurityException.class)
        public ResponseEntity<ApiResponse> handlerGeneralSecurityException(GeneralSecurityException e) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ApiResponse.error(ErrorMessage.GENERAL_SECURITY_ERROR.getMessage() + e.getMessage()));
        }

        /**
         * JsonProcessingException 오류 시 JSON 처리 오류가 발생했을 때 해당 메소드에서 처리
         *
         * @param e JsonProcessingException
         * @return ResponseEntity<ApiResponse>
         */
        @ExceptionHandler(JsonProcessingException.class)
        public ResponseEntity<ApiResponse> handlerJsonProcessingException(JsonProcessingException e) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ApiResponse.error(ErrorMessage.JSON_PROCESSING_ERROR.getMessage() + e.getMessage()));
        }
    }


}
