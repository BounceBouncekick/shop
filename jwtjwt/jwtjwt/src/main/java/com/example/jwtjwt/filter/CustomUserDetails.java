package com.example.jwtjwt.filter;

import com.example.jwtjwt.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Slf4j
public class CustomUserDetails implements UserDetails {


    private final UserEntity userEntity;

    public CustomUserDetails(UserEntity userEntity) {
        log.info("CustomUserDetailsuserEntity : " + userEntity);
        this.userEntity = userEntity;
    }


    //role 값 반환
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        Collection<GrantedAuthority> collection = new ArrayList<>();
//
//        collection.add(new GrantedAuthority() {
//
//            @Override
//            public String getAuthority() {
//                return userEntity.getRole();
//            }
//        });
//        log.info("collection :" + collection);
//        return collection;
//    }
    @Override
   public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        String role = userEntity.getRole(); // 역할을 가져옴
        log.info("collectionrole :" + role);
        collection.add(new SimpleGrantedAuthority(role)); // 문자열로 변환하여 SimpleGrantedAuthority 객체 생성
        log.info("collectionrole2 :" + role);
        log.info("collection :" + collection);
        return collection;
   }
    //password
    @Override
    public String getPassword() {

        return userEntity.getPassword();
    }
    //id
    @Override
    public String getUsername() {

        return userEntity.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {

        return true;
    }

    @Override
    public boolean isAccountNonLocked() {

        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {

        return true;
    }

    @Override
    public boolean isEnabled() {

        return true;
    }

}