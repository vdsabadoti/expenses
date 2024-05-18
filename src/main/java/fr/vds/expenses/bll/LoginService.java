package fr.vds.expenses.bll;

import fr.vds.expenses.bo.LoginInterface;

public interface LoginService {


    boolean authentificationCheck(LoginInterface loginInterface);
}