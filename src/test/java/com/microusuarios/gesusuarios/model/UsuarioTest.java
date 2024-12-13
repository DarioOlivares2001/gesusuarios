package com.microusuarios.gesusuarios.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UsuarioTest {

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        // Inicializamos el objeto Usuario antes de cada prueba
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setUsername("testUser");
        usuario.setEmail("testuser@example.com");
        usuario.setPassword("password123");
        usuario.setBirthdate("1990-01-01");
        usuario.setAddress("123 Main St, Anytown, USA");
    }

    @Test
    void testGettersAndSetters() {
        // Comprobamos que los valores asignados por los setters se obtienen correctamente con los getters

        assertEquals(1L, usuario.getId());
        assertEquals("testUser", usuario.getUsername());
        assertEquals("testuser@example.com", usuario.getEmail());
        assertEquals("password123", usuario.getPassword());
        assertEquals("1990-01-01", usuario.getBirthdate());
        assertEquals("123 Main St, Anytown, USA", usuario.getAddress());
    }

    @Test
    void testSetUsername() {
        // Comprobamos que el setter para 'username' funciona correctamente
        usuario.setUsername("newUsername");
        assertEquals("newUsername", usuario.getUsername());
    }

    @Test
    void testSetEmail() {
        // Comprobamos que el setter para 'email' funciona correctamente
        usuario.setEmail("newemail@example.com");
        assertEquals("newemail@example.com", usuario.getEmail());
    }

    @Test
    void testSetPassword() {
        // Comprobamos que el setter para 'password' funciona correctamente
        usuario.setPassword("newPassword123");
        assertEquals("newPassword123", usuario.getPassword());
    }

    @Test
    void testSetBirthdate() {
        // Comprobamos que el setter para 'birthdate' funciona correctamente
        usuario.setBirthdate("2000-12-12");
        assertEquals("2000-12-12", usuario.getBirthdate());
    }

    @Test
    void testSetAddress() {
        // Comprobamos que el setter para 'address' funciona correctamente
        usuario.setAddress("456 New Address, Newtown, USA");
        assertEquals("456 New Address, Newtown, USA", usuario.getAddress());
    }
}
