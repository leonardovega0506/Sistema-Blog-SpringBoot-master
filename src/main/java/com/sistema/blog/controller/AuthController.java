package com.sistema.blog.controller;

import com.sistema.blog.dto.LoginDTO;
import com.sistema.blog.dto.RegistroDTO;
import com.sistema.blog.model.Rol;
import com.sistema.blog.model.Usuario;
import com.sistema.blog.repository.IRol;
import com.sistema.blog.repository.IUsuario;
import com.sistema.blog.security.JwtAuthResponseDTO;
import com.sistema.blog.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private IUsuario iUsuario;

    @Autowired
    private IRol iRol;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/iniciarSesion")
    public ResponseEntity<JwtAuthResponseDTO> authenticateUser(@RequestBody LoginDTO loginDTO){
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsernameOrEmail(),loginDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authenticate);

        //Obtener el token del jwtTokenProvider
        String token = jwtTokenProvider.generarToken(authenticate);

        return ResponseEntity.ok(new JwtAuthResponseDTO(token));
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarUsuario(@RequestBody RegistroDTO registroDTO){
        if(iUsuario.existsByUsername(registroDTO.getUsername())){
            return new ResponseEntity<>("Ese nombre de usuario ya existe",HttpStatus.ALREADY_REPORTED);
        }
        else if(iUsuario.existsByEmail(registroDTO.getEmail())){
            return new ResponseEntity<>("Ese email de usuario ya existe",HttpStatus.ALREADY_REPORTED);
        }
        else {
            Usuario usuario = new Usuario();
            usuario.setNombre(registroDTO.getNombre());
            usuario.setUsername(registroDTO.getUsername());
            usuario.setEmail(registroDTO.getEmail());
            usuario.setPassword(passwordEncoder.encode(registroDTO.getPassword()));

            Rol roles = iRol.findByNombre("ROLE_ADMIN").get();
            usuario.setRoles(Collections.singleton(roles));

            iUsuario.save(usuario);
            return new ResponseEntity<>("Usuario registrado",HttpStatus.CREATED);
        }
    }
}
