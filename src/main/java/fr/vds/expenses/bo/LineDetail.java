package fr.vds.expenses.bo;

import java.util.Objects;

public abstract class LineDetail {

    private int idLineDetail;
    private int value;

    public LineDetail() {
    }

    public LineDetail(int idLineDetail, int value) {
        this.idLineDetail = idLineDetail;
        this.value = value;
    }

    public int getIdLineDetail() {
        return idLineDetail;
    }

    public void setIdLineDetail(int idLineDetail) {
        this.idLineDetail = idLineDetail;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "LineDetail{" +
                "idLineDetail=" + idLineDetail +
                ", value=" + value +
                '}';
    }

    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof LineDetail)) return false;
        if (!super.equals(object)) return false;
        LineDetail that = (LineDetail) object;
        return idLineDetail == that.idLineDetail;
    }

    public int hashCode() {
        return Objects.hash(super.hashCode(), idLineDetail);
    }
}