package com.spring.social.springSocial.service;

import org.springframework.social.facebook.api.User;
import org.springframework.stereotype.Service;

public interface FacebookService {
    String facebookLogin();

    String getFacebookAccessToken(String code);

    User getFacebookUserProfile(String accessToken);
}
