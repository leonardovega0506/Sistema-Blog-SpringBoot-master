package com.sistema.blog.service;

import com.sistema.blog.dto.ComentarioDTO;

import java.util.List;

public interface ComentarioService {
    public ComentarioDTO crearComentario(long publicacionId, ComentarioDTO comentarioDTO);

    public List<ComentarioDTO> obtenerComentariosByID(long publicacionId);

    public ComentarioDTO obtenerComenatrioPorId(long publicacionId, long comentarioID);
}
