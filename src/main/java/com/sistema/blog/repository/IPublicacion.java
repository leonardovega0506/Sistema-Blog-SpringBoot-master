package com.sistema.blog.repository;

import com.sistema.blog.model.Publicacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPublicacion extends JpaRepository<Publicacion,Long> {
}
