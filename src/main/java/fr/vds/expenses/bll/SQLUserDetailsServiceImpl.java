package fr.vds.expenses.bll;

import fr.vds.expenses.dal.UserDAO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SQLUserDetailsServiceImpl implements UserDetailsService {

    private final UserDAO userDAO;

    SQLUserDetailsServiceImpl(UserDAO userDAO){
        this.userDAO = userDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userDAO.findUserByMail(username).get();
    }
}
