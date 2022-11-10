package com.sistema.blog.model;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "publicaciones",uniqueConstraints = {@UniqueConstraint(columnNames = {"titulo"})})
public class Publicacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name ="titulo",nullable = false)
    private String titutlo;

    @Column(name ="descripcion",nullable = false)
    private String descripcion;

    @Column(name ="contenido",nullable = false)
    private String contenido;

    @OneToMany(mappedBy = "publicacion", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comentario> comentarios = new HashSet<>();

    public Publicacion(){

    }

    public Publicacion(Long id, String titutlo, String descripcion, String contenido) {
        this.id = id;
        this.titutlo = titutlo;
        this.descripcion = descripcion;
        this.contenido = contenido;
    }
}
