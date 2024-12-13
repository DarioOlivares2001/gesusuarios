package com.microusuarios.gesusuarios.service;

import com.microusuarios.gesusuarios.model.Usuario;
import com.microusuarios.gesusuarios.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Inicializa un usuario de prueba
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setUsername("testUser");
        usuario.setEmail("testuser@example.com");
        usuario.setPassword("password123");
        usuario.setBirthdate("1990-01-01");
        usuario.setAddress("123 Main St");
    }

    @Test
    void testListarUsuarios() {
        // Simula la respuesta del repositorio
        when(usuarioRepository.findAll()).thenReturn(List.of(usuario));

        // Llama al método listarUsuarios()
        List<Usuario> usuarios = usuarioService.listarUsuarios();

        // Verifica que la lista no esté vacía y contenga el usuario correcto
        assertNotNull(usuarios);
        assertEquals(1, usuarios.size());
        assertEquals("testUser", usuarios.get(0).getUsername());

        // Verifica que el repositorio haya sido llamado
        verify(usuarioRepository, times(1)).findAll();
    }

    @Test
    void testObtenerUsuario() {
        // Simula la respuesta del repositorio para buscar un usuario por ID
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        // Llama al método obtenerUsuario()
        Optional<Usuario> usuarioEncontrado = usuarioService.obtenerUsuario(1L);

        // Verifica que el usuario haya sido encontrado
        assertTrue(usuarioEncontrado.isPresent());
        assertEquals("testUser", usuarioEncontrado.get().getUsername());

        // Verifica que el repositorio haya sido llamado
        verify(usuarioRepository, times(1)).findById(1L);
    }

    @Test
    void testGuardarUsuario() {
        // Simula la respuesta del repositorio para guardar el usuario
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        // Llama al método guardarUsuario()
        Usuario usuarioGuardado = usuarioService.guardarUsuario(usuario);

        // Verifica que el usuario guardado sea el mismo que el pasado al servicio
        assertNotNull(usuarioGuardado);
        assertEquals("testUser", usuarioGuardado.getUsername());

        // Verifica que el repositorio haya sido llamado
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    void testEliminarUsuario() {
        // Simula la respuesta del repositorio para buscar un usuario por ID
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        // Llama al método eliminarUsuario()
        usuarioService.eliminarUsuario(1L);

        // Verifica que el repositorio haya sido llamado para eliminar el usuario
        verify(usuarioRepository, times(1)).deleteById(1L);
    }

    @Test
    void testObtenerUsuarioNoEncontrado() {
        // Simula que el usuario no es encontrado en el repositorio
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        // Llama al método obtenerUsuario() y verifica que no se encuentre el usuario
        Optional<Usuario> usuarioNoEncontrado = usuarioService.obtenerUsuario(1L);

        // Verifica que el resultado esté vacío
        assertFalse(usuarioNoEncontrado.isPresent(), "El usuario no debe estar presente");

        // Verifica que el repositorio haya sido llamado
        verify(usuarioRepository, times(1)).findById(1L);
    }
}
