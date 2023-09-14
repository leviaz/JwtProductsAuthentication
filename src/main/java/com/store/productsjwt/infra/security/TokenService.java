package com.store.productsjwt.infra.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.store.productsjwt.model.user.User;

//classe que gera tokens
//Definir o algoritmo que gera a criptografia
//O jwt create recebe o nome da aplica;ão, o login do user e o tempo de exp assim como o sign algoritmo
@Service
public class TokenService {

  @Value("${api.security.token.secret}")
  private String secret;

  public String generateToken(User user) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(secret);
      String token = JWT.create()
          .withIssuer("auth-api")
          .withSubject(user.getLogin())
          .withExpiresAt(genExpInstant())
          .sign(algorithm);
      return token;
    } catch (JWTCreationException exception) {
      throw new RuntimeException("error while generating token", exception);

    }

  }

  // método para verificar se o token está valido ou não e identificar o user
  public String validateToken(String token) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(secret);
      return JWT.require(algorithm)
          .withIssuer("auth-api")
          .build()
          .verify(token)
          .getSubject();
    } catch (JWTVerificationException exception) {
      return "";
    }
  }

  // tempo de expira;ão de 2 horas usando o fuso -3 h do tipo instant
  private Instant genExpInstant() {
    return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
  }
}
