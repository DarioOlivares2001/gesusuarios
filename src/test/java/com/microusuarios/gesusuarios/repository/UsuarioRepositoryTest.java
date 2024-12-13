package com.microusuarios.gesusuarios.repository;

import com.microusuarios.gesusuarios.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        // Configura un usuario para las pruebas
        usuario = new Usuario();
        usuario.setUsername("testUser");
        usuario.setEmail("testuser@example.com");
        usuario.setPassword("password123");
        usuario.setBirthdate("1990-01-01");
        usuario.setAddress("123 Main St");
        
        // Guardar el usuario en la base de datos
        usuario = usuarioRepository.save(usuario);
    }

    @Test
    void testSaveUsuario() {
        // Verifica que el usuario se haya guardado correctamente
        assertNotNull(usuario.getId(), "El usuario debe tener un ID asignado");
        assertEquals("testUser", usuario.getUsername());
        assertEquals("testuser@example.com", usuario.getEmail());
    }

    @Test
    void testFindById() {
        // Busca el usuario por ID
        Optional<Usuario> usuarioEncontrado = usuarioRepository.findById(usuario.getId());
        assertTrue(usuarioEncontrado.isPresent(), "El usuario debe ser encontrado");
        assertEquals(usuario.getUsername(), usuarioEncontrado.get().getUsername());
    }

    @Test
    void testUpdateUsuario() {
        // Actualiza el nombre de usuario y guarda los cambios
        usuario.setUsername("updatedUser");
        usuarioRepository.save(usuario);

        // Verifica que los cambios se hayan guardado
        Optional<Usuario> usuarioActualizado = usuarioRepository.findById(usuario.getId());
        assertTrue(usuarioActualizado.isPresent(), "El usuario debe ser encontrado");
        assertEquals("updatedUser", usuarioActualizado.get().getUsername());
    }

    @Test
    void testDeleteUsuario() {
        // Elimina el usuario
        Long id = usuario.getId();
        usuarioRepository.deleteById(id);

        // Verifica que el usuario haya sido eliminado
        Optional<Usuario> usuarioEliminado = usuarioRepository.findById(id);
        assertFalse(usuarioEliminado.isPresent(), "El usuario debe haber sido eliminado");
    }

    @Test
    void testFindAllUsuarios() {
        // Verifica que el usuario se encuentre en la lista
        Usuario usuario2 = new Usuario();
        usuario2.setUsername("testUser2");
        usuario2.setEmail("testuser2@example.com");
        usuario2.setPassword("password123");
        usuario2.setBirthdate("1992-01-01");
        usuario2.setAddress("456 Another St");
        usuarioRepository.save(usuario2);

        assertTrue(usuarioRepository.findAll().size() > 1, "La lista de usuarios debe tener más de un elemento");
    }
}
