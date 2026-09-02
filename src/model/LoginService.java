package model;

import java.util.ArrayList;
import java.util.List;

public class LoginService {

    private List<Usuario> usuarios;

    public LoginService() {
        this.usuarios = new ArrayList<>();

        usuarios.add(new Usuario("admin", "123"));
        usuarios.add(new Usuario("joao", "456"));
        usuarios.add(new Usuario("maria", "789"));
    }

    public Usuario autenticar(String login, String senha) {
        for (Usuario u : usuarios) {
            if (u.getLogin().equals(login) && u.getSenha().equals(senha)) {
                return u;
            }
        }
        return null;
    }
}
