package fr.vds.expenses.bo;

public class UserTokenInterface {

    private int idUser;
    private String token;

    UserTokenInterface(){}

    public UserTokenInterface(int idUser, String token){
        this.idUser = idUser;
        this.token = token;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
