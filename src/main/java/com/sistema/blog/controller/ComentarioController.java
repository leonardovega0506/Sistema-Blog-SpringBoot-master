package com.sistema.blog.controller;

import com.sistema.blog.dto.ComentarioDTO;
import com.sistema.blog.service.ComentarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class ComentarioController {

    @Autowired
    private ComentarioService sComentario;

    @GetMapping("publicaciones/{publicacionId}/comentarios")
    public List<ComentarioDTO> listarComentariosPublicacion(@PathVariable(value = "publicacionId") long publicacionId){
        return sComentario.obtenerComentariosByID(publicacionId);
    }
    @GetMapping("/publicaciones/{publicacionId}")
    @PostMapping("/publicaciones/{publicacionId}/comentarios")
    public ResponseEntity<ComentarioDTO> guardarComentario(@PathVariable(value = "publicacionId") long publicacionId,@Valid  @RequestBody ComentarioDTO comentarioDTO){
        return new ResponseEntity<>(sComentario.crearComentario(publicacionId,comentarioDTO), HttpStatus.CREATED);
    }
}
