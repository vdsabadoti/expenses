package fr.vds.expenses.dal;

import fr.vds.expenses.bo.Group;

import java.util.List;

public interface GroupDAO {
    List<Group> getExpensesByUser(int userId);

    Group getExpensesById(int expenseId);

    void createGroup(Group group);

    void updateGroup(Group group);

    void updateBudgetExpense(int idExpense, int budget);

    void deleteExpense(int idExpense);
}
