package com.example.demo.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.Auth;
import com.example.demo.models.Logs;
import com.example.demo.models.Repository;
import com.example.demo.service.GitHubService;

@Validated
@RestController
@RequestMapping("/git")
@CrossOrigin
public class GitHubController {

	@Autowired
	GitHubService gitHubService;

	@GetMapping("/repos")
	public List<Repository> getRepos(@RequestHeader(value = "user", required = false) String user,
			@RequestParam(value = "query", required = false) String searchQuery) {
		return gitHubService.getRepos(user, searchQuery);
	}
	
	@GetMapping("/auth")
	public String getAuths(@RequestHeader(value = "name", required = false) String name,
			@RequestHeader(value = "password", required = false) String password) throws Exception {
		return gitHubService.checkAuth(name, password);
	}
	
	@GetMapping("/bookmarks")
	public List<Repository> getBM(@RequestHeader("name") String name, @RequestHeader("password") String password) throws Exception {
		return gitHubService.getBookMarks(name, password);
	}
	
	@GetMapping("/logs")
	public List<Logs> getLogs(@RequestHeader("name") String name, @RequestHeader("password") String password) throws Exception {
		return gitHubService.getLogs(name, password);
	}
	
	@GetMapping("/delete")
	public String getLogs(@RequestHeader("name") String name, @RequestHeader("password") String password, @RequestParam("id") String id) throws Exception {
		 gitHubService.removeBookMark(id, name, password);
		 return "ok";
	}
	
	@PostMapping("/register")
	public String register(@RequestBody Auth auth) throws Exception {
		auth.setId(UUID.randomUUID().toString());
		return gitHubService.register(auth);
	}
	
	@PostMapping("/add")
	public String add(@RequestBody Repository repo, @RequestHeader("name") String name, @RequestHeader("password") String password) throws Exception {
		gitHubService.addBookMark(repo, name, password);
		return "OK";
	}
	
	

}
