package com.ti.estoque.controllers;

import com.ti.estoque.dto.DadosAutenticacao;
import com.ti.estoque.dto.DadosDetalheUsuario;
import com.ti.estoque.dto.DadosTokenWT;
import com.ti.estoque.models.Usuario;
import com.ti.estoque.repository.UsuarioRepository;
import com.ti.estoque.services.TokenService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

	@Autowired
	private AuthenticationManager manager;

	@Autowired
	private TokenService tokenService;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@PostMapping
	@Transactional
	public ResponseEntity<?> login(@RequestBody @Valid DadosAutenticacao dados) {
		var tokenJWT = tokenService.generateToken(dados.login());
		return ResponseEntity.ok(new DadosTokenWT(tokenJWT));
	}


	@PostMapping("/create")
	@Transactional
	public ResponseEntity<DadosDetalheUsuario> cadastrar(@RequestBody @Valid DadosAutenticacao dados, UriComponentsBuilder uriBuilder) {
		String encodedPassword = new BCryptPasswordEncoder().encode(dados.senha());
		Usuario newUser = new Usuario(null, dados.login(), encodedPassword, true);
		newUser = usuarioRepository.save(newUser);
		var uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(newUser.getId()).toUri();
		return ResponseEntity.created(uri).body(new DadosDetalheUsuario(newUser));
	}

}
