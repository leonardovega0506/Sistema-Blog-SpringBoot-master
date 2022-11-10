package com.sistema.blog.repository;

import com.sistema.blog.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRol extends JpaRepository<Rol, Long> {

    public Optional<Rol> findByNombre(String nombre);
}
