package com.example.demo.service;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Auth;
import com.example.demo.models.BookMarks;

@Repository
public interface BookMarksRepository extends MongoRepository<BookMarks,String> {



}