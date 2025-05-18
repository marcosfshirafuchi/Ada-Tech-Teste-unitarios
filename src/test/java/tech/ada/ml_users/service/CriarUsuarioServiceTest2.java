package tech.ada.ml_users.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import tech.ada.ml_users.model.Endereco;
import tech.ada.ml_users.model.Usuario;
import tech.ada.ml_users.repository.UsuariosRepository;

public class CriarUsuarioServiceTest2 {
    //TODO: Implementar os testes unitários para o CriarUsuarioService
    private UsuariosRepository repository;
    private CriarUsuarioService service;
    private EnderecoService enderecoService;

    @BeforeEach
    public void setUp() {
        repository = Mockito.mock(UsuariosRepository.class);
        enderecoService = Mockito.mock(EnderecoService.class);
        service = new CriarUsuarioService(repository,enderecoService);
    }

    @Test
    void deveCriarUsuarioComSucesso() {
        //Cenario
        Long id = 1L;
        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setNome("Amanda");
        String cep = "03015-000";
        Endereco endereco = new Endereco();
        endereco.setCep(cep);
        usuario.setEndereco(endereco);
        Mockito.when(enderecoService.obterEnderecoPeloCep(cep)).thenReturn(endereco);

        //Mockito.when(repository.save(usuario)).thenReturn(usuario);

        //Execução
        Usuario usuarioSalvo = service.criarUsuario(usuario);


        //Validação
        Assertions.assertNotNull(usuarioSalvo);
        Assertions.assertEquals(id,usuario.getId());
        Assertions.assertEquals("Amanda",usuario.getNome());
        Assertions.assertEquals(cep,usuario.getEndereco().getCep());
    }
}
