package com.colabear754.authentication_example_java.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * API 응답 DTO
 *
 * @Author NINEFIVE
 */
@Getter
@Setter
@Schema(name = "ApiResponse", description = "API 응답 DTO")
@AllArgsConstructor
public class ApiResponse extends AbstractDTO {

    @Schema(name = "code", description = "응답 코드", example = "200")
    private int code;
    @Schema(name = "message", description = "응답 메시지", example = "SUCCESS")
    private String message;
    @Schema(name = "data", description = "응답 데이터", example = "{}")
    private Object data;
    @Schema(name = "sample", description = "샘플 데이터", example = "{}")
    private Object sample;

    // NOTE 성공 시 응답
    public static ApiResponse success(Object data) {
        try {
            LinkedHashMap<String, Object> exampleMap = new LinkedHashMap<>();
            if (data instanceof List) { // NOTE 리스트 형식을 검사
                List<?> dataList = (List<?>) data; // NOTE 리스트로 캐스팅
                if (!dataList.isEmpty()) { // NOTE 리스트가 비어있지 않은 경우
                    Object firstElement = dataList.get(0); // NOTE 첫 번째 요소를 가져옴
                    processFields(firstElement, exampleMap); // NOTE 첫 번째 요소의 필드를 처리
                }
            } else { // NOTE 리스트가 아닌 경우
                processFields(data, exampleMap);
            }
            return new ApiResponse(200, "SUCCESS", data, exampleMap);
        } catch (Exception e) {
            return new ApiResponse(200, "SUCCESS", data, new ArrayList<>());
        }
    }

    // NOTE 에러 시 응답
    public static ApiResponse error(String message) {
        return new ApiResponse(500, message, new ArrayList<>(), new ArrayList<>());
    }

    private static void processFields(Object data, Map<String, Object> exampleMap) throws Exception {
        // NOTE 현재 클래스의 타입을 가져옴
        Class<?> currentClass = data.getClass();
        // NOTE 상위 클래스를 포함하여 모든 필드를 반복
        while (currentClass != null) {
            // NOTE 현재 클래스의 모든 필드를 가져옴
            Field[] fields = currentClass.getDeclaredFields();
            // NOTE 각 필드를 반복
            for (Field field : fields) {
                // NOTE 필드에 @Schema 어노테이션이 있는지 확인
                Schema schema = field.getAnnotation(Schema.class);
                if (schema != null) {
                    // NOTE 필드를 접근 가능하도록 설정
                    field.setAccessible(true);
                    // NOTE 필드 이름과 @Schema 어노테이션의 description 값을 exampleMap에 추가
                    exampleMap.put(field.getName(), schema.description());
                }
            }
            // NOTE 상위 클래스로 이동
            currentClass = currentClass.getSuperclass();
        }
    }

}
