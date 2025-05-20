package tech.ada.ml_users.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import tech.ada.ml_users.model.Endereco;
import tech.ada.ml_users.model.Usuario;
import tech.ada.ml_users.repository.UsuariosRepository;

class CriarUsuarioServiceTest {

    CriarUsuarioService service;
    UsuariosRepository repository;
    EnderecoService enderecoService;


    @BeforeEach
    public void setUp(){
        repository = Mockito.mock(UsuariosRepository.class);
        enderecoService = Mockito.mock(EnderecoService.class);
        service = new CriarUsuarioService(repository, enderecoService);
    }

    @DisplayName("Dado um usuário válido, deve criar esse usuário com sucesso")
    @Test
    void testeCriarUsuario(){
        //Cenario
        Usuario usuarioValido = umUsuario();

        Usuario usuarioSalvoNoBancoDeDados = umUsuario();
        Mockito.when(repository.save(usuarioValido)).thenReturn(usuarioSalvoNoBancoDeDados);

        //Execução
        Usuario usuarioCriado = service.criarUsuario(usuarioValido);

        //Validação
        Assertions.assertNotNull(usuarioCriado);
        Assertions.assertEquals(usuarioCriado, usuarioCriado);
        Assertions.assertNotNull(usuarioValido.getDataCriacao());
        Assertions.assertEquals("Rodolfo", usuarioCriado.getNome());
        Assertions.assertEquals("rodolfo@gmail.com",usuarioCriado.getEmail());
        Assertions.assertEquals("123456", usuarioCriado.getSenha());
        Assertions.assertEquals("71901128", usuarioCriado.getEndereco().getCep());
        Mockito.verify(repository, Mockito.times(1)).save(usuarioValido);
    }
    private Usuario umUsuario(){
        Usuario usuario = new Usuario();
        usuario.setNome("Rodolfo");
        usuario.setEmail("rodolfo@gmail.com");
        usuario.setSenha("123456");
        Endereco endereco = new Endereco();
        endereco.setCep("71901128");
        usuario.setEndereco(endereco);
        return usuario;
    }
}