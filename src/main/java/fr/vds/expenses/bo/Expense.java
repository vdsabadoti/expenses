package fr.vds.expenses.bo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Expense {

    private int idExpense;
    private int balance;
    private int budgetByMonth;
    private String expenseName;
    private String description;
    private User owner;
    private List<Participant> participantList = new ArrayList<Participant>();
    private List<Line> lineList = new ArrayList<Line>();

    public Expense(int budgetByMonth, int idExpense, int balance, String expenseName, String description, User owner, List<Participant> participantList, List<Line> lineList) {
        this.idExpense = idExpense;
        this.balance = balance;
        this.expenseName = expenseName;
        this.description = description;
        this.owner = owner;
        this.participantList = participantList;
        this.lineList = lineList;
        this.budgetByMonth = budgetByMonth;
    }

    public Expense(int idExpense) {
        this.idExpense = idExpense;
    }

    public Expense() {
    }


    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Expense)) return false;
        if (!super.equals(object)) return false;
        Expense expense = (Expense) object;
        return idExpense == expense.idExpense;
    }

    public int hashCode() {
        return Objects.hash(super.hashCode(), idExpense);
    }

    public int getIdExpense() {
        return idExpense;
    }

    public void setIdExpense(int idExpense) {
        this.idExpense = idExpense;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getExpenseName() {
        return expenseName;
    }

    public void setExpenseName(String expenseName) {
        this.expenseName = expenseName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<Participant> getParticipantList() {
        return participantList;
    }

    public void setParticipantList(List<Participant> participantList) {
        this.participantList = participantList;
    }

    public void addParticipantToList(Participant participant) {
        this.participantList.add(participant);
    }

    public List<Line> getLineList() {
        return lineList;
    }

    public void setLineList(List<Line> lineList) {
        this.lineList = lineList;
    }

    public int getBudgetByMonth() {
        return budgetByMonth;
    }

    public void setBudgetByMonth(int budgetByMonth) {
        this.budgetByMonth = budgetByMonth;
    }
}