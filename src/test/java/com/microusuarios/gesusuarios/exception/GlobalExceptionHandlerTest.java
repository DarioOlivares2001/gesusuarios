package com.microusuarios.gesusuarios.exception;

import com.microusuarios.gesusuarios.controller.UsuarioController;
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

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class GlobalExceptionHandlerTest {

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
    void testHandleResourceNotFoundException() throws Exception {
        // Simula que no se encuentra el usuario
        when(usuarioService.obtenerUsuario(1L)).thenReturn(Optional.empty());

        // Realiza la solicitud GET para un usuario que no existe
        mockMvc.perform(get("/api/usuarios/{id}", 1L))
                .andExpect(status().isNotFound())  // Verifica el código 404 (Not Found)
                .andExpect(content().string("Usuario con ID 1 no fue encontrado."));  // Verifica el mensaje de error
    }
}
