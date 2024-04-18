package fr.vds.expenses.dal;

import fr.vds.expenses.bo.Detail;

import java.util.List;

public interface DetailDAO {

    List<Detail> getLineDetailByLineId(int idExpenseLine);

    void createDetail(Detail detail, int groupId, int expenseId);

    void deleteDetail(Detail detail);

    void updateDetail(Detail detail, int expenseId, int groupId);
}
