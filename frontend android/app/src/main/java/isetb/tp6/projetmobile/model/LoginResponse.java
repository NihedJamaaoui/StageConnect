package isetb.tp6.projetmobile.model;

import java.util.List;

public class LoginResponse {
    private List<Utilisateur> user;
    private String token;

    public List<Utilisateur> getUser() {
        return user;
    }

    public void setUser(List<Utilisateur> user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
