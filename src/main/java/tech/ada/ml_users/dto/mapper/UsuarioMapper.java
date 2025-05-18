package tech.ada.ml_users.dto.mapper;

import tech.ada.ml_users.dto.EnderecoDTO;
import tech.ada.ml_users.dto.UsuarioDTO;
import tech.ada.ml_users.model.Usuario;

public class UsuarioMapper {

    public static UsuarioDTO toUsuarioDTO(Usuario usuario) {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setEndereco(EnderecoDTOMapper.toDto(usuario.getEndereco()));
        usuarioDTO.setEmail(usuario.getEmail());
        usuarioDTO.setIdade(usuario.getIdade());
        usuarioDTO.setNome(usuario.getNome());
        return usuarioDTO;
    }

}
