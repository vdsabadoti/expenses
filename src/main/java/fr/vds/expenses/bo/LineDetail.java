package fr.vds.expenses.bo;

import java.util.Objects;

public class LineDetail {

    private int idLineDetail;
    private User user;
    private int value;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LineDetail() {
    }

    public LineDetail(int idLineDetail, int value, User user) {
        this.idLineDetail = idLineDetail;
        this.value = value;
        this.user = user;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LineDetail that = (LineDetail) o;
        return idLineDetail == that.idLineDetail && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idLineDetail, user);
    }
}