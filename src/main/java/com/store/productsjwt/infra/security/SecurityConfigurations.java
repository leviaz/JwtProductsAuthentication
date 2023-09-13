package com.store.productsjwt.infra.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {
  // define rotas e configs

  // security filter chain, verifica se o user está apto a realizar a request
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    // realizar autentica;ão stateless sem armazenar informa;ões de sessões
    // gerando apenas um token para que realize as request
    // token será verificado e caso seja criado será validado a request, sendo usado
    // nos processos de request
    // Assim gera-se uma session stateless armazenando apenas o token
    return httpSecurity.csrf(csrf -> csrf.disable())
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .build();
  }
}
