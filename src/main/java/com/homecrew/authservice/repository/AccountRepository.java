package com.homecrew.authservice.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.homecrew.authservice.entity.Account;

public interface AccountRepository extends JpaRepository<Account, UUID> {

}
