package tech.ada.ml_users.service;

import org.springframework.stereotype.Service;
import tech.ada.ml_users.exception.UsuarioNaoEncontradoException;
import tech.ada.ml_users.repository.UsuariosRepository;

@Service
public class DeletarUsuarioService {

    private final UsuariosRepository repository;
    private final BuscarUsuariosService buscarUsuariosService;

    public DeletarUsuarioService(UsuariosRepository repository,
                                 BuscarUsuariosService buscarUsuariosService) {
        this.repository = repository;
        this.buscarUsuariosService = buscarUsuariosService;
    }

    public void deletarUsuarioPorId(Long id) {
        buscarUsuariosService.buscarUsuarioPorId(id);
        repository.deleteById(id);
    }

}
