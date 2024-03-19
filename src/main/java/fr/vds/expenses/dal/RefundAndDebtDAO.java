package fr.vds.expenses.dal;

import fr.vds.expenses.bo.Detail;
import fr.vds.expenses.bo.Group;

import java.util.List;

public interface RefundAndDebtDAO {

    List<Detail> getLineDetailByLineId(int idExpenseLine);

    void createDetail(Detail detail, int groupId, int expenseId);
}
