package tech.ada.ml_users.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import tech.ada.ml_users.model.Usuario;
import tech.ada.ml_users.repository.UsuariosRepository;

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
}
