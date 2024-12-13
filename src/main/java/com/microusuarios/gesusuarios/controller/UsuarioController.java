package com.microusuarios.gesusuarios.controller;

import java.util.List;

import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.microusuarios.gesusuarios.exception.ResourceNotFoundException;
import com.microusuarios.gesusuarios.model.Usuario;
import com.microusuarios.gesusuarios.service.UsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "http://localhost:4200") // Permite solicitudes desde el frontend
@Validated
public class UsuarioController {

    private static final Logger log = LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    private UsuarioService usuarioService;

    // Listar usuarios
    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        List<Usuario> usuarios = usuarioService.listarUsuarios();
        log.info("GET /usuarios - Listando todos los usuarios");
        return ResponseEntity.ok(usuarios);
    }

    // Crear usuario
    @PostMapping
    public ResponseEntity<Usuario> crearUsuario(@Valid @RequestBody Usuario usuario) {
        log.info("POST /usuarios - Creando usuario: " + usuario.getUsername());
        Usuario nuevoUsuario = usuarioService.guardarUsuario(usuario);
        return new ResponseEntity<>(nuevoUsuario, HttpStatus.CREATED);
    }

    // Obtener usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuario(@PathVariable Long id) {
        log.info("GET /usuarios/" + id);
        Usuario usuario = usuarioService.obtenerUsuario(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario con ID " + id + " no fue encontrado."));
        return ResponseEntity.ok(usuario);
    }

    // Actualizar usuario
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Long id, @Valid @RequestBody Usuario detallesUsuario) {
        log.info("PUT /usuarios/" + id + " - Actualizando usuario");
        Usuario usuario = usuarioService.obtenerUsuario(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario con ID " + id + " no fue encontrado."));

        usuario.setUsername(detallesUsuario.getUsername());
        usuario.setPassword(detallesUsuario.getPassword());
        usuario.setEmail(detallesUsuario.getEmail());
        usuario.setBirthdate(detallesUsuario.getBirthdate());
        usuario.setAddress(detallesUsuario.getAddress());

        Usuario usuarioActualizado = usuarioService.guardarUsuario(usuario);
        return ResponseEntity.ok(usuarioActualizado);
    }

    // Eliminar usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        log.info("DELETE /usuarios/" + id);
        usuarioService.obtenerUsuario(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario con ID " + id + " no fue encontrado."));
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<Usuario>> crearUsuariosMasivamente(@Valid @RequestBody List<Usuario> usuarios) {
        log.info("POST /usuarios/bulk - Creando usuarios masivamente");
        List<Usuario> usuariosGuardados = usuarios.stream()
                .map(usuarioService::guardarUsuario)
                .collect(Collectors.toList());
        return new ResponseEntity<>(usuariosGuardados, HttpStatus.CREATED);
    }
}