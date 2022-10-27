package com.example.demo.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class email {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String emailFrom;
    String emailTo;
    String subject;
    String body;

}
