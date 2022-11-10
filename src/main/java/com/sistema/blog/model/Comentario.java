package com.sistema.blog.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "comentarios")
@Data
public class Comentario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nombre;
    private String email;
    private String cuerpo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publicacion_id" ,nullable = false)
    private Publicacion publicacion;
}
