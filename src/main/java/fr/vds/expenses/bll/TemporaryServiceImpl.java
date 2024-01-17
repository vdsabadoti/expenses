package fr.vds.expenses.bll;

import fr.vds.expenses.bo.Expense;
import fr.vds.expenses.bo.Line;
import fr.vds.expenses.bo.Participant;
import fr.vds.expenses.bo.User;
import fr.vds.expenses.dal.ExpenseDAO;
import fr.vds.expenses.dal.LineDAO;
import fr.vds.expenses.dal.ParticipantDAO;
import fr.vds.expenses.dal.UserDAO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TemporaryServiceImpl implements TemporaryService {

	private UserDAO userDAO;
	private ExpenseDAO expenseDAO;
	private ParticipantDAO participantDAO;
	private LineDAO lineDAO;

	public TemporaryServiceImpl(LineDAO lineDAO, UserDAO userDAO, ExpenseDAO expenseDAO, ParticipantDAO participantDAO) {
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
    public List<Expense> getExpensesFromUser(int idUser) {
		List<Participant> participantsByUserId = participantDAO.readParticipantsByUserId(idUser);
		List<Expense> expensesLst = new ArrayList<>();

		for (Participant participants : participantsByUserId) {
			//FIND EXPENSE THANKS TO USER ID (THE USER IS THE PARTICIPANT)
			int expenseId = participants.getExpense().getIdExpense();
			Expense expense = getSingleExpense(expenseId);
			expensesLst.add(expense);
		}
		return expensesLst;
    }

	@Override
	public Expense getSingleExpense(int idExpense) {
		Expense expense = expenseDAO.getExpensesById(idExpense);
		//CREATE ALL THE RELATIONSHIPS OF THE EXPENSE
		////// GET ALL THE PARTICIPANTS OF THE EXPENSE
		List<Participant> allTheParticipantsOfTheExpense = participantDAO.getAllTheParticipantsOfTheExpense(idExpense);
		for (Participant participantOfTheExpense : allTheParticipantsOfTheExpense) {
			expense.addParticipantToList(participantOfTheExpense);
		}
		////// GET ALL THE LINES OF THE EXPENSE
		List<Line> allTheLinesOfTheExpense = lineDAO.getAllLinesFromExpense(idExpense);
		for (Line line : allTheLinesOfTheExpense){
			expense.getLineList().add(line);
		}
		return expense;
	}

	@Override
	public void createExpense(Expense newExpense) {
		expenseDAO.createExpense(newExpense);
	}

	@Override
	public void createParticipantInExpense(Participant participant, int idExpense) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<User> getAllTheUsersFromDatabase(){
		return userDAO.readAllUsers();
	}
}