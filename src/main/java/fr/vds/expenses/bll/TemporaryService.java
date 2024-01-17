package fr.vds.expenses.bll;

import fr.vds.expenses.bo.Expense;
import fr.vds.expenses.bo.Participant;
import fr.vds.expenses.bo.User;
import java.util.List;

public interface TemporaryService {


    //public void createNewLine(?);

    //public void modifyLine(?);

    public User getUserFromDataBase(int idUser);

    public List<Expense> getExpensesFromUser(int idUser);
    
    public Expense getSingleExpense(int ifExpense);
    //Create object Expense with Expense table and ParticipantsOfExpense as well (for the list
    //of participants
    
    public void createExpense(Expense newExpense);
    
    public void createParticipantInExpense(Participant participant, int idExpense);

    List<User> getAllTheUsersFromDatabase();
    //create line in table ParticipantsOfExpense

}