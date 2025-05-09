package security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;


public class JwtUtil {
    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public static String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600_000)) // 1 hour
                .signWith(key)
                .compact();
    }

    public static void validateToken(String token) throws JwtException {
        if(token==null) throw new JwtException("Token is invalid");
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        token=token.trim();
        Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
    }
    public static String getUsername(String token) throws JwtException {
        if(token==null) throw new JwtException("Token is invalid");
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        token=token.trim();
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
