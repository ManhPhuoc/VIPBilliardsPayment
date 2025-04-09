package com.group1.vipbilliardspayment.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.spec.SecretKeySpec;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Value("${jwt.signerKey}")
    String signerKey;

    public String[] PUBLIC_POST_ENPOINTS = {"authentication/chuquantoken", "authentication/thungantoken", "authentication/introspect"};

    //chu quan enpoints
    public String[] ADMIN_GET_ENPOINTS = {"banbida/getallbanbida",
            "thungan/getallthungan",
            "hoadon", "hoadon/thongke", "hoadon/findbydate",
            "mathang",
            "loaiban",
            "capdohoivien",
            "hoivien"};

    public String[] ADMIN_POST_ENPOINTS = {"banbida/thembanbida",
            "thungan/timthungan", "thungan/getthunganbytendangnhap", "thungan/themthungan", "thungan/updatethungan",
            "mathang",
            "loaiban",
            "capdohoivien"};

    public String[] ADMIN_PUT_ENPOINTS = {"banbida/xoabanbida",
            "thungan/updatethungan",
            "mathang",
            "loaiban",
            "capdohoivien"};


    //thu ngan enpoints
    public String[] USER_GET_ENPOINTS = {"banbida/getallbanbida", "hoadon", "hoivien"};

    public String[] USER_POST_ENPOINTS = {"hoadon", "hoivien"};

    public String[] USER_PUT_ENPOINTS = {"hoadon", "hoadon/thanhtoan", "hoivien"};

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(request ->
                request.requestMatchers(HttpMethod.POST, PUBLIC_POST_ENPOINTS).permitAll()
                        .requestMatchers(HttpMethod.GET, ADMIN_GET_ENPOINTS).hasAuthority("SCOPE_ADMIN")
                        .requestMatchers(HttpMethod.POST, ADMIN_POST_ENPOINTS).hasAuthority("SCOPE_ADMIN")
                        .requestMatchers(HttpMethod.PUT, ADMIN_PUT_ENPOINTS).hasAuthority("SCOPE_ADMIN")
                        .requestMatchers(HttpMethod.GET, USER_GET_ENPOINTS).hasAuthority("SCOPE_USER")
                        .requestMatchers(HttpMethod.POST, USER_POST_ENPOINTS).hasAuthority("SCOPE_USER")
                        .requestMatchers(HttpMethod.PUT, USER_PUT_ENPOINTS).hasAuthority("SCOPE_USER")
                        .anyRequest().authenticated());

        http.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwtConfigurer -> jwtConfigurer.decoder(jwtDecoder())));

        http.csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

    @Bean
    JwtDecoder jwtDecoder() {
        SecretKeySpec secretKeySpec = new SecretKeySpec(signerKey.getBytes(), "HS512");
        return NimbusJwtDecoder
                .withSecretKey(secretKeySpec)
                .macAlgorithm(MacAlgorithm.HS512)
                .build();
    }
}
