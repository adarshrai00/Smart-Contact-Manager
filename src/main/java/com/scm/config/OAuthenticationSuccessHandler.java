package com.scm.config;

import com.scm.entity.Providers;
import com.scm.entity.User;
import com.scm.helper.AppConstants;
import com.scm.repositories.UserRepo;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private Logger logger = LoggerFactory.getLogger(OAuthenticationSuccessHandler.class);

    @Autowired
    private UserRepo userRepo;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        logger.info("OAuthenticationSuccessHandler triggered");

        // Identify the provider
        OAuth2AuthenticationToken oauth2Token = (OAuth2AuthenticationToken) authentication;
        String provider = oauth2Token.getAuthorizedClientRegistrationId(); // "github" or "google"

        DefaultOAuth2User oauthUser = (DefaultOAuth2User) authentication.getPrincipal();

        // Log all attributes for debugging
        logger.info("Provider: " + provider);
        oauthUser.getAttributes().forEach((key, value) -> {
            logger.info(key + " : " + value);
        });

        String email = null;
        String name = null;
        String picture = null;

        // Handle different OAuth providers
        if ("github".equalsIgnoreCase(provider)) {
            // GitHub attributes
            email = oauthUser.getAttribute("email") != null ?
                    oauthUser.getAttribute("email").toString() : null;
            name = oauthUser.getAttribute("login") != null ?
                    oauthUser.getAttribute("login").toString() :
                    oauthUser.getAttribute("name").toString();
            picture = oauthUser.getAttribute("avatar_url") != null ?
                    oauthUser.getAttribute("avatar_url").toString() : null;

            // GitHub may not provide email if it's private
            if (email == null || email.isEmpty()) {
                email = name + "@github.user"; // Fallback email
            }

        } else if ("google".equalsIgnoreCase(provider)) {
            // Google attributes
            email = oauthUser.getAttribute("email").toString();
            name = oauthUser.getAttribute("name").toString();
            picture = oauthUser.getAttribute("picture").toString();
        }

        // Check if user already exists
        User user = userRepo.findByEmail(email).orElse(null);

        if(user == null) {
            // Create new user
            user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setProfilePic(picture);
            user.setProvider("github".equalsIgnoreCase(provider) ? Providers.GITHUB : Providers.GOOGLE);
            user.setEnabled(true);
            user.setEmailVerified(true);
            user.setPassword("password");
            user.setProviderUserId(name);
            user.setAbout("Account created using " + provider);
            user.setRole(AppConstants.Role_User);

            userRepo.save(user);
            logger.info("New user saved: " + email);
        } else {
            logger.info("User already exists: " + email);
            // Optionally update profile picture
            user.setProfilePic(picture);
            userRepo.save(user);
        }

        // Redirect to dashboard
        new DefaultRedirectStrategy().sendRedirect(request, response, "/user/bokka");
    }
}