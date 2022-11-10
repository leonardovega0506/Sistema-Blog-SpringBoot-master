package com.sistema.blog.service;

import com.sistema.blog.dto.PublicacionDTO;
import com.sistema.blog.dto.PublicacionRespuesta;
import com.sistema.blog.exception.ResourceNotFoundException;
import com.sistema.blog.model.Publicacion;
import com.sistema.blog.repository.IPublicacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PublicacionServiceImpl implements PublicacionService{
    @Autowired
    private IPublicacion iPublicacion;

    @Override
    public PublicacionDTO crearPublicacion(PublicacionDTO publicacionDTOs) {
       Publicacion publicacion = mapearEntidad(publicacionDTOs);
       Publicacion nuevaPublicacion = iPublicacion.save(publicacion);
       PublicacionDTO publicacionRespuesta = mapearDTO(nuevaPublicacion);
       return  publicacionRespuesta;
    }

    @Override
    public PublicacionRespuesta obtenerPublicaciones(int numeroPagina, int sizePagina, String orderBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(orderBy).ascending():Sort.by(orderBy).descending();
        Pageable pageable  = PageRequest.of(numeroPagina,sizePagina, sort);
        Page<Publicacion> publicaciones = iPublicacion.findAll(pageable);
        List<Publicacion> listaPublicaciones = publicaciones.getContent();
        List<PublicacionDTO> contenido = listaPublicaciones.stream().map(publicacion -> mapearDTO(publicacion)).collect(Collectors.toList());
        PublicacionRespuesta publicacionRespuesta = new PublicacionRespuesta();
        publicacionRespuesta.setContenido(contenido);
        publicacionRespuesta.setNumeroPagina(publicaciones.getNumber());
        publicacionRespuesta.setSizePagina(publicaciones.getSize());
        publicacionRespuesta.setTotalElementos(publicaciones.getTotalElements());
        publicacionRespuesta.setTotalPaginas(publicaciones.getTotalPages());
        publicacionRespuesta.setUltima(publicaciones.isLast());
        return  publicacionRespuesta;
    }

    @Override
    public PublicacionDTO obtenerPublicacionByID(long id) {
        Publicacion publicacion = iPublicacion.findById(id).orElseThrow(() -> new ResourceNotFoundException("Publicacion","ID",id));
        return mapearDTO(publicacion);
    }

    @Override
    public PublicacionDTO actualizarPublicacion(PublicacionDTO publicacionDTO, long id) {
        Publicacion publicacion = iPublicacion.findById(id).orElseThrow(() -> new ResourceNotFoundException("Publicacion","ID",id));


        publicacion.setTitutlo(publicacionDTO.getTitulo());
        publicacion.setContenido(publicacionDTO.getContenido());
        publicacion.setDescripcion(publicacionDTO.getContenido());
        Publicacion publicacionActualizada = iPublicacion.save(publicacion);

        return mapearDTO(publicacionActualizada);

    }

    @Override
    public void eliminarPublicacion(long id) {
        Publicacion publicacion = iPublicacion.findById(id).orElseThrow(() -> new ResourceNotFoundException("Publicacion","ID",id));
        iPublicacion.delete(publicacion);
    }

    //Convierte model a DTO
    private PublicacionDTO mapearDTO(Publicacion publicacion){
        PublicacionDTO publicacionDTO = new PublicacionDTO();
        publicacionDTO.setId(publicacion.getId());
        publicacionDTO.setTitulo(publicacion.getTitutlo());
        publicacionDTO.setContenido(publicacion.getContenido());
        publicacionDTO.setDescripcion(publicacion.getDescripcion());
        return  publicacionDTO;
    }

    //Convierte DTO a Model
    private Publicacion mapearEntidad(PublicacionDTO publicacionDTO){
        Publicacion publicacion = new Publicacion();
        publicacion.setId(publicacionDTO.getId());
        publicacion.setTitutlo(publicacionDTO.getTitulo());
        publicacion.setContenido(publicacionDTO.getContenido());
        publicacion.setDescripcion(publicacionDTO.getDescripcion());
        return publicacion;
    }
}
