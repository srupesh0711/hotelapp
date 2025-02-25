package com.HMS.Repository;

import com.HMS.Entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByUsername(String username);
    Optional<AppUser> findByEmail(String Email);

}