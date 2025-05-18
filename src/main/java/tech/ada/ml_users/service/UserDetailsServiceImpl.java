package tech.ada.ml_users.service;


import java.util.Collections;

//@Service
//public class UserDetailsServiceImpl implements UserDetailsService {
//
//    private final UsuariosRepository repository;
//
//    public UserDetailsServiceImpl(UsuariosRepository repository) {
//        this.repository = repository;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Usuario user = repository.findByEmailIgnoreCase(username).orElseThrow(() -> new UsernameNotFoundException(
//                "Login inválido"
//        ));
//        return new User(username, user.getSenha(), Collections.singletonList(() -> "USER"));
//    }
//}
