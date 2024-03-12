package fr.vds.expenses.bll;

import fr.vds.expenses.bo.Participant;
import fr.vds.expenses.bo.User;

import java.util.List;

public interface ParticipantService {


    //public void createNewLine(?);

    //public void modifyLine(?);

    public User getUserFromDataBase(int idUser);
    
    public void createParticipantInExpense(Participant participant, int idExpense);

    List<User> getAllTheUsersFromDatabase();

    List<Participant> getAllTheParticipantsOfExpense(int idExpense);

    void deleteParticipant(int idParticipant);
}