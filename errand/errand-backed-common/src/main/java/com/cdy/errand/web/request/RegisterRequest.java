package com.cdy.errand.web.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author qq1234
 */
public class RegisterRequest {
    @Data
    public static class UserInfo {

        @NotNull
        private String userName;

        @NotNull
        private String password;
        private String nickName;
        /**
         * 性别
         */
        private int gender;
        /**
         * 地区
         */
        private String city;

        /**
         *
         */
        private String country;

        private String avatarUrl;

        private String  university;
    }
}
