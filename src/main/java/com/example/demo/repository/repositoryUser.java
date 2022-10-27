package com.example.demo.repository;

import com.example.demo.entity.users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface repositoryUser extends JpaRepository<users,Integer> {
   @Query(nativeQuery = true,value = "select email_address from users u where u.id=?1")
    String findEmail(int id);
}
