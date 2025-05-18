package tech.ada.ml_users.service;


import org.springframework.stereotype.Service;
import tech.ada.ml_users.model.Usuario;
import tech.ada.ml_users.repository.UsuariosRepository;

import java.time.LocalDateTime;

@Service
public class CriarUsuarioService {

    private final UsuariosRepository repository;
    private final EnderecoService enderecoService;

    public CriarUsuarioService(UsuariosRepository repository,
                               EnderecoService enderecoService) {
        this.repository = repository;
        this.enderecoService = enderecoService;
    }

    public Usuario criarUsuario(Usuario usuario) {
        usuario.setEndereco(enderecoService.obterEnderecoPeloCep(usuario.getEndereco().getCep()));
        usuario.setDataCriacao(LocalDateTime.now());
        return repository.save(usuario);
    }
}
