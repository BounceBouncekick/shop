package com.example.jwtjwt.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Entity
@Setter
@Getter
@Table(name = "jwttable")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private int id;

    @NotBlank(message = "닉네임을 입력해주세요")
    @Size(min = 2, max = 10, message = "닉네임은 2자 이상 10자 이하로 입력해주세요")
    private String username;
    @NotBlank(message = "비밀번호는 필수입니다.")
    @Size(min = 8, max = 20, message = "비밀번호는 8자이상 20자 이하로 입력해주세요")
    private String password;
    private String userEmail;

    private String role;



    public String getRole() {
        log.info("getRole: "+ role);
        return this.role; // 혹은 다른 필드에 따라 달라질 수 있습니다.
    }

}