package fr.vds.expenses.dal;

import fr.vds.expenses.bo.User;

import java.util.List;
import java.util.Optional;

public interface UserDAO {
    void createMulan();

    User readMulanRowId();

    User readUserById(int id);

    Optional<User> findUserByMail(String mail);

    List<User> readAllUsersByMail(String mail);

    List<User> readAllUsers();
}
