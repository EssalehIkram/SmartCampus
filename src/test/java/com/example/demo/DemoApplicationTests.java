package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class DemoApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void testForceEchec() {
        // On force une assertion fausse pour que Jenkins détecte une erreur
        assertEquals(true, true, "erreur de test");
    }
}