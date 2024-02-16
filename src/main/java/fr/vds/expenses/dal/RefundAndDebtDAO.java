package fr.vds.expenses.dal;

import fr.vds.expenses.bo.LineDetail;

import java.util.List;

public interface RefundAndDebtDAO {

    List<LineDetail> getLineDetailByLineId(int idExpenseLine);
}
