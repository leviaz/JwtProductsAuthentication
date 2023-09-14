package com.store.productsjwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.store.productsjwt.infra.security.TokenService;
import com.store.productsjwt.model.user.AuthLoginResponseDTO;
import com.store.productsjwt.model.user.AuthenticationDTO;
import com.store.productsjwt.model.user.RegisterDTO;
import com.store.productsjwt.model.user.User;
import com.store.productsjwt.repository.UserRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("auth")
public class AuthenticationController {
  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private UserRepository repository;

  @Autowired
  private TokenService tokenService;

  @PostMapping("/login")
  public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) {
    // deve-se salvar os tokens no banco criptografados da senha (hash)
    // gera os dados para valida;ão
    var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
    // autenticar o user criado
    var auth = this.authenticationManager.authenticate(usernamePassword);

    var token = tokenService.generateToken((User) auth.getPrincipal());
    return ResponseEntity.ok(new AuthLoginResponseDTO(token)); // devolve o token para o user logado e assim atraves do
                                                               // token é possivel verificar e utilizar as outras
                                                               // requests
  }

  // método de registro verifica se o user já foi cadastado
  // caso não, irá criptografar a senha em hash e repassar a senha
  // posteriormente salvando no banco de dados
  @PostMapping("/register")
  public ResponseEntity register(@RequestBody @Valid RegisterDTO data) {
    if (this.repository.findByLogin(data.login()) != null) {
      return ResponseEntity.badRequest().build();
    }
    String hashSenha = new BCryptPasswordEncoder().encode(data.password());
    User newUser = new User(data.login(), hashSenha, data.role());
    this.repository.save(newUser);
    return ResponseEntity.ok().build();
  }
}
