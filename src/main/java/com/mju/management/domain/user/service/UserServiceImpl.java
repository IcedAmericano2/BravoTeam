package com.mju.management.domain.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mju.management.domain.user.controller.UserFeignClient;
import com.mju.management.domain.user.dto.UserInfoDto;
import com.mju.management.global.model.Exception.ExceptionList;
import com.mju.management.global.model.Exception.UserNotFindException;
import com.nimbusds.jwt.SignedJWT;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.HttpCookie;
import java.text.ParseException;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl {

    private final UserFeignClient userFeignClient;
    private final ObjectMapper objectMapper;

    /**
     * 유저 아이디 가져오기
     * @param request
     */

    public String getUserId(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String ssoToken = authorizationHeader.substring(7); // "Bearer " 부분을 제거하여 토큰 값만 추출

            if (ssoToken == null) {
                return null;
            }
            HttpCookie token = new HttpCookie("ssoToken", ssoToken);
            String userId = getUserInfoDto(token ).getId();
            System.out.println(userId);
            return userId;
        } else {
            throw new UserNotFindException(ExceptionList.EMPTY_USER);
        }
    }

    /**
     * UserInfoDto 값 가져오기
     * @param ssoToken
     */

    public UserInfoDto getUserInfoDto(final HttpCookie ssoToken) {
        String userId = extractLoginUserId(ssoToken);
        UserInfoDto userInfoDto = userFeignClient.getUserInfo(userId);
        if(userInfoDto == null) {
            throw new UserNotFindException(ExceptionList.NOT_EXISTENT_USER);
        }
        return userInfoDto;
    }

    /**
     * user-service와 통신
     */
    public UserInfoDto getUserInfoDto(String userId) {
        UserInfoDto userInfoDto = userFeignClient.getUserInfo(userId);
        return userInfoDto;
    }

    /**
     * 토큰에서 userId 가져오기
     */
    private String extractLoginUserId(final HttpCookie ssoToken) {
        try {
            String tokenPayload = SignedJWT.parse(ssoToken.getValue()).getPayload().toString();

            System.out.println(tokenPayload);

            return objectMapper.readTree(tokenPayload).path("sub").asText();

        } catch (ParseException | JsonProcessingException e) {
            // 예외 처리 로직을 추가하거나 예외를 다시 throw할 수 있습니다.
            // 예외를 다시 throw하는 경우, 메서드 시그니처에 예외를 선언해야 합니다.
            e.printStackTrace(); // 예외 정보를 출력합니다. 실제로는 로깅 등의 처리를 권장합니다.
            throw new UserNotFindException(ExceptionList.EMPTY_USER);
        }
    }

    /**
     * 유저가 null인지 확인
     */
    public void checkUserId(String userId) {
        if(userId == null) {
            throw new UserNotFindException(ExceptionList.EMPTY_JWT);
        }
    }

    /***
     * 유저가 존재하는지, 해당 유저 타입이 맞는 userType인지 확인
     * @param userId
     * @param userType
     */
    public void checkUserType(String userId, String userType) {
        checkUserId(userId);
        UserInfoDto userInfoDto = getUserInfoDto(userId);
        if(!userInfoDto.getUserInformationType().equals(userType)) {
            throw new UserNotFindException(ExceptionList.NOT_CORRECT_USER);
        }
    }




}
