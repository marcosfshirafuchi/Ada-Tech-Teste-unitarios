package tech.ada.ml_users.dto.mapper;

import tech.ada.ml_users.dto.EnderecoDTO;
import tech.ada.ml_users.model.Endereco;

public class EnderecoDTOMapper {

    public static Endereco toEntity(EnderecoDTO enderecoDTO) {
        Endereco endereco = new Endereco();
        endereco.setCep(enderecoDTO.getCep());
        endereco.setLogradouro(enderecoDTO.getLogradouro());
        endereco.setBairro(enderecoDTO.getBairro());
        endereco.setLocalidade(enderecoDTO.getLocalidade());
        return endereco;
    }


    public static EnderecoDTO toDto(Endereco endereco) {
        EnderecoDTO enderecoDto = new EnderecoDTO();
        enderecoDto.setCep(endereco.getCep());
        enderecoDto.setLogradouro(endereco.getLogradouro());
        enderecoDto.setBairro(endereco.getBairro());
        enderecoDto.setLocalidade(endereco.getLocalidade());
        return enderecoDto;
    }
}
