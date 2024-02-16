package fr.vds.expenses.bll;

import fr.vds.expenses.bo.*;

import java.util.List;

public interface TemporaryService {


    //public void createNewLine(?);

    //public void modifyLine(?);
    public List<Expense> getExpensesFromUser(int idUser);

    Line getLineFromExpense(int idLine);

    public Expense getSingleExpense(int ifExpense);
    //Create object Expense with Expense table and ParticipantsOfExpense as well (for the list
    //of participants
    
    public void createExpense(Expense newExpense);

    void loadBudgetExpense(int idExpense);

    List<LineDetail> getLineDetailByLineExpenseId(int lineExpenseId);

    void deleteExpense(int idExpense);
    //create line in table ParticipantsOfExpense

}