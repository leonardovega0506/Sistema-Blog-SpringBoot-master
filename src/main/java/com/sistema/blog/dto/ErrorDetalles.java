package com.sistema.blog.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ErrorDetalles {
    private Date marcaTiempo;
    private String mensaje;
    private String detalle;

    public ErrorDetalles(Date marcaTiempo, String mensaje, String detalle) {
        this.marcaTiempo = marcaTiempo;
        this.mensaje = mensaje;
        this.detalle = detalle;
    }

    public ErrorDetalles() {
    }
}
