package tech.ada.ml_users.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tech.ada.ml_users.dto.UsuarioDTO;
import tech.ada.ml_users.service.AtualizarUsuarioService;
import tech.ada.ml_users.service.BuscarUsuariosService;
import tech.ada.ml_users.service.CriarUsuarioService;
import tech.ada.ml_users.service.DeletarUsuarioService;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class UsuariosControllerTest {
    @InjectMocks
    UsuariosController controller;
    @Mock
    BuscarUsuariosService buscarUsuariosService;
    @Mock
    CriarUsuarioService criarUsuarioService;
    @Mock
    AtualizarUsuarioService atualizarUsuarioService;
    @Mock
    DeletarUsuarioService deletarUsuarioService;

    MockMvc mockMvc;

    @BeforeEach
    public void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void deveBuscarTodosOsUsuariosComSucesso() throws Exception {
        //Cenário
        UsuarioDTO usuario = new UsuarioDTO("Rodolfo", 30,"rodolfo.lima@email.com");
        UsuarioDTO usuario2 = new UsuarioDTO("Helena",5,"helena.lima@email.com");
        List<UsuarioDTO> usuariosRetornados = List.of(usuario, usuario2);
                Mockito.when(buscarUsuariosService.buscarTodosOsUsuarios()).thenReturn(usuariosRetornados);

        //Execução
        mockMvc.perform(MockMvcRequestBuilders.get("/usuarios")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.content().json(asJsonString( usuariosRetornados)))
                .andDo(MockMvcResultHandlers.print());
        //Validação

    }

    private static String asJsonString(Object object){
        ObjectMapper objectMapper = new ObjectMapper();
         try {
             return objectMapper.writeValueAsString(object);
         } catch (Exception e) {
             throw new RuntimeException("Não foi possível mapear o objeto para json");
         }
    }
}