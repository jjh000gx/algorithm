package com.example.entity.testone;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

@Getter
@Setter
public class User implements Serializable {

    @Column(name = "id")
    @Id
    private Long              id;

    @Column(name = "name")
    private String            name;

    @Column(name = "email")
    private String            email;


}

