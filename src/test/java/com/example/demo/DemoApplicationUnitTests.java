package com.example.demo;

import com.example.demo.entity.email;
import com.example.demo.entity.users;
import com.example.demo.repository.repositoryEmail;
import com.example.demo.repository.repositoryUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

//Unit Testing
@SpringBootTest
class DemoApplicationUnitTests {

	@Autowired
	repositoryUser repo;
	@Autowired
	repositoryEmail repoE;
	// test users repository
	@Test
	void contextLoads() {
	}
	@BeforeEach
	void setup() {
        // save user
 		users user = new users(1,"first","last","abc@gmail.com",123456789);
		users user2 = new users(2,"first","last","abc@gmail.com",123456789);
		users user3 = new users(3,"first","last","abc2@gmail.com",123456789);
		repo.save(user);
		repo.save(user3);
		repo.save(user2);

	  // save email
		email e = new email(1,"abc@gmail.com","bcd@gmail.com","subject","Body");
		email e2= new email(2,"abc@gmail.com","bcd@gmail.com","subject","Body");
		email e3 = new email(3,"abc@gmail.com","bcd@gmail.com","subject","Body");
        repoE.save(e);
		repoE.save(e2);
		repoE.save(e3);

	}

	@Test
	public void testFindEmail()
	{
     assertEquals("abc@gmail.com",repo.findEmail(1));
	}

	@Test
	public void testFindCount()
	{
		assertEquals(3,repoE.findCount("abc@gmail.com"));
	}
}
