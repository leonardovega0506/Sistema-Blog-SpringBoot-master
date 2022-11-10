package com.sistema.blog.service;

import com.sistema.blog.dto.PublicacionDTO;
import com.sistema.blog.dto.PublicacionRespuesta;


public interface PublicacionService {
    public PublicacionDTO crearPublicacion(PublicacionDTO publicacionDTOs);

    public PublicacionRespuesta obtenerPublicaciones(int numeroPagina, int sizePagina,String orderBy,String sortDir);

    public PublicacionDTO obtenerPublicacionByID(long id);

    public PublicacionDTO actualizarPublicacion(PublicacionDTO publicacionDTO, long id);

    public  void eliminarPublicacion(long id);
}
