package fr.vds.expenses.bo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Group {

    private int id;
    private float balance;
    private float budgetByMonth;
    private String name;
    private String description;
    private User owner;
    private List<Participant> participantList = new ArrayList<Participant>();
    private List<Expense> expensesList = new ArrayList<Expense>();

    public Group(int id, float budgetByMonth, float balance, String name, String description, User owner, List<Participant> participantList, List<Expense> expensesList) {
        this.id = id;
        this.balance = balance;
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.participantList = participantList;
        this.expensesList = expensesList;
        this.budgetByMonth = budgetByMonth;
    }

    public Group(int id) {
        this.id = id;
    }

    public Group() {
    }


    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Group)) return false;
        if (!super.equals(object)) return false;
        Group group = (Group) object;
        return id == group.id;
    }

    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public List<Expense> getLineList() {
        return expensesList;
    }

    public void setLineList(List<Expense> expenseList) {
        this.expensesList = expenseList;
    }

    public float getBudgetByMonth() {
        return budgetByMonth;
    }

    public void setBudgetByMonth(float budgetByMonth) {
        this.budgetByMonth = budgetByMonth;
    }
}