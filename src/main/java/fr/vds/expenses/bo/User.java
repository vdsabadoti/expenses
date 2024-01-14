package fr.vds.expenses.bo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User {

    private int idUser;
    private String username;
    private String mail;
    private List<Expense> lstExpenses = new ArrayList<Expense>();
    private String password;

    public User(int idUser, String username, String mail, List<Expense> lstExpenses, String password) {
        this.idUser = idUser;
        this.username = username;
        this.mail = mail;
        this.lstExpenses = lstExpenses;
        this.password = password;
    }

    public User(int idUser) {
        this.idUser = idUser;
    }

    public User() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
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

    public List<Expense> getLstExpenses() {
        return lstExpenses;
    }

    public void setLstExpenses(List<Expense> lstExpenses) {
        this.lstExpenses = lstExpenses;
    }

    @Override
    public String toString() {
        return "User{" +
                "idUser=" + idUser +
                ", username='" + username + '\'' +
                '}';
    }

    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof User)) return false;
        if (!super.equals(object)) return false;
        User user = (User) object;
        return idUser == user.idUser;
    }

    public int hashCode() {
        return Objects.hash(super.hashCode(), idUser);
    }

}



