package fr.vds.expenses.dal;

import fr.vds.expenses.bo.Expense;

import java.util.List;

public interface ExpenseDAO {
    List<Expense> getExpensesByUser(int userId);

    Expense getExpensesById(int expenseId);

    void createExpense(Expense expense);
}
