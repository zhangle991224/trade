package com.dm.trade.common.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.dm.trade.common.config.Constant;
import com.dm.trade.common.exception.ApiRequestUnknownException;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhongchao
 * @Date 2017-11-15
 * @since v1.0.0
 */
public class SecurityToken {


    private static final int FAILURE_TIME = 1000 * 60 * 60 * 24 * 10;

    private static String SECRET = "trade:HOBO/-;L";

    public static String createToken(Long userId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");
        try {
            return JWT.create()
                    .withHeader(map)//header
                    .withClaim(Constant.USERID, userId)//payload
                    .withClaim(Constant.FAILURE_NAME, System.currentTimeMillis() + FAILURE_TIME)
                    .sign(Algorithm.HMAC256(SECRET));
        } catch (UnsupportedEncodingException e) {
            throw new ApiRequestUnknownException("生成凭证异常", e);
        }
    }

    public static Map<String, Claim> verifyToken(String token) throws Exception {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET))
                .build();
        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaims();
    }

    public static void main(String[] args) throws Exception {
        Map<String, Claim> stringClaimMap = verifyToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1aWQiOiIyNDdjMDY1Mi1jMmJmLTRiODktOGMxNC02NWEzZmE1ZTllZWEiLCJmbiI6MTUxMjc0NzA5ODU0Mn0.TG_g2TQsGEWdcl29QuzVx4jHzeeGh5zHdJsQBrZNQLg");
        System.out.println();
    }
}