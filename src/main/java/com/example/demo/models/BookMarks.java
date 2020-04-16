package com.example.demo.models;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Document("Bookmarks")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class BookMarks {
	@Id
	private String userId;
	private List<Repository> repos;
	private List<Logs> logs;

}
