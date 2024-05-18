package fr.vds.expenses.bll;

import fr.vds.expenses.bo.LoginInterface;
import fr.vds.expenses.dal.UserDAO;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    private UserDAO userDAO;

    public LoginServiceImpl(UserDAO userDAO){
        this.userDAO = userDAO;
    }

    @Override
    public boolean authentificationCheck(LoginInterface loginInterface){
        return !this.userDAO.readAllUsersByMail(loginInterface.getMail()).isEmpty();
    }

}