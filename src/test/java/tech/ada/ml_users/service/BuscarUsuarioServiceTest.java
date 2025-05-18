package tech.ada.ml_users.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import tech.ada.ml_users.dto.UsuarioDTO;
import tech.ada.ml_users.exception.UsuarioNaoEncontradoException;
import tech.ada.ml_users.model.Endereco;
import tech.ada.ml_users.model.Usuario;
import tech.ada.ml_users.repository.UsuariosRepository;

import java.util.List;
import java.util.Optional;

public class BuscarUsuarioServiceTest {

    //Precisa de uma instancia de UsuarioService
    BuscarUsuariosService service;
    UsuariosRepository repository;

    @BeforeEach
    public void setUp(){
        //Temos que mokar a classe repository
        repository = Mockito.mock(UsuariosRepository.class);
        service = new BuscarUsuariosService(repository);
    }

    @Test
    void deveRetornarUsuarioBuscadoPorIdComSucesso(){
        //Cenario
        Long id = 1L;

        //Criar um objeto usuario
        Usuario usuarioQueEstaSalvoNoBancoDeDados = new Usuario();
        usuarioQueEstaSalvoNoBancoDeDados.setId(id);
        usuarioQueEstaSalvoNoBancoDeDados.setNome("Rodolfo");

        //Ensina o mockito, quando a classe repository é chamada, deve retornar o usuario que está salvo no banco de dados
        Mockito.when(repository.findById(id)).thenReturn(Optional.of(usuarioQueEstaSalvoNoBancoDeDados));


        //Execução
        Usuario usuarioRetornado = service.buscarUsuarioPorId(id);

        //Validação

        //Valida se o usuario não é nulo
        Assertions.assertNotNull(usuarioRetornado);

        //Valida se o Id do usuário é igual ao Id retornado
        Assertions.assertEquals(id,usuarioRetornado.getId());

        //Valida se o nome Rodolfo é igual o nome retornado
        Assertions.assertEquals("Rodolfo",usuarioRetornado.getNome());
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoEncontrado(){

        //Cenário
        Long id = 1L;

        Mockito.when(repository.findById(id)).thenReturn(Optional.empty());

        //Execução
        UsuarioNaoEncontradoException exception = Assertions.assertThrows(UsuarioNaoEncontradoException.class, () -> service.buscarUsuarioPorId(id));

        //Verificação
        Assertions.assertNotNull(exception);
        Assertions.assertEquals("Usuário com ID " + id + " não encontrado", exception.getMessage());
    }

    @Test
    void deveBuscarTodosOsUsuariosComSucesso(){
        //Cenario
        Usuario usuario1 = new Usuario();
        usuario1.setNome("Rodolfo");
        usuario1.setId(1L);
        Endereco endereco1 = new Endereco();
        endereco1.setCep("7100000");
        usuario1.setEndereco(endereco1);

        Usuario usuario2 = new Usuario();
        usuario2.setNome("Helena");
        usuario2.setId(2L);
        Endereco endereco2 = new Endereco();
        endereco2.setCep("8000000");
        usuario2.setEndereco(endereco2);

        Mockito.when(repository.findAll()).thenReturn(List.of(usuario1,usuario2));

        //Execução
        List<UsuarioDTO> usuariosDTO = service.buscarTodosOsUsuarios();

        //Validação
        Assertions.assertNotNull(usuariosDTO);
        Assertions.assertEquals(2,usuariosDTO.size());
        Assertions.assertEquals("Rodolfo",usuariosDTO.get(0).getNome());
        Assertions.assertEquals("Helena",usuariosDTO.get(1).getNome());
    }

}
