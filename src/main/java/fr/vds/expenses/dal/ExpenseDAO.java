package fr.vds.expenses.dal;

import fr.vds.expenses.bo.Expense;

import java.util.List;

public interface ExpenseDAO {
    List<Expense> getExpensesByUser(int userId);

    List<Expense> getExpensesByRowId(int rowId);
}
