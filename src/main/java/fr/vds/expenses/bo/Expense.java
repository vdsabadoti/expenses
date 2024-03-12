package fr.vds.expenses.bo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Expense {

    private int id;
    private float value;
    private LocalDate date;
    private String label;
    private User payor;
    private List<Detail> detailList = new ArrayList<Detail>();
    private int debtOrRefund;

    public Expense() {
    }

    public Expense(int id) {
        this.id = id;
    }

    public Expense(int id, float value, LocalDate date, String label, User payor, List<Detail> detailList, int debtOrRefund) {
        this.id = id;
        this.value = value;
        this.date = date;
        this.label = label;
        this.payor = payor;
        this.detailList = detailList;
        this.debtOrRefund = debtOrRefund;
    }

    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Expense)) return false;
        if (!super.equals(object)) return false;
        Expense expense = (Expense) object;
        return id == expense.id;
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

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getDebtOrRefund() {
        return debtOrRefund;
    }

    public void setDebtOrRefund(int debtOrRefund) {
        this.debtOrRefund = debtOrRefund;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public User getPayor() {
        return payor;
    }

    public void setPayor(User payor) {
        this.payor = payor;
    }

    public List<Detail> getLineDetailList() {
        return detailList;
    }

    public void setLineDetailList(List<Detail> detailList) {
        this.detailList = detailList;
    }
}