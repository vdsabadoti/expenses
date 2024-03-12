package fr.vds.expenses.dal;

import fr.vds.expenses.bo.Detail;

import java.util.List;

public interface RefundAndDebtDAO {

    List<Detail> getLineDetailByLineId(int idExpenseLine);
}
