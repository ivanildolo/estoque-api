package com.ti.estoque.dto;

import com.ti.estoque.models.Usuario;

public record DadosDetalheUsuario(String login, Boolean ativo) {

    public DadosDetalheUsuario(Usuario usuario) {
        this(
            usuario.getLogin(),
            usuario.getAtivo());
    }
}
