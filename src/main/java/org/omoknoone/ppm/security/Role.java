package org.omoknoone.ppm.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    PM("ROLE_PM", "PM"),
    PL("ROLE_PL", "PL"),
    PARTICIPANT("ROLE_PARTICIPANT", "프로젝트 참여자");

    private final String key;
    private final String title;
}
