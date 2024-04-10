package com.demousers.users.repositories;

import com.demousers.users.entidades.UsersModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UsersModel, Long> {
    UsersModel findByEmail(String email);
}
