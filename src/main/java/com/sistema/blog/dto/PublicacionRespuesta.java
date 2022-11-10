package com.sistema.blog.dto;

import lombok.Data;

import java.util.List;

@Data
public class PublicacionRespuesta {
    private List<PublicacionDTO> contenido;
    private int numeroPagina;
    private int sizePagina;
    private long totalElementos;
    private int totalPaginas;
    private boolean ultima;

}
