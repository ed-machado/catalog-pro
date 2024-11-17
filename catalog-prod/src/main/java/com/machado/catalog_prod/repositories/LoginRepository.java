package com.machado.catalog_prod.repositories;

import java.util.Optional;

import com.machado.catalog_prod.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LoginRepository extends JpaRepository<User, Long>{

	public Optional<User> findByUsername(String login);

    boolean existsByUsername(String admin);
}
