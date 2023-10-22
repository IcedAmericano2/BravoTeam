package com.mju.management.domain.project.infrastructure;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Role {
    LEADER("팀장"),
    MEMBER("팀원");

    private final String description;
}
