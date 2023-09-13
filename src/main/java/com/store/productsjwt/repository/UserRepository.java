package com.store.productsjwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.store.productsjwt.model.user.User;

public interface UserRepository extends JpaRepository<User, String> {
  // declarar método de login (usando assinatura padrão do jpa findBy)

  UserDetails findByLogin(String login);

}
