package com.example.demo.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Users;

@Repository
public interface UsersRepo extends JpaRepository<Users, String> {

	Optional<Users> findByEmail(String email);

	Optional<Users> findByEmailAndPassword(String email, String password);

	List<Users> findByNameContaining(String keywords);
}
