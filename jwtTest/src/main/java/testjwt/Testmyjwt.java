package testjwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Testmyjwt {

    public static String SECRET = "mydog";

    public static String createToken(){
        Date now = new Date();

        Calendar nowtime = Calendar.getInstance();
        nowtime.add(Calendar.MINUTE,1);
        Date espireTime = nowtime.getTime();


        Map<String,Object> map = new HashMap<String, Object>();
        map.put("alg","HS2E6");
        map.put("type","JWT");
        return JWT.create()
                .withHeader(map)
                .withClaim("name", "newman")
                .withClaim("age", 23)
                .withClaim("org", "今日头条")
                .withExpiresAt(espireTime)
                .sign(Algorithm.HMAC256(SECRET));
    }

    //解密token
    public static Map<String, Claim> verifyToken(String token) throws Exception{
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
        DecodedJWT jwt = null;

        try {
            jwt = verifier.verify(token);
        }catch (Exception e ){
            throw new RuntimeException("凭证已经过时，请重新获取凭证");
        }
        return jwt.getClaims();
    }

    public static void main(String[] args) throws Exception{
        System.out.println(createToken());
        Map<String, Claim> map = verifyToken(createToken());
        for (String key : map.keySet()){
            System.out.println(key+":"+map.get(key));
        }
       String pastToken = "eyJ0eXAiOiJKV1QiLCJ0eXBlIjoiSldUIiwiYWxnIjoiSFMyNTYifQ.eyJvcmciOiLku4rml6XlpLTmnaEiLCJuYW1lIjoibmV3bWFuIiwiZXhwIjoxNjE5MTQyNjE2LCJhZ2UiOjIzfQ.4PCBh7hJo4w3K9eM2jdyRLntuL0lb_KsP4rBSX1-JoQ";
        verifyToken(pastToken);

    }
}
