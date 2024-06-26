package fr.vds.expenses.bll;

import fr.vds.expenses.bo.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TemporaryService {


    //public void createNewLine(?);

    //public void modifyLine(?);
    public List<Group> getGroupsFromUser(int userId);

    Expense getExpenseById(int expenseId);

    public Group getGroupById(int groupId);
    //Create object Expense with Expense table and ParticipantsOfExpense as well (for the list
    //of participants
    
    public void createGroup(Group newGroup);

    void updateGroup(Group group);

    void loadBudgetGroup(int groupId);

    List<Detail> getDetails(int expenseId);

    void deleteGroup(int groupId);
    //create line in table ParticipantsOfExpense

    void createExpense(int groupId, Expense expense);

    @Transactional
    void updateExpense(int groupId, Expense expense);

    @Transactional
    void deleteExpense(int expenseId);
}