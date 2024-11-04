package com.ecommerce.Users.dao;

import aj.org.objectweb.asm.commons.Remapper;
import com.ecommerce.Users.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Integer> {

    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndPassword(String email, String password);
}
