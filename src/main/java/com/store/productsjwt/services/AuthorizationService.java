package com.store.productsjwt.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.store.productsjwt.repository.UserRepository;

@Service
public class AuthorizationService implements UserDetailsService {

  @Autowired
  public UserRepository repository;

  // Método que consulta os users na tabela (consulta para o spring security)
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return repository.findByLogin(username);
  }

}
