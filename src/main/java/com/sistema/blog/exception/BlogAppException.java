package com.sistema.blog.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class BlogAppException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    private HttpStatus estado;
    private String mensaje;

    public BlogAppException(HttpStatus estado, String mensaje) {
        this.estado = estado;
        this.mensaje = mensaje;
    }
    public BlogAppException(HttpStatus estado, String mensaje,String mensaje1) {
        this.estado = estado;
        this.mensaje = mensaje;
        this.mensaje = mensaje1;
    }

}
