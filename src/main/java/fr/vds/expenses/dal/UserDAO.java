package fr.vds.expenses.dal;

import fr.vds.expenses.bo.User;

public interface UserDAO {
    void createMulan();

    User readMulanRowId();

    User readUserById(int id);
}
