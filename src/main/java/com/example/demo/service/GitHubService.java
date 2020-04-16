package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.Auth;
import com.example.demo.models.BookMarks;
import com.example.demo.models.Logs;
import com.example.demo.models.Repository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GitHubService {
	
	@Autowired
	GateWayService gatewayService;
	
	@Autowired
	BookMarksRepository bmr;
	
	@Autowired
	AuthRepository authRepo;
	
	public List<Repository>  getRepos(String user, String searchQuery)
	{
		if(StringUtils.isEmpty(searchQuery))
		{
		return gatewayService.getRepos(searchQuery).getBody().getItems();
		}
		else
			return gatewayService.getRepos(searchQuery).getBody().getItems().stream().filter(x->x.getName().matches(searchQuery)).collect(Collectors.toList());
	}

	public String register(Auth auth1) throws Exception {
		List<Auth> auth = authRepo.findAuthByName(auth1.getName());
		if(auth==null || auth.size()==0)
		{    
			auth1.setId(UUID.randomUUID().toString());
			authRepo.save(auth1);
			return "ok";
		}
		else
		{
			throw new Exception();

		}
	}
	
	public String checkAuth(String name, String password) throws Exception
	{
		List<Auth> auth = authRepo.findAuthByNameAndPassword(name, password);
		if(null!=auth && auth.size()>0)
		{
			return "OK";
		}
		else
			throw new Exception();
	}
	
	public List<Repository> getBookMarks(String name,String password) throws Exception
	{
		List<Auth> auth = authRepo.findAuthByNameAndPassword(name, password);
		if(null!=auth && auth.size()>0)
		{
		     return  bmr.findById(auth.get(0).getId()).get().getRepos();
		}
		else
			throw new Exception();
	}
	
	public List<Logs> getLogs(String name,String password) throws Exception
	{
		List<Auth> auth = authRepo.findAuthByNameAndPassword(name, password);
		if(null!=auth && auth.size()>0)
		{
		     return  bmr.findById(auth.get(0).getId()).get().getLogs();
		}
		else
			throw new Exception();
	}
	
	public void addBookMark(Repository repo, String name, String password) throws Exception
	{
		List<Auth> auth = authRepo.findAuthByNameAndPassword(name, password);
		if(null!=auth && auth.size()>0)
		{
			Optional<BookMarks> nmr = bmr.findById(auth.get(0).getId());
		  if(nmr.isPresent())
		  {
			  BookMarks act = nmr.get();
			  act.getRepos().add(repo);
			  act.getLogs().add(Logs.builder().action("Added").repoId(repo.getId()).repoName(repo.getName()).timestamp(LocalDateTime.now().toString()).build());
			  bmr.save(act);
		  }
		  else
		  {
			  List<Repository> repos = new ArrayList<Repository>();
			  repos.add(repo);
			  List<Logs> logs = new ArrayList<Logs>();
			  logs.add(Logs.builder().action("Added").repoId(repo.getId()).repoName(repo.getName()).timestamp(LocalDateTime.now().toString()).build());
			  BookMarks bookmks = BookMarks.builder().logs(logs).repos(repos).userId(auth.get(0).getId()).build();
			  bmr.save(bookmks);
		  }
		}
		else
			throw new Exception();
	}
	
	public void removeBookMark(String id, String name, String password) throws Exception
	{
		List<Auth> auth = authRepo.findAuthByNameAndPassword(name, password);
		if(null!=auth && auth.size()>0)
		{
			Optional<BookMarks> nmr = bmr.findById(auth.get(0).getId());
		  if(nmr.isPresent())
		  {
			  BookMarks act = nmr.get();
			  List<Repository> repoList = act.getRepos().stream().filter(repo->!(repo.getId().equalsIgnoreCase(id))).collect(Collectors.toList());
			  String repoName = act.getRepos().stream().filter(repo->(repo.getId().equalsIgnoreCase(id))).findFirst().get().getName();
			  repoList.forEach(System.out::println);
			  act.setRepos(repoList);
			  act.getLogs().add(Logs.builder().action("Removed").repoId(id).repoName(repoName).timestamp(LocalDateTime.now().toString()).build());
			  bmr.save(act);
		  }
		  else
		  {
				throw new Exception();

		  }
		}
		else
			throw new Exception();
	}

}
