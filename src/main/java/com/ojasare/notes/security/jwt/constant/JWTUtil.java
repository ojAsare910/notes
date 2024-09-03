package com.ojasare.notes.security.jwt.constant;

public class JWTUtil {

    public static final long EXPIRE_ACCESS_TOKEN = 60 * 1000; //10* 60 * 1000 = 10 minutes

    public static final long EXPIRE_REFRESH_TOKEN = 120 * 60 * 1000;

    public static final String ISSUER = "Ojasare";

    public static final String BEARER_PREFIX = "Bearer ";

    public static final String AUTH_HEADER = "Authorization";
}
