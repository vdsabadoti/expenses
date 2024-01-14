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

	public TemporaryServiceImpl(UserDAO userDAO, ExpenseDAO expenseDAO) {
		this.userDAO = userDAO;
		this.expenseDAO = expenseDAO;
	}

	@Override
    public User getUserFromDataBase(int idUser) {
		return userDAO.readUserById(idUser);
    }

    @Override
    public List<Expense> getExpensesFromUser(int idUser) {
		List<Expense> expensesLst = expenseDAO.getExpensesByUser(idUser);
		for (Expense expense:
			expensesLst ) {
			//CREATE METHOD INTO PARTICIPANTDAO TO GET ALL THE PARTICIPANTS
			//WHERE EXPENSE ID EQUALS ROWID OF EACH EXPENSE OF THE USER (expensesLst)
			List<Participant> participantsOfExpense = new ArrayList<Participant>();
			for (Participant participant: participantsOfExpense
				 ) {
				expense.addParticipantToList(participant);
			}
		}
		return expenseDAO.getExpensesByUser(3);
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