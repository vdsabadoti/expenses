package fr.vds.expenses.dal;

import fr.vds.expenses.bo.Expense;

import java.util.List;

public interface LineDAO {
    List<Expense> getAllLinesFromExpense(int idExpense);

    Expense getLineFromExpense(int idLine);

    void createExpense(int groupId, Expense expense);
}
