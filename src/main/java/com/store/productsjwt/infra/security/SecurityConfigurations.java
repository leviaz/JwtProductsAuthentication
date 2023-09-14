package com.store.productsjwt.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {
  // define rotas e configs

  @Autowired
  SecurityFilter securityFilter;

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
        .authorizeHttpRequests(authorize -> authorize
            .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
            .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
            .requestMatchers(HttpMethod.POST, "/product").hasRole("ADMIN")
            .anyRequest().authenticated()) // verifica autenticacao
        .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class) // filtro que verifica se o
                                                                                     // endpoint está aberto ou não
        .build();
  }
  // APENAS ADMINS ROLE PODEM USAR POST
  // Adicionar a config de authentication manager que irá verificar o user passado

  // retorna a instancia de autentica;ão
  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
      throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  // método para retornar o encoder que criptografa as senhas para armazenar no
  // banco
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
