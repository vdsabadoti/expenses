package fr.vds.expenses.bo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User {

    private int id;
    private String username;
    private String mail;
    private List<Group> groupsList = new ArrayList<Group>();
    private String password;
    private String image;
    private String quote;

    public User(int id, String username, String mail, List<Group> groupsList, String password, String image, String quote) {
        this.id = id;
        this.username = username;
        this.mail = mail;
        this.groupsList = groupsList;
        this.password = password;
        this.image = "image in progress";
        this.quote = "all you need is love";
    }

    public User(int id) {
        this.id = id;
    }

    public User() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public List<Group> getLstExpenses() {
        return groupsList;
    }

    public void addGroup(Group groupsList) {
        this.groupsList.add(groupsList);
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    @Override
    public String toString() {
        return "User{" +
                "idUser=" + id +
                ", username='" + username + '\'' +
                '}';
    }

    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof User)) return false;
        if (!super.equals(object)) return false;
        User user = (User) object;
        return id == user.id;
    }

    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }

}



