    package com.nucleus.security;

    import io.jsonwebtoken.Jwts;
    import io.jsonwebtoken.io.Decoders;
    import io.jsonwebtoken.security.Keys;
    import io.jsonwebtoken.security.MacAlgorithm;
    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.stereotype.Component;

    import javax.crypto.SecretKey;
    import java.util.Date;

    @Component
    public class JwtUtil {
        private final SecretKey key;
        private final long jwtExpirationMs;
        private final MacAlgorithm algorithm = Jwts.SIG.HS256;

        public JwtUtil(
                @Value("${jwt.secret}") String secret,
                @Value("${jwt.expirationMs}") long jwtExpirationMs
        ) {
            this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
            this.jwtExpirationMs = jwtExpirationMs;
        }

        public String generateToken(String email) {
            Date date = new Date();
            Date expirationDate = new Date(date.getTime() + jwtExpirationMs);

           return Jwts.builder()
                   .issuer("Nucleus")
                   .expiration(expirationDate)
                   .issuedAt(date)
                   .subject(email)
                   .signWith(key, algorithm)
                   .compact();
        }

        public boolean validateToken(String token) {
            try {
                Jwts.parser()
                        .verifyWith(key)
                        .build()
                        .parseSignedClaims(token)
                        .getPayload();
                return true;

            } catch (Exception e) {
                return false;
            }
        }

        public String extractEmail(String token) {
            return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
        }

    }
