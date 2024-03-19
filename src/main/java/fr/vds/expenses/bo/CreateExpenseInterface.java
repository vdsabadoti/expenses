package fr.vds.expenses.bo;

import java.util.Objects;

public class CreateExpenseInterface {

    private int id;
    private Expense expense;

    public CreateExpenseInterface(){

    }
    public CreateExpenseInterface(int id, Expense expense) {
        this.id = id;
    }

    public Expense getExpense() {
        return expense;
    }

    public void setExpense(Expense expense) {
        this.expense = expense;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateExpenseInterface that = (CreateExpenseInterface) o;
        return id == that.id && Objects.equals(expense, that.expense);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, expense);
    }
}
