package com.demousers.users.repositories;

import com.demousers.users.entidades.Phone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneRepository extends JpaRepository<Phone, Long> {
}
