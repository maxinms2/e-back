/*package com.icodeap.ecommerce.backend.infrastructure.jwt;

import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;

public class Constants {
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String TOKEN_BEARER_PREFIX = "Bearer ";

    public static final String SUPER_SECRET_KEY = ""; aqui va la clave maestra para la seguridad
    public  static  final  long TOKEN_EXPIRATION_TIME = 1800000; //30 MINUTOS

    public static Key getSignedKey(String secretKey){
        byte [] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}*/
