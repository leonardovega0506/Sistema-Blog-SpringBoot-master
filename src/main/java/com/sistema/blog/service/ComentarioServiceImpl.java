package com.sistema.blog.service;

import com.sistema.blog.dto.ComentarioDTO;
import com.sistema.blog.exception.BlogAppException;
import com.sistema.blog.exception.ResourceNotFoundException;
import com.sistema.blog.model.Comentario;
import com.sistema.blog.model.Publicacion;
import com.sistema.blog.repository.IComentario;
import com.sistema.blog.repository.IPublicacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComentarioServiceImpl implements ComentarioService{
    @Autowired
    private IComentario iComentario;

    @Autowired
    private IPublicacion iPublicacion;

    @Override
    public ComentarioDTO crearComentario(long publicacionId, ComentarioDTO comentarioDTO) {
        Comentario comentario = mapearEntidad(comentarioDTO);
        Publicacion publicacion = iPublicacion.findById(publicacionId).orElseThrow(() -> new ResourceNotFoundException("Publicacion","ID",publicacionId));

        comentario.setPublicacion(publicacion);
        Comentario nuevoComentario = iComentario.save(comentario);
        return mapearDTO(nuevoComentario);
    }

    @Override
    public List<ComentarioDTO> obtenerComentariosByID(long publicacionId) {
        List<Comentario> comemtario = iComentario.findByPublicacionId(publicacionId);
        return comemtario.stream().map(comentario -> mapearDTO(comentario)).collect(Collectors.toList());
    }

    @Override
    public ComentarioDTO obtenerComenatrioPorId(long publicacionId, long comentarioID) {
        Publicacion publicacion = iPublicacion.findById(publicacionId).orElseThrow(() -> new ResourceNotFoundException("Publicacion","ID",publicacionId));

        Comentario comentario = iComentario.findById(comentarioID).orElseThrow(() -> new ResourceNotFoundException("Comentario","ID",comentarioID));
        if(!comentario.getPublicacion().getId().equals(publicacion.getId())){
            throw  new BlogAppException(HttpStatus.BAD_REQUEST, "El comentario no pertenece a la publicacion");
        }
        return mapearDTO(comentario);
    }

    private ComentarioDTO mapearDTO(Comentario comentario){
        ComentarioDTO comentarioDTO = new ComentarioDTO();
        comentarioDTO.setId(comentario.getId());
        comentarioDTO.setNombre(comentario.getNombre());
        comentarioDTO.setEmail(comentario.getEmail());
        comentarioDTO.setCuerpo(comentario.getCuerpo());

        return  comentarioDTO;
    }
    private Comentario mapearEntidad(ComentarioDTO comentarioDTO){
        Comentario comentario = new Comentario();
        comentario.setId(comentarioDTO.getId());
        comentario.setNombre(comentarioDTO.getNombre());
        comentario.setEmail(comentarioDTO.getEmail());
        comentario.setCuerpo(comentarioDTO.getCuerpo());

        return comentario;
    }
}
