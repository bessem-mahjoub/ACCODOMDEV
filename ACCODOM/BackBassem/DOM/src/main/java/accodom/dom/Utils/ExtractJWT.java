package accodom.dom.Utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SignatureException;

public class ExtractJWT {
    public static String payloadJWTExtraction(String token, String key) throws Exception {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey("your_secret_key") // Replace with your actual secret key
                    .parseClaimsJws(token)
                    .getBody();

            return claims.get(key, String.class);
        } catch (SignatureException e) {
            throw new Exception("Invalid token");
        }
    }
}
