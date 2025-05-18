package tech.ada.ml_users.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import tech.ada.ml_users.dto.UsuarioDTO;
import tech.ada.ml_users.exception.UsuarioNaoEncontradoException;
import tech.ada.ml_users.model.Endereco;
import tech.ada.ml_users.model.Usuario;
import tech.ada.ml_users.repository.UsuariosRepository;


import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class BuscarUsuariosServiceTest {

    BuscarUsuariosService service;
    UsuariosRepository repository;

    @BeforeEach
    public void setUp() {
        repository = Mockito.mock(UsuariosRepository.class);
        service = new BuscarUsuariosService(repository);
    }

    @DisplayName("Deve retornar um usuário com sucesso quando buscar por ID")
    @Test
    void deveRetornarUsuarioBuscadoPorIdComSucesso() {
        //Cenário
        Long id = 2L;

        Usuario usuarioQueEstaSalvoNoBancoDeDados = new Usuario();
        usuarioQueEstaSalvoNoBancoDeDados.setId(id);
        usuarioQueEstaSalvoNoBancoDeDados.setNome("Rodolfo");

        Mockito.when(repository.findById(id)).thenReturn(Optional.of(usuarioQueEstaSalvoNoBancoDeDados));

        //Execução
        Usuario usuarioRetornado = service.buscarUsuarioPorId(id);

        //Validação
        assertNotNull(usuarioRetornado);
        assertEquals(id, usuarioRetornado.getId());
        assertEquals("Rodolfo", usuarioRetornado.getNome());
    }

    @DisplayName("Deve lançar uma exceção quando um usuário não foi encontrado")
    @Test
    void deveLancarExcecaoQuandoUsuarioNaoEncontrado() {
        //Cenário
        Long id = 1L;
        Mockito.when(repository.findById(id)).thenReturn(Optional.empty());

        //Execução
        UsuarioNaoEncontradoException exception = assertThrows(
                UsuarioNaoEncontradoException.class, () -> service.buscarUsuarioPorId(id));


        //Verificação
        assertNotNull(exception);
        assertEquals("Usuário com ID " + id + " não encontrado", exception.getMessage());

    }

    @DisplayName("Deve buscar todos os usuários com sucesso")
    @Test
    void deveBuscarTodosOsUsuariosComSucesso() {
        //Cenário
        Usuario usuario1 = new Usuario();
        usuario1.setNome("Rodolfo");
        usuario1.setId(1L);
        Endereco endereco = new Endereco();
        endereco.setCep("7100000");
        usuario1.setEndereco(endereco);

        Usuario usuario2 = new Usuario();
        usuario2.setNome("Helena");
        usuario2.setId(2L);
        Endereco endereco2 = new Endereco();
        endereco2.setCep("800000");
        usuario2.setEndereco(endereco2);

        Mockito.when(repository.findAll()).thenReturn(List.of(usuario1, usuario2));

        //Execução
        List<UsuarioDTO> usuariosDTO = service.buscarTodosOsUsuarios();


        //Validação
        assertNotNull(usuariosDTO);
        assertEquals(2, usuariosDTO.size());
        assertEquals("Rodolfo", usuariosDTO.get(0).getNome());
        assertEquals("Helena", usuariosDTO.get(1).getNome());
    }

    @DisplayName("Deve retornar uma lista vazia")
    @Test
    void deveRetornarListaVazioAoBuscarTodosOsUsuarios() {
        //Cenário
        Mockito.when(repository.findAll()).thenReturn(Collections.emptyList());

        //Execução
        List<UsuarioDTO> usuariosRetornados = service.buscarTodosOsUsuarios();

        //Validação
        assertNotNull(usuariosRetornados);
        assertTrue(usuariosRetornados.isEmpty());
    }




}