package fr.vds.expenses.bll;

import fr.vds.expenses.bo.Expense;
import fr.vds.expenses.bo.Participant;
import fr.vds.expenses.bo.User;
import fr.vds.expenses.dal.ExpenseDAO;
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

	public TemporaryServiceImpl(UserDAO userDAO, ExpenseDAO expenseDAO, ParticipantDAO participantDAO) {
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
			int expenseId = participants.getExpense().getIdExpense();
			Expense expense = expenseDAO.getExpensesById(expenseId);
			List<Participant> allTheParticipantsOfTheExpense = participantDAO.getAllTheParticipantsOfTheExpense(expenseId);
			for (Participant participantOfTheExpense : allTheParticipantsOfTheExpense) {
				expense.addParticipantToList(participantOfTheExpense);
			}
			expensesLst.add(expense);
		}

		return expensesLst;
    }

	@Override
	public Expense getSingleExpense(int ifExpense) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createExpense(Expense newExpense) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createParticipantInExpense(Participant participant, int idExpense) {
		// TODO Auto-generated method stub
		
	}
}