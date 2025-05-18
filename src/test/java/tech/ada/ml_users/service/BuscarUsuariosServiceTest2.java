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

public class BuscarUsuariosServiceTest2 {
    private BuscarUsuariosService service;
    private UsuariosRepository repository;

    @BeforeEach
    public void setUp(){
        repository = Mockito.mock(UsuariosRepository.class);
        service = new BuscarUsuariosService(repository);
    }


    @Test
    void deveRetornarUmUsuarioBuscadoComIdComSucesso(){
        //Cenário
        Long id = 1L;
        Usuario usuarioSalvoNoBancoDeDados =  new Usuario();
        usuarioSalvoNoBancoDeDados.setId(id);
        usuarioSalvoNoBancoDeDados.setNome("Marcos");

        Mockito.when(repository.findById(id)).thenReturn(Optional.of(usuarioSalvoNoBancoDeDados));

        //Execução
        Usuario usuarioRetornado = service.buscarUsuarioPorId(id);

        //Validação
        Assertions.assertNotNull(usuarioRetornado);
        Assertions.assertEquals(id,usuarioRetornado.getId());
        Assertions.assertEquals("Marcos",usuarioRetornado.getNome());
    }

    @Test
    void deveLancarUmaExcecaoQuandoUmUusarioNaoEncontrado(){
        //Cenário
        Long id = 10L;
        Mockito.when(repository.findById(id)).thenReturn(Optional.empty());

        //Execução
        UsuarioNaoEncontradoException exception = Assertions.assertThrows(
                UsuarioNaoEncontradoException.class, () -> service.buscarUsuarioPorId(id));

        //Validação
        Assertions.assertNotNull(exception);
        Assertions.assertEquals("Usuário com ID " + id + " não encontrado", exception.getMessage());
    }

    @Test
    void deveEncontrarTodosUsuariosCadastrados(){
        //Cenário
        Usuario usuario1 = new Usuario();
        usuario1.setId(1L);
        usuario1.setNome("Marcos");
        Endereco endereco1 = new Endereco();
        endereco1.setCep("7100000");
        usuario1.setEndereco(endereco1);

        Usuario usuario2 = new Usuario();
        usuario2.setId(2L);
        usuario2.setNome("Maria");
        Endereco endereco2 = new Endereco();
        endereco2.setCep("03015000");
        usuario2.setEndereco(endereco2);
        Mockito.when(repository.findAll()).thenReturn(List.of(usuario1, usuario2));

        //Execução
        List<UsuarioDTO> usuariosDTO = service.buscarTodosOsUsuarios();

        //Validação
        Assertions.assertNotNull(usuariosDTO);
        Assertions.assertEquals(2,usuariosDTO.size());
        Assertions.assertEquals("Marcos",usuario1.getNome());
        Assertions.assertEquals("Maria",usuario2.getNome());
    }

    @Test
    void deveRetornarListaVaziaAoBuscarTodosOsUsuarios(){
        //Cenario
        Mockito.when(repository.findAll()).thenReturn(List.of());

        //Execução
        List<UsuarioDTO> listavazia = service.buscarTodosOsUsuarios();

        //Validação
        Assertions.assertNotNull(listavazia);
        Assertions.assertTrue(listavazia.isEmpty());

    }
}
