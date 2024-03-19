package fr.vds.expenses.bo;

import java.util.Objects;

public class Participant {

    private int id;
    private User user;
    private float balance;
    private float budgetByMonth;

    private Group group;

    public Participant(int id) {
        this.id = id;
    }

    public Participant() {
    }

    public Participant(Group group, int id, User user, float balance, float budgetByMonth) {
        this.id = id;
        this.user = user;
        this.balance = balance;
        this.budgetByMonth = budgetByMonth;
        this.group = group;
    }

    public Group getExpense() {
        return group;
    }

    public void setExpense(Group group) {
        this.group = group;
    }

    public float getBudgetByMonth() {
		return budgetByMonth;
	}

	public void setBudgetByMonth(float budgetByMonth) {
		this.budgetByMonth = budgetByMonth;
	}

	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        return id == that.id;
    }

    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }
}