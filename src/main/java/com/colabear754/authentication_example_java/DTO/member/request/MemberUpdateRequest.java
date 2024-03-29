package com.colabear754.authentication_example_java.DTO.member.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MemberUpdateRequest{

        @Schema(description = "회원 비밀번호", example = "1234")
        String password;
        @Schema(description = "회원 새 비밀번호", example = "1234")
        String newPassword;
        @Schema(description = "회원 이름", example = "콜라곰")
        String name;
        @Schema(description = "회원 나이", example = "30")
        int age;

}
