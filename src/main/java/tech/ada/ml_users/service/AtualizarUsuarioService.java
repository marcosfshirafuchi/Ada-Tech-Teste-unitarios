package tech.ada.ml_users.service;

import org.springframework.stereotype.Service;
import tech.ada.ml_users.exception.UsuarioNaoEncontradoException;
import tech.ada.ml_users.model.Usuario;
import tech.ada.ml_users.repository.UsuariosRepository;

import java.time.LocalDateTime;

@Service
public class AtualizarUsuarioService {

    private final UsuariosRepository repository;
    private final BuscarUsuariosService buscarUsuariosService;

    public AtualizarUsuarioService(UsuariosRepository repository, BuscarUsuariosService buscarUsuariosService) {
        this.repository = repository;
        this.buscarUsuariosService = buscarUsuariosService;
    }

    public void atualizarUsuario(Usuario usuario, Long id) {
       buscarUsuariosService.buscarUsuarioPorId(id);
       usuario.setId(id);
       usuario.setDataAtualizacao(LocalDateTime.now());
       repository.save(usuario);
    }

}
