package com.example.demo.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Document("Auth")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class Auth {
  private String email;
  private String name;
  private String password;
  @Id
  private String id;
}
