package com.sistema.blog.security;

import com.sistema.blog.model.Rol;
import com.sistema.blog.model.Usuario;
import com.sistema.blog.repository.IUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private IUsuario iUsuario;
    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        Usuario usuario = iUsuario.findByUsernameOrEmail(usernameOrEmail,usernameOrEmail).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con ese username o emial: "+usernameOrEmail));

        return new User(usuario.getEmail(),usuario.getPassword(),mapearRoles(usuario.getRoles()));
    }
    private Collection<? extends GrantedAuthority> mapearRoles(Set<Rol> roles){
        return roles.stream().map(rol -> new SimpleGrantedAuthority(rol.getNombre())).collect(Collectors.toList());
    }
}
