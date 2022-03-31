package com.cdy.errand.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author qq1234
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserDTO {
    private Long userId;

    private String userName;

    private String phoneNumber;

    private String nickName;
    private String avatarUrl;
    /**
     * 个人简介
     */
    private String description;

    /**
     * 大学
     */
    private String university;

    private String token;
}
