package br.edu.tdd;

import java.util.ArrayList;
import java.util.List;

public class ValidadorSenha {
    public ResultadoValidacao validar(String senha) {
        List<String> erros = new ArrayList<>();
        if (senha.length() < 8) {
            erros.add("A senha deve ter pelo menos 8 caracteres");
        }
        return new ResultadoValidacao(erros.isEmpty(), erros);
    }
}
