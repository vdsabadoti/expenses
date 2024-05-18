package fr.vds.expenses.bo;

public class LoginInterface {

    private String username;
    private String mail;
    private String password;

    public LoginInterface(){

    }

    public LoginInterface(String username, String mail, String password){
        this.mail = mail;
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
