package com.wellsfargo.security.Repository;

import com.wellsfargo.security.Entity.user_custom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<user_custom, Integer> {
    Optional<user_custom> findByemail_id(String email_id);
}
