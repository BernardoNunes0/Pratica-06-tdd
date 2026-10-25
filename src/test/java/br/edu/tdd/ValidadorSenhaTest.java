package br.edu.tdd;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

class ValidadorSenhaTest {
    private final ValidadorSenha validador = new ValidadorSenha();

    @Test
    void deveRejeitarSenhaComSeteCaracteres() {
        ResultadoValidacao resultado = validador.validar("Abcd12!");
        assertFalse(resultado.valida());
        assertEquals(List.of("A senha deve ter pelo menos 8 caracteres"), resultado.erros());
    }

    @Test
    void deveAceitarSenhaComExatamenteOitoCaracteres() {
        ResultadoValidacao resultado = validador.validar("Abcde12!");
        assertTrue(resultado.valida());
        assertTrue(resultado.erros().isEmpty());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Abcdefg!", "Abcdef1!"})
    void deveRejeitarSenhaComMenosDeDoisDigitos(String senha) {
        ResultadoValidacao resultado = validador.validar(senha);
        assertFalse(resultado.valida());
        assertEquals(List.of("A senha deve conter pelo menos 2 dígitos"), resultado.erros());
    }
}
