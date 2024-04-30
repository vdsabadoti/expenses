package fr.vds.expenses.bo;

import java.util.Objects;

public class CreateExpenseInterface {

    private int grouipId;
    private Expense expense;

    public CreateExpenseInterface(){

    }
    public CreateExpenseInterface(int grouipId, Expense expense) {
        this.grouipId = grouipId;
    }

    public Expense getExpense() {
        return expense;
    }

    public void setExpense(Expense expense) {
        this.expense = expense;
    }

    public int getGrouipId() {
        return grouipId;
    }

    public void setGrouipId(int grouipId) {
        this.grouipId = grouipId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateExpenseInterface that = (CreateExpenseInterface) o;
        return grouipId == that.grouipId && Objects.equals(expense, that.expense);
    }

    @Override
    public int hashCode() {
        return Objects.hash(grouipId, expense);
    }
}
