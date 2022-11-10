package com.sistema.blog.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class PublicacionDTO {

    private Long id;

    @NotEmpty
    @Size(min =2, message = "El titulo de la publicacion deberia tener al menos 2 caracteres")
    private String titulo;

    @NotEmpty
    @Size(min =2, message = "El Descripcion de la publicacion deberia tener al menos 2 caracteres")
    private String descripcion;

    private String contenido;
}
