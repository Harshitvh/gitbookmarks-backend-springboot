package com.example.demo.models;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RepositoryAPIDto {
	  private String total_count;
	  private List<Repository> items=new ArrayList<Repository>();

}
