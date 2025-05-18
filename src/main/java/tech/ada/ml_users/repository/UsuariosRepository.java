package tech.ada.ml_users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tech.ada.ml_users.model.Usuario;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsuariosRepository extends JpaRepository<Usuario, Long> {

    // Buscar usuários ordenados por nome (ascendente)
    List<Usuario> findByOrderByNomeAsc();

    // Buscar usuários dentro de uma faixa de idade
    List<Usuario> findByIdadeBetween(int idadeInicial, int idadeFinal);

    Optional<Usuario> findByEmailIgnoreCase(String email);

    @Query(value = "SELECT u.* FROM USUARIO u JOIN ENDERECO e ON u.endereco_id = e.id WHERE e.cep = :cepEndereco",
            nativeQuery = true)
    List<Usuario> buscarUsuariosPorCep(@Param("cepEndereco") String cepEndereco);

    //JPQL
    @Query("SELECT u FROM Usuario u WHERE u.dataAtualizacao BETWEEN :inicio AND :fim")
    List<Usuario> buscarUsuariosAtualizadosNoPeriodo(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);
}
