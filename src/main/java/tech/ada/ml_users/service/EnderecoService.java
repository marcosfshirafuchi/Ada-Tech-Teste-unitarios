package tech.ada.ml_users.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tech.ada.ml_users.dto.EnderecoDTO;
import tech.ada.ml_users.dto.mapper.EnderecoDTOMapper;
import tech.ada.ml_users.model.Endereco;

@Service
public class EnderecoService {

    @Value("${url.viacep}")
    private String urlViaCep;

    public Endereco obterEnderecoPeloCep(String cep) {
        RestTemplate restTemplate = new RestTemplate();
        EnderecoDTO enderecoViaCep =  restTemplate.getForObject(String.format(urlViaCep, cep), EnderecoDTO.class);
        return EnderecoDTOMapper.toEntity(enderecoViaCep);
    }

}
