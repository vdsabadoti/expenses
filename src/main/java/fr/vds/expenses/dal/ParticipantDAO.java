package fr.vds.expenses.dal;

import fr.vds.expenses.bo.Participant;

import java.util.List;

public interface ParticipantDAO {
    List<Participant> readParticipantsByUserId(int userId);

    List<Participant> getAllTheParticipantsOfTheExpense(int expenseId);
}
