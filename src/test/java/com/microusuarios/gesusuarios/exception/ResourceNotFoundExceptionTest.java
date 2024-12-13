package com.microusuarios.gesusuarios.exception;

import com.microusuarios.gesusuarios.model.Usuario;
import com.microusuarios.gesusuarios.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class ResourceNotFoundExceptionTest {

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
        // Simula que el servicio no encuentra el usuario con ID 1
        when(usuarioService.obtenerUsuario(1L)).thenThrow(new ResourceNotFoundException("Usuario con ID 1 no fue encontrado."));

        // Realiza la solicitud GET para un usuario que no existe
        mockMvc.perform(get("/api/usuarios/{id}", 1L))
                .andExpect(status().isNotFound())  // Verifica que el código de estado sea 404 Not Found
                .andExpect(content().string("Usuario con ID 1 no fue encontrado."));  // Verifica el mensaje de error
    }
}
