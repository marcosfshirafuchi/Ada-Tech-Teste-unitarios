package tech.ada.ml_users.service;

import org.springframework.stereotype.Service;
import tech.ada.ml_users.dto.UsuarioDTO;
import tech.ada.ml_users.dto.mapper.UsuarioMapper;
import tech.ada.ml_users.exception.UsuarioNaoEncontradoException;
import tech.ada.ml_users.model.Usuario;
import tech.ada.ml_users.repository.UsuariosRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BuscarUsuariosService {

    private final UsuariosRepository usuariosRepository;

    public BuscarUsuariosService(UsuariosRepository usuariosRepository) {
        this.usuariosRepository = usuariosRepository;
    }

    public List<UsuarioDTO> buscarTodosOsUsuarios() {
        return usuariosRepository.findAll()
                .stream()
                .map(usuario -> UsuarioMapper.toUsuarioDTO(usuario))
                .collect(Collectors.toList());
    }

    public Usuario buscarUsuarioPorId(Long id) {
        Optional<Usuario> usuarioOptional = usuariosRepository.findById(id);
        return usuarioOptional
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário com ID " + id + " não encontrado"));
    }
}
