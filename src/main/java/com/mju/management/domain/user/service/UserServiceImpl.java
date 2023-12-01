package com.mju.management.domain.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mju.management.domain.project.dto.response.GetProjectUserResponseDto;
import com.mju.management.domain.project.infrastructure.ProjectUser;
import com.mju.management.domain.user.controller.UserFeignClient;
import com.mju.management.domain.user.dto.GetUserResponseDto;
import com.mju.management.global.model.Exception.ExceptionList;
import com.mju.management.global.model.Exception.UserNotFindException;
import com.nimbusds.jwt.SignedJWT;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.net.HttpCookie;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl {

    private final UserFeignClient userFeignClient;

    private final Environment environment;

    public GetUserResponseDto getUser(Long userId){
        try{return userFeignClient.getUser(userId).getBody();}
        catch (Exception e){e.printStackTrace(); return null;}
    }

    public String getUsername(Long userId){
        GetUserResponseDto getUserResponseDto = getUser(userId);
        if(getUserResponseDto == null) return "(알 수 없음)";
        return getUserResponseDto.getName();
    }
}
