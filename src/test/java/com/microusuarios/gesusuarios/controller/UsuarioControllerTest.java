package com.microusuarios.gesusuarios.controller;

import com.microusuarios.gesusuarios.model.Usuario;
import com.microusuarios.gesusuarios.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class UsuarioControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        // Configura un usuario de ejemplo
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setUsername("testUser");
        usuario.setEmail("testuser@example.com");
        usuario.setPassword("password123");
        usuario.setBirthdate("1990-01-01");
        usuario.setAddress("123 Main St");
    }

    @Test
    void testListarUsuarios() throws Exception {
        // Simula la respuesta del servicio
        when(usuarioService.listarUsuarios()).thenReturn(Arrays.asList(usuario));

        // Realiza la solicitud GET y verifica la respuesta
        mockMvc.perform(get("/api/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("testUser"))
                .andExpect(jsonPath("$[0].email").value("testuser@example.com"));

        // Verifica que el servicio haya sido llamado una vez
        verify(usuarioService, times(1)).listarUsuarios();
    }

    @Test
    void testCrearUsuario() throws Exception {
        // Simula la respuesta del servicio
        when(usuarioService.guardarUsuario(Mockito.any(Usuario.class))).thenReturn(usuario);

        // Realiza la solicitud POST y verifica la respuesta
        mockMvc.perform(post("/api/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"testUser\", \"email\":\"testuser@example.com\", \"password\":\"password123\", \"birthdate\":\"1990-01-01\", \"address\":\"123 Main St\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("testUser"))
                .andExpect(jsonPath("$.email").value("testuser@example.com"));

        // Verifica que el servicio haya sido llamado una vez
        verify(usuarioService, times(1)).guardarUsuario(Mockito.any(Usuario.class));
    }

    @Test
    void testObtenerUsuario() throws Exception {
        // Simula la respuesta del servicio
        when(usuarioService.obtenerUsuario(1L)).thenReturn(Optional.of(usuario));

        // Realiza la solicitud GET y verifica la respuesta
        mockMvc.perform(get("/api/usuarios/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testUser"))
                .andExpect(jsonPath("$.email").value("testuser@example.com"));

        // Verifica que el servicio haya sido llamado una vez
        verify(usuarioService, times(1)).obtenerUsuario(1L);
    }

    @Test
    void testActualizarUsuario() throws Exception {
        // Simula la respuesta del servicio
        when(usuarioService.obtenerUsuario(1L)).thenReturn(Optional.of(usuario));
        when(usuarioService.guardarUsuario(Mockito.any(Usuario.class))).thenReturn(usuario);

        // Realiza la solicitud PUT y verifica la respuesta
        mockMvc.perform(put("/api/usuarios/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"updatedUser\", \"email\":\"updateduser@example.com\", \"password\":\"newpassword123\", \"birthdate\":\"1990-01-01\", \"address\":\"123 Main St\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("updatedUser"))
                .andExpect(jsonPath("$.email").value("updateduser@example.com"));

        // Verifica que el servicio haya sido llamado una vez
        verify(usuarioService, times(1)).obtenerUsuario(1L);
        verify(usuarioService, times(1)).guardarUsuario(Mockito.any(Usuario.class));
    }

    @Test
    void testEliminarUsuario() throws Exception {
        // Simula la respuesta del servicio
        when(usuarioService.obtenerUsuario(1L)).thenReturn(Optional.of(usuario));

        // Realiza la solicitud DELETE y verifica la respuesta
        mockMvc.perform(delete("/api/usuarios/{id}", 1L))
                .andExpect(status().isNoContent());

        // Verifica que el servicio haya sido llamado una vez
        verify(usuarioService, times(1)).obtenerUsuario(1L);
        verify(usuarioService, times(1)).eliminarUsuario(1L);
    }

    @Test
    void testCrearUsuariosMasivo() throws Exception {
        // Simula la respuesta del servicio para múltiples usuarios
        when(usuarioService.guardarUsuario(Mockito.any(Usuario.class))).thenReturn(usuario);

        // Realiza la solicitud POST masiva y verifica la respuesta
        mockMvc.perform(post("/api/usuarios/bulk")
                .contentType(MediaType.APPLICATION_JSON)
                .content("[{\"username\":\"user1\", \"email\":\"user1@example.com\", \"password\":\"password123\", \"birthdate\":\"1990-01-01\", \"address\":\"123 Main St\"}, " +
                        "{\"username\":\"user2\", \"email\":\"user2@example.com\", \"password\":\"password456\", \"birthdate\":\"1992-02-02\", \"address\":\"456 Second St\"}]"))
                .andExpect(status().isCreated());

        // Verifica que el servicio haya sido llamado para cada producto
        verify(usuarioService, times(2)).guardarUsuario(Mockito.any(Usuario.class));
    }
}
