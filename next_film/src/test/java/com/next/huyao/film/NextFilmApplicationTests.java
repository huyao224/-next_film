package com.next.huyao.film;

import com.next.huyao.film.dao.entity.NextUser;
import com.next.huyao.film.dao.mapper.NextUserMapper;
import com.next.huyao.film.example.dao.UserMapper;
import com.next.huyao.film.example.dao.entity.User;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class NextFilmApplicationTests {
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private NextUserMapper nextUserMapper;
	@Test
	void contextLoads() {
	}

	@Test
	public void helloWorld(){
		List<User> users= userMapper.selectList(null);
		users.forEach(System.out::println);
	}

	@Test
	public void helloWorld2(){
		List<NextUser> nextUsers = nextUserMapper.selectList(null);
		nextUsers.forEach(System.out::println);
	}
}
