package com.heartape.controller;

import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.AbstractOAuth2Token;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
public class Oauth2ClientController {

    private final ReactiveOAuth2AuthorizedClientService authorizedClientService;

    public Oauth2ClientController(ReactiveOAuth2AuthorizedClientService authorizedClientService) {
        this.authorizedClientService = authorizedClientService;
    }

    @GetMapping("/")
    public Mono<Map<String, String>> index(OAuth2AuthenticationToken authentication){
        if (authentication == null || !authentication.isAuthenticated()){
            return Mono.empty();
        }
        OidcUser oidcUser = (OidcUser)authentication.getPrincipal();
        OidcIdToken idToken = oidcUser.getIdToken();
        return authorizedClientService
                .loadAuthorizedClient(authentication.getAuthorizedClientRegistrationId(), authentication.getName())
                .flux()
                .flatMap(oAuth2AuthorizedClient -> Flux.just(oAuth2AuthorizedClient.getAccessToken(), oAuth2AuthorizedClient.getRefreshToken()))
                .mergeWith(Mono.just(idToken))
                .collectMap(abstractOAuth2Token -> abstractOAuth2Token.getClass().getSimpleName(), AbstractOAuth2Token::getTokenValue);
    }
}
