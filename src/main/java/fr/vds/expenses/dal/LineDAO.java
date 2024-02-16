package fr.vds.expenses.dal;

import fr.vds.expenses.bo.Line;

import java.util.List;

public interface LineDAO {
    List<Line> getAllLinesFromExpense(int idExpense);

    Line getLineFromExpense(int idLine);
}
