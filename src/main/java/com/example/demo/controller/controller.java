package com.example.demo.controller;

import com.example.demo.entity.email;
import com.example.demo.entity.users;
import com.example.demo.repository.repositoryEmail;
import com.example.demo.repository.repositoryUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class controller {

    @Autowired
    repositoryUser repoU;

    @Autowired
    repositoryEmail repoE;

    // Get all User
    @GetMapping("/users")
    public ResponseEntity<List<users>>getAllUsers(){
       List<users> list = repoU.findAll();
       if(list.isEmpty())
           return new ResponseEntity<>(HttpStatus.OK);
       return new ResponseEntity<>(list,HttpStatus.OK);
    }
    // Add new user
    @PostMapping("/user")
    public String addUser(@RequestBody users u){
        repoU.save(u);
        return "user Added";
    }

    // Get al User
    @GetMapping("/user/{id}")
    public ResponseEntity<Optional<users>>getUser(@PathVariable int id){
        Optional<users> userNew = repoU.findById(id);
        if(userNew.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(userNew,HttpStatus.OK);
    }

    @GetMapping("/emails/count")
    public ResponseEntity<Integer> getEmailCount(@RequestParam("user") int id ) throws Exception {

        String emailFrom = repoU.findEmail(id);

         if (emailFrom == null)
           {
               return new ResponseEntity<>(HttpStatus.NOT_FOUND);

           }



        int emailNew = repoE.findCount(emailFrom);
        return new ResponseEntity<>(emailNew,HttpStatus.OK);

    }


    // Add new email
    @PostMapping("/email")
    public String addEmail(@RequestBody email u){
        repoE.save(u);
        return "Email Added";
    }
}
