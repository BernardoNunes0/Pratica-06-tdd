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

    @Test
    void deveRejeitarSenhaSemLetraMaiuscula() {
        ResultadoValidacao resultado = validador.validar("abcde12!");
        assertFalse(resultado.valida());
        assertEquals(List.of("A senha deve conter pelo menos uma letra maiuscula"), resultado.erros());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Abcdef12", "Abcde12 "})
    void deveRejeitarSenhaSemCaractereEspecial(String senha) {
        ResultadoValidacao resultado = validador.validar(senha);
        assertFalse(resultado.valida());
        assertEquals(List.of("A senha deve conter pelo menos um caractere especial"), resultado.erros());
    }

    @ParameterizedTest
    @ValueSource(strings = {"mypass", ""})
    void deveRetornarTodosOsErrosDeValidacao(String senha) {
        ResultadoValidacao resultado = validador.validar(senha);
        assertFalse(resultado.valida());
        assertEquals(List.of(
                "A senha deve ter pelo menos 8 caracteres",
                "A senha deve conter pelo menos 2 dígitos",
                "A senha deve conter pelo menos uma letra maiuscula",
                "A senha deve conter pelo menos um caractere especial"
        ), resultado.erros());
    }

    @Test
    void deveRetornarSomenteAsRegrasVioladas() {
        ResultadoValidacao resultado = validador.validar("abcdefgh");
        assertFalse(resultado.valida());
        assertEquals(List.of(
                "A senha deve conter pelo menos 2 dígitos",
                "A senha deve conter pelo menos uma letra maiuscula",
                "A senha deve conter pelo menos um caractere especial"
        ), resultado.erros());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Abcde12!", "SENHA123@", "Abcde12#", "Abcde12_", "Ábcde12!", "A123456!"})
    void deveAceitarSenhasQueAtendemTodasAsRegras(String senha) {
        ResultadoValidacao resultado = validador.validar(senha);
        assertTrue(resultado.valida());
        assertEquals(List.of(), resultado.erros());
    }

    @Test
    void deveManterResultadosIndependentesEntreChamadas() {
        ResultadoValidacao invalida = validador.validar("mypass");
        ResultadoValidacao valida = validador.validar("Abcde12!");
        assertEquals(4, invalida.erros().size());
        assertTrue(valida.valida());
        assertTrue(valida.erros().isEmpty());
    }
}
