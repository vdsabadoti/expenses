package fr.vds.expenses.bll;

import fr.vds.expenses.bo.*;

import java.util.List;

public interface TemporaryService {


    //public void createNewLine(?);

    //public void modifyLine(?);
    public List<Group> getExpensesFromUser(int idUser);

    Expense getLineFromExpense(int idLine);

    public Group getSingleExpense(int ifExpense);
    //Create object Expense with Expense table and ParticipantsOfExpense as well (for the list
    //of participants
    
    public void createGroup(Group newGroup);

    void loadBudgetExpense(int idExpense);

    List<Detail> getLineDetailByLineExpenseId(int lineExpenseId);

    void deleteExpense(int idExpense);
    //create line in table ParticipantsOfExpense

}