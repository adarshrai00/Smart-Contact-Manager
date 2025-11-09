package com.scm.helper;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class Helper {

    public static String getEmailOfLoggedInUser(Authentication authentication) {

        // ✅ Check for OAuth2AuthenticationToken (not OAuth2AuthenticatedPrincipal)
        if (authentication instanceof OAuth2AuthenticationToken) {

            OAuth2AuthenticationToken oauth2Token = (OAuth2AuthenticationToken) authentication;
            String clientId = oauth2Token.getAuthorizedClientRegistrationId();

            // Get OAuth2User principal
            OAuth2User oauth2User = (OAuth2User) oauth2Token.getPrincipal();

            String email = "";

            if (clientId.equalsIgnoreCase("google")) {
                // Google provides email directly
                email = oauth2User.getAttribute("email");
                System.out.println("Google login - Email: " + email);

            } else if (clientId.equalsIgnoreCase("github")) {
                // GitHub provides email (may be null if private)
                email = oauth2User.getAttribute("email");

                // Fallback: use login username if email is private
                if (email == null || email.isEmpty()) {
                    String login = oauth2User.getAttribute("login");
                    email = login + "@github.user";
                }
                System.out.println("GitHub login - Email: " + email);
            }

            return email;

        } else {
            // Database/Form login
            System.out.println("Database login");
            return authentication.getName();
        }
    }
}