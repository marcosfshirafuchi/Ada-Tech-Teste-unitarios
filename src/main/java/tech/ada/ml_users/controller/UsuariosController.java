package tech.ada.ml_users.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.ada.ml_users.dto.CriarUsuarioRequestDTO;
import tech.ada.ml_users.dto.UsuarioDTO;
import tech.ada.ml_users.dto.mapper.CriarUsuarioRequestMapper;
import tech.ada.ml_users.exception.UsuarioNaoEncontradoException;
import tech.ada.ml_users.model.Usuario;
import tech.ada.ml_users.service.AtualizarUsuarioService;
import tech.ada.ml_users.service.BuscarUsuariosService;
import tech.ada.ml_users.service.CriarUsuarioService;
import tech.ada.ml_users.service.DeletarUsuarioService;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuariosController {

    private final BuscarUsuariosService buscarUsuariosService;
    private final CriarUsuarioService criarUsuarioService;
    private final AtualizarUsuarioService atualizarUsuarioService;
    private final DeletarUsuarioService deletarUsuarioService;

    public UsuariosController(BuscarUsuariosService buscarUsuariosService,
                              CriarUsuarioService criarUsuarioService,
                              AtualizarUsuarioService atualizarUsuarioService,
                              DeletarUsuarioService deletarUsuarioService) {
        this.buscarUsuariosService = buscarUsuariosService;
        this.criarUsuarioService = criarUsuarioService;
        this.atualizarUsuarioService = atualizarUsuarioService;
        this.deletarUsuarioService = deletarUsuarioService;
    }

    @GetMapping
    @Operation(summary = "Listar todos os usuários",
            description = "Retorna uma lista de todos os usuários cadastrados.")
    public List<UsuarioDTO> buscarTodosOsUsuarios() {
        return buscarUsuariosService.buscarTodosOsUsuarios();
    }

    @GetMapping(path = "/{id}")
    @Operation(summary = "Buscar usuário por ID",
            description = "Retorna um usuário específico com base no ID fornecido.")
    public Usuario buscarUsuarioPorId(@PathVariable(value = "id") Long id) {
        return buscarUsuariosService.buscarUsuarioPorId(id);
    }

    @PostMapping
    @Operation(summary = "Criar um novo usuário",
            description = "Cria um novo usuário com os dados fornecidos.")
    public ResponseEntity<Usuario> criarUsuario(@RequestBody @Valid CriarUsuarioRequestDTO usuario) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(criarUsuarioService.criarUsuario(CriarUsuarioRequestMapper.toEntity(usuario)));
    }

    @PutMapping("/{id}")
        @Operation(summary = "Atualizar um usuário existente",
                description = "Atualiza os dados de um usuário existente com base no ID fornecido.")
    public ResponseEntity<Void> atualizarUsuario(@PathVariable(value = "id") Long id,
                                                 @RequestBody @Valid CriarUsuarioRequestDTO usuario) {
        atualizarUsuarioService.atualizarUsuario(CriarUsuarioRequestMapper.toEntity(usuario), id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar um usuário",
            description = "Deleta um usuário específico com base no ID fornecido.")
    public ResponseEntity<Void> deletarUsuario(@PathVariable(value = "id") Long id) {
         deletarUsuarioService.deletarUsuarioPorId(id);
         return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
