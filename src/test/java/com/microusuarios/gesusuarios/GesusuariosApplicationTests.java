package com.microusuarios.gesusuarios;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class GesusuariosApplicationTests {

    @Test
    void contextLoads() {
        // Verifica que el contexto de la aplicación Spring Boot se cargue correctamente.
    }

    @Test
    void applicationStarts() {
        GesusuariosApplication.main(new String[]{});
        assertThat(true).isTrue(); // Simple validación de que el flujo no lanza excepciones
    }
}


