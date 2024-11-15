package com.microusuarios.gesusuarios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.microusuarios.gesusuarios.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
