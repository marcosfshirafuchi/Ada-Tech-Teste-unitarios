package tech.ada.ml_users.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;
import tech.ada.ml_users.repository.UsuariosRepository;

class DeletarUsuarioServiceTest {
    DeletarUsuarioService deletarUsuarioService;
    UsuariosRepository repository;
    BuscarUsuariosService buscarUsuariosService;

    @BeforeEach
    public void setUp(){
        repository = Mockito.mock(UsuariosRepository.class);
        buscarUsuariosService = Mockito.mock(BuscarUsuariosService.class);
        deletarUsuarioService = new DeletarUsuarioService(repository,buscarUsuariosService);
    }

    @DisplayName("Deve encontrar o usuário no banco de dados e excluí-lo")
    @Test
    void deveEncontrarUmUsuarioNoBancoDeDadosEExcluilo(){
        //Cenário
        Long id = 1L;

        //Execução
        deletarUsuarioService.deletarUsuarioPorId(id);

        //Validação
        //Verifica quantas vezes o método buscarUsuarioPorId foi chamado
        Mockito.verify(buscarUsuariosService, Mockito.times(1)).buscarUsuarioPorId(id);

        //Verifica quantas vezes o método deleteById foi chamado
        Mockito.verify(repository, Mockito.times(1)).deleteById(Mockito.any());

        //Verifica a ordem de como os métodos foram chamados
        InOrder inOrder = Mockito.inOrder(buscarUsuariosService, repository);
        inOrder.verify(buscarUsuariosService).buscarUsuarioPorId(id);
        inOrder.verify(repository).deleteById(id);
    }
}