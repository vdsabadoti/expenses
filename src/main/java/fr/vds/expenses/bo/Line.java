package fr.vds.expenses.bo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Line {

    private int idLine;
    private int value;
    private LocalDate date;
    private String label;
    private User payor;
    private List<LineDetail> lineDetailList = new ArrayList<LineDetail>();
    private int debtOrRefund;

    public Line() {
    }

    public Line(int idLine) {
        this.idLine = idLine;
    }

    public Line(int idLine, int value, LocalDate date, String label, User payor, List<LineDetail> lineDetailList, int debtOrRefund) {
        this.idLine = idLine;
        this.value = value;
        this.date = date;
        this.label = label;
        this.payor = payor;
        this.lineDetailList = lineDetailList;
        this.debtOrRefund = debtOrRefund;
    }

    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Line)) return false;
        if (!super.equals(object)) return false;
        Line line = (Line) object;
        return idLine == line.idLine;
    }

    public int hashCode() {
        return Objects.hash(super.hashCode(), idLine);
    }

    public int getIdLine() {
        return idLine;
    }

    public void setIdLine(int idLine) {
        this.idLine = idLine;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
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

    public List<LineDetail> getLineDetailList() {
        return lineDetailList;
    }

    public void setLineDetailList(List<LineDetail> lineDetailList) {
        this.lineDetailList = lineDetailList;
    }
}