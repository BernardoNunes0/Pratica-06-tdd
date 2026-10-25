package br.edu.tdd;

import java.util.List;

public record ResultadoValidacao(boolean valida, List<String> erros) {
    public ResultadoValidacao {
        erros = List.copyOf(erros);
    }
}
