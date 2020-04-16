package com.example.demo.service;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Auth;

@Repository
public interface AuthRepository extends MongoRepository<Auth,String> {

 List<Auth> findAuthByName(String name);
 List<Auth> findAuthByNameAndPassword(String name, String password);

}