package fr.vds.expenses.bo;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

public class User implements UserDetails {

    private int id;
    private String username;
    private String mail;
    private List<Group> groupsList = new ArrayList<Group>();
    private String password;
    private String image;
    private String quote;

    private String authority;

    public User(int id, String username, String mail, List<Group> groupsList, String password, String image, String quote, String authority) {
        this.id = id;
        this.username = username;
        this.mail = mail;
        this.groupsList = groupsList;
        this.password = password;
        this.image = "image in progress";
        this.quote = "all you need is love";
        this.authority = authority;
    }

    public User(String username, String mail, String password) {
        this.username = username;
        this.mail = mail;
        this.password = password;
        this.image = "image in progress";
        this.quote = "all you need is love";
    }


    public User(int id) {
        this.id = id;
    }

    public User() {
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority){
        this.authority = authority;
    }

    public List<String> getBusinnessAuthorities(){
        String[] roles = this.authority.split(",");
        return new ArrayList<String>(Arrays.asList(roles));
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grants = new ArrayList<GrantedAuthority>();
        String[] roles = this.authority.split(",");
        for (String role : roles) {
            grants.add(new SimpleGrantedAuthority(role));
        }
        return grants;
    }
    @Override
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

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
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



