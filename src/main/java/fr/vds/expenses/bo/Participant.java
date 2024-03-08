package fr.vds.expenses.bo;

import java.util.Objects;

public class Participant {

    private int idParticipant;
    private User user;
    private float balance;
    private float budgetByMonth;

    private Expense expense =  new Expense();

    public Participant(int idParticipant) {
        this.idParticipant = idParticipant;
    }

    public Participant() {
    }

    public Participant(Expense expense, int idParticipant, User user, float balance, float budgetByMonth) {
        this.idParticipant = idParticipant;
        this.user = user;
        this.balance = balance;
        this.budgetByMonth = budgetByMonth;
        this.expense = expense;
    }

    public Expense getExpense() {
        return expense;
    }

    public void setExpense(Expense expense) {
        this.expense = expense;
    }

    public float getBudgetByMonth() {
		return budgetByMonth;
	}

	public void setBudgetByMonth(float budgetByMonth) {
		this.budgetByMonth = budgetByMonth;
	}

	public int getIdParticipant() {
        return idParticipant;
    }

    public void setIdParticipant(int idParticipant) {
        this.idParticipant = idParticipant;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Participant)) return false;
        if (!super.equals(object)) return false;
        Participant that = (Participant) object;
        return idParticipant == that.idParticipant;
    }

    public int hashCode() {
        return Objects.hash(super.hashCode(), idParticipant);
    }
}