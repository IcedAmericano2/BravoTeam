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

    public List<GetProjectUserResponseDto> getProjectUserResponseDtoList(List<ProjectUser> projectUserList) {
        List<GetProjectUserResponseDto> getProjectUserResponseDtoList = new ArrayList<>();
            for(ProjectUser projectUser : projectUserList){
                GetUserResponseDto getUserResponseDto = null;
                try{getUserResponseDto = userFeignClient.getUser(projectUser.getUserId()).getBody();}
                catch (Exception e){continue;}
                GetProjectUserResponseDto getProjectUserResponseDto =
                        GetProjectUserResponseDto.from(getUserResponseDto, projectUser.getRole());
                getProjectUserResponseDtoList.add(getProjectUserResponseDto);
            }
        return getProjectUserResponseDtoList;
    }
}
