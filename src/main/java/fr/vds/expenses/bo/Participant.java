package fr.vds.expenses.bo;

import java.util.Objects;

public class Participant {

    private int idParticipant;
    private User user;
    private int balance;
    private int budgetByMonth;

    public Participant(int idParticipant) {
        this.idParticipant = idParticipant;
    }

    public Participant() {
    }

    public Participant(int idParticipant, User user, int balance, int budgetByMonth) {
        this.idParticipant = idParticipant;
        this.user = user;
        this.balance = balance;
        this.budgetByMonth = budgetByMonth;
    }

   
    
    public int getBudgetByMonth() {
		return budgetByMonth;
	}

	public void setBudgetByMonth(int budgetByMonth) {
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

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
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