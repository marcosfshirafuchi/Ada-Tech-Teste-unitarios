package tech.ada.ml_users.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.ada.ml_users.model.Endereco; // Você precisará desta classe
import tech.ada.ml_users.model.Usuario;  // Você precisará desta classe
import tech.ada.ml_users.repository.UsuariosRepository;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Habilita a integração do Mockito com JUnit 5
class CriarUsuarioServiceTest {

    @Mock // Cria um mock para UsuariosRepository
    private UsuariosRepository usuariosRepositoryMock;

    @Mock // Cria um mock para EnderecoService
    private EnderecoService enderecoServiceMock;

    @InjectMocks // Cria uma instância de CriarUsuarioService e injeta os mocks acima
    private CriarUsuarioService criarUsuarioService;

    @Captor // Cria um ArgumentCaptor para capturar argumentos passados aos mocks
    private ArgumentCaptor<Usuario> usuarioArgumentCaptor;

    private Usuario usuarioEntrada;
    private Endereco enderecoEntrada;
    private Endereco enderecoCompletoMock;
    private final String CEP_ENTRADA = "12345-678";

    @BeforeEach
    void setUp() {
        // Configuração inicial para cada teste

        // Endereço que o usuário de entrada possui (apenas com CEP)
        enderecoEntrada = new Endereco();
        enderecoEntrada.setCep(CEP_ENTRADA);

        // Usuário que será passado para o método criarUsuario
        usuarioEntrada = new Usuario();
        usuarioEntrada.setNome("Nome Teste");
        usuarioEntrada.setEmail("teste@example.com");
        // ... outros atributos do usuário que não são modificados diretamente pelo método
        usuarioEntrada.setEndereco(enderecoEntrada);

        // Endereço completo que esperamos que o EnderecoService retorne
        enderecoCompletoMock = new Endereco();
        enderecoCompletoMock.setCep(CEP_ENTRADA);
        enderecoCompletoMock.setLogradouro("Rua Testada");
       // enderecoCompletoMock.setNumero("100");
        enderecoCompletoMock.setBairro("Bairro Mock");
      //  enderecoCompletoMock.setCidade("Cidade Exemplo");
       // enderecoCompletoMock.setEstado("TE");
    }

    @Test
    @DisplayName("Deve criar usuário com sucesso, buscando endereço e definindo data de criação")
    void deveCriarUsuarioComSucesso() {
        // Arrange (Organizar)
        // 1. Configurar o comportamento do mock EnderecoService
        when(enderecoServiceMock.obterEnderecoPeloCep(CEP_ENTRADA)).thenReturn(enderecoCompletoMock);

        // 2. Configurar o comportamento do mock UsuariosRepository
        //    Fazemos o mock de 'save' retornar o usuário que lhe foi passado.
        //    Isso simula o comportamento comum de um repositório que retorna a entidade persistida.
        when(usuariosRepositoryMock.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act (Agir)
        LocalDateTime antesDaChamada = LocalDateTime.now();
        Usuario usuarioCriado = criarUsuarioService.criarUsuario(usuarioEntrada);
        LocalDateTime depoisDaChamada = LocalDateTime.now();

        // Assert (Verificar)
        // 1. Verificar se o EnderecoService foi chamado com o CEP correto
        verify(enderecoServiceMock, times(1)).obterEnderecoPeloCep(CEP_ENTRADA);

        // 2. Verificar se o UsuariosRepository.save foi chamado
        //    Usamos o ArgumentCaptor para pegar o objeto Usuario que foi passado para o save
        verify(usuariosRepositoryMock, times(1)).save(usuarioArgumentCaptor.capture());
        Usuario usuarioSalvo = usuarioArgumentCaptor.getValue();

        // 3. Verificar as propriedades do usuário retornado pelo método
        assertNotNull(usuarioCriado, "O usuário criado não deve ser nulo.");
        assertEquals(enderecoCompletoMock, usuarioCriado.getEndereco(), "O endereço do usuário criado deve ser o retornado pelo EnderecoService.");
        assertNotNull(usuarioCriado.getDataCriacao(), "A data de criação do usuário não deve ser nula.");

        // 4. Verificar se a data de criação foi definida para "agora"
        //    Esta é uma forma de verificar sem precisar mockar LocalDateTime.now() estaticamente ou injetar um Clock.
        //    Assume-se que a execução do método é rápida.
        assertTrue(
                (usuarioCriado.getDataCriacao().isEqual(antesDaChamada) || usuarioCriado.getDataCriacao().isAfter(antesDaChamada)) &&
                        (usuarioCriado.getDataCriacao().isEqual(depoisDaChamada) || usuarioCriado.getDataCriacao().isBefore(depoisDaChamada)),
                "A data de criação deve ser o momento atual (entre antes e depois da chamada)."
        );

        // 5. Verificar as propriedades do usuário que foi passado para o método save
        assertNotNull(usuarioSalvo, "O usuário salvo (capturado) não deve ser nulo.");
        assertEquals(enderecoCompletoMock, usuarioSalvo.getEndereco(), "O endereço do usuário salvo deve ser o retornado pelo EnderecoService.");
        assertNotNull(usuarioSalvo.getDataCriacao(), "A data de criação do usuário salvo não deve ser nula.");
        assertEquals(usuarioEntrada.getNome(), usuarioSalvo.getNome(), "O nome do usuário salvo deve ser o mesmo do usuário de entrada.");
        assertEquals(usuarioEntrada.getEmail(), usuarioSalvo.getEmail(), "O email do usuário salvo deve ser o mesmo do usuário de entrada.");

        // 6. Verificar se o objeto retornado é o mesmo que foi salvo (já que nosso mock do save retorna o argumento)
        assertSame(usuarioSalvo, usuarioCriado, "O usuário retornado deve ser o mesmo objeto que foi passado para o save.");

        // 7. O objeto 'usuarioEntrada' original também foi modificado, pois é passado por referência.
        assertEquals(enderecoCompletoMock, usuarioEntrada.getEndereco(), "O endereço do objeto original 'usuarioEntrada' deve ter sido atualizado.");
        assertNotNull(usuarioEntrada.getDataCriacao(), "A data de criação do objeto original 'usuarioEntrada' deve ter sido atualizada.");
    }

    @Test
    @DisplayName("Deve propagar exceção se EnderecoService.obterEnderecoPeloCep falhar")
    void devePropagarExcecaoSeEnderecoServiceFalhar() {
        // Arrange
        String mensagemErro = "Falha ao buscar CEP";
        when(enderecoServiceMock.obterEnderecoPeloCep(CEP_ENTRADA)).thenThrow(new RuntimeException(mensagemErro));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            criarUsuarioService.criarUsuario(usuarioEntrada);
        }, "Deveria ter lançado RuntimeException.");

        assertEquals(mensagemErro, exception.getMessage(), "A mensagem da exceção não é a esperada.");

        // Verificar que o método save do repositório não foi chamado
        verify(usuariosRepositoryMock, never()).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Deve propagar exceção se UsuariosRepository.save falhar")
    void devePropagarExcecaoSeRepositorioSaveFalhar() {
        // Arrange
        String mensagemErro = "Falha ao salvar no banco de dados";
        when(enderecoServiceMock.obterEnderecoPeloCep(CEP_ENTRADA)).thenReturn(enderecoCompletoMock);
        when(usuariosRepositoryMock.save(any(Usuario.class))).thenThrow(new RuntimeException(mensagemErro));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            criarUsuarioService.criarUsuario(usuarioEntrada);
        }, "Deveria ter lançado RuntimeException.");

        assertEquals(mensagemErro, exception.getMessage(), "A mensagem da exceção não é a esperada.");

        // Verificar que o EnderecoService foi chamado
        verify(enderecoServiceMock, times(1)).obterEnderecoPeloCep(CEP_ENTRADA);
        // Verificar que o save foi tentado
        verify(usuariosRepositoryMock, times(1)).save(usuarioArgumentCaptor.capture());

        // Opcional: verificar o estado do usuário que seria salvo
        Usuario usuarioTentativaSave = usuarioArgumentCaptor.getValue();
        assertEquals(enderecoCompletoMock, usuarioTentativaSave.getEndereco());
        assertNotNull(usuarioTentativaSave.getDataCriacao());
    }
}