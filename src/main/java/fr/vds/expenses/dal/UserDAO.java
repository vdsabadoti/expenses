package fr.vds.expenses.dal;

import fr.vds.expenses.bo.User;

import java.util.List;

public interface UserDAO {
    void createMulan();

    User readMulanRowId();

    User readUserById(int id);

    User findUserByMail(String mail);

    List<User> readAllUsers();
}
