package com.sistema.blog.model;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Roles")
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(length =60)
    private String nombre;


}
