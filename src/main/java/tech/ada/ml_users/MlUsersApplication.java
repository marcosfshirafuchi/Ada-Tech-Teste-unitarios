package tech.ada.ml_users;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tech.ada.ml_users.controller.UsuariosController;
import tech.ada.ml_users.service.BuscarUsuariosService;

@SpringBootApplication
public class MlUsersApplication {

	public static void main(String[] args) {
		SpringApplication.run(MlUsersApplication.class, args);
	}

}