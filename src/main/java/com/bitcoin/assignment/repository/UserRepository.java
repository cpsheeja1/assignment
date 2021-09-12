package com.bitcoin.assignment.repository;

import com.bitcoin.assignment.modal.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<Users, Integer> {
}