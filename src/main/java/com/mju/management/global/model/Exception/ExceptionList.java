package com.mju.management.global.model.Exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ExceptionList {

    UNKNOWN(-9999, "알 수 없는 오류가 발생하였습니다."),
    NON_EXISTENT_CHECKLIST(5005, "내용이 존재하지 않습니다."),
    NON_EXISTENT_SCHEDULE(5006, "일정이 존재하지 않습니다."),
    NON_EXISTENT_SCHEDULELIST(5007, "일정 목록이 존재하지 않습니다."),
    INVALID_DATE_RANGE_EXCEPTION(5008, "날짜의 범위가 잘못되었습니다."),
    NON_EXISTENT_PROJECT(5008, "프로젝트가 존재하지 않습니다."),
    INVALID_INPUT_VALUE(5009, "유효성 검사에 실패하였습니다."),
    // PROJECT
    INVALID_PROJECT_ID(5010, "요청으로 들어온 Project의 식별자가 유효하지 않습니다."),

    // USER
    INVALID_USER_ID(5011, "요청으로 들어온 User의 식별자가 유효하지 않습니다."),

    // PROJECT & TEAM
    NOT_TEAM_MEMBER(5012, "요청으로 들어온 User는 해당 Project의 팀원이 아닙니다."),

    // POST
    INVALID_POST_ID(5013, "요청으로 들어온 Post의 식별자가 유효하지 않습니다."),
    NO_PERMISSION_TO_EDIT_POST(5014, "게시글 수정 권한이 없습니다.");





    private final int code;
    private final String message;
}
