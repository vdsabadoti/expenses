package fr.vds.expenses.bll;

import fr.vds.expenses.bo.Participant;
import fr.vds.expenses.bo.User;
import fr.vds.expenses.dal.ExpenseDAO;
import fr.vds.expenses.dal.LineDAO;
import fr.vds.expenses.dal.ParticipantDAO;
import fr.vds.expenses.dal.UserDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParticipantServiceImpl implements ParticipantService {

	private UserDAO userDAO;
	private ExpenseDAO expenseDAO;
	private ParticipantDAO participantDAO;
	private LineDAO lineDAO;

	public ParticipantServiceImpl(LineDAO lineDAO, UserDAO userDAO, ExpenseDAO expenseDAO, ParticipantDAO participantDAO) {
		this.lineDAO = lineDAO;
		this.userDAO = userDAO;
		this.expenseDAO = expenseDAO;
		this.participantDAO = participantDAO;
	}

	@Override
    public User getUserFromDataBase(int idUser) {
		return userDAO.readUserById(idUser);
    }

	@Override
	public void createParticipantInGroup(Participant participant, int idExpense) {
		if (!userIsAlreadyParticipant(idExpense, participant.getUser().getId())){
			participant.getExpense().setId(idExpense);
			participantDAO.createPaticipant(participant);
		}
	}

	@Override
	public List<User> getAllTheUsersFromDatabase(){
		return userDAO.readAllUsers();
	}


	@Override
	public List<Participant> getAllTheParticipantsOfGroup(int groupId){
		List<Participant> participants = participantDAO.getAllTheParticipantsOfTheExpense(groupId);
		for (Participant participant : participants){
			User user = getUserFromDataBase(participant.getUser().getId());
			participant.setUser(user);
		}
		return participants;
	}

	@Override
	public void deleteParticipant(int idParticipant){
		participantDAO.deleteParticipant(idParticipant);
	}

	public boolean userIsAlreadyParticipant(int idExpense, int idUser){
		return (participantDAO.countParticipantsByUserId(idUser, idExpense) != 0);
	}
}