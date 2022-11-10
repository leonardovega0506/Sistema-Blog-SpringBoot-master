package com.sistema.blog.controller;

import com.sistema.blog.dto.PublicacionDTO;
import com.sistema.blog.dto.PublicacionRespuesta;
import com.sistema.blog.service.PublicacionService;
import com.sistema.blog.util.AppConstantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("api/publicaciones")
public class PublicacionController {

    @Autowired
    private PublicacionService sPublicacion;

    @GetMapping
    public PublicacionRespuesta listarPublicaciones(
            @RequestParam(value = "pageNo", defaultValue = AppConstantes.NUMERO_PAGINA_DEFECTO,required = false)int numeroPagina,
            @RequestParam(value = "pageSize",defaultValue = AppConstantes.MEDIDA_PAGINA_DEFECTO, required = false) int sizePagina,
            @RequestParam(value = "sortBy",defaultValue = AppConstantes.ORDENAR_DEFECTO,required = false)String orderBy,
            @RequestParam(value="sortDir",defaultValue = AppConstantes.ORDENAR_DIRECCION_DEFECTO,required = false) String sortDir){
        return sPublicacion.obtenerPublicaciones(numeroPagina,sizePagina,orderBy,sortDir);
    }
    @GetMapping("/{id}")
    public ResponseEntity<PublicacionDTO> obtenerPublicacionByID(@PathVariable(name = "id") long id){
        return ResponseEntity.ok(sPublicacion.obtenerPublicacionByID(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PublicacionDTO> guardarPublicacion(@Valid @RequestBody PublicacionDTO publicacionDTO){
        return new ResponseEntity<>(sPublicacion.crearPublicacion(publicacionDTO), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PublicacionDTO> actualizarPublicacion(@Valid @RequestBody PublicacionDTO publicacionDTO,@PathVariable(name = "id") long id){
        PublicacionDTO publicacionRespuesta = sPublicacion.actualizarPublicacion(publicacionDTO,id);
        return new ResponseEntity<>(publicacionRespuesta,HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<String> eliminarPublicacion(@PathVariable(name = "id") long id){
        sPublicacion.eliminarPublicacion(id);
        return new ResponseEntity<>("Publicacion eliminada con exito",HttpStatus.OK);
    }
}
