package fr.vds.expenses.bll;

import fr.vds.expenses.bo.Expense;
import fr.vds.expenses.bo.Line;
import fr.vds.expenses.bo.Participant;
import fr.vds.expenses.dal.ExpenseDAO;
import fr.vds.expenses.dal.LineDAO;
import fr.vds.expenses.dal.ParticipantDAO;
import fr.vds.expenses.dal.UserDAO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TemporaryServiceImpl implements TemporaryService {

	private UserDAO userDAO;
	private ExpenseDAO expenseDAO;
	private ParticipantDAO participantDAO;
	private LineDAO lineDAO;

	private ParticipantService participantService;

	public TemporaryServiceImpl(ParticipantService participantService, LineDAO lineDAO, UserDAO userDAO, ExpenseDAO expenseDAO, ParticipantDAO participantDAO) {
		this.lineDAO = lineDAO;
		this.userDAO = userDAO;
		this.expenseDAO = expenseDAO;
		this.participantDAO = participantDAO;
		this.participantService = participantService;
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
		List<Participant> allTheParticipantsOfTheExpense = participantService.getAllTheParticipantsOfExpense(idExpense);
		for (Participant participantOfTheExpense : allTheParticipantsOfTheExpense) {
			expense.addParticipantToList(participantOfTheExpense);
		}
		List<Line> allTheLinesOfTheExpense = lineDAO.getAllLinesFromExpense(idExpense);
		for (Line line : allTheLinesOfTheExpense) {
			expense.getLineList().add(line);
		}
		return expense;
	}

	@Override
	public void createExpense(Expense newExpense) {
		expenseDAO.createExpense(newExpense);
	}

	@Override
	public void loadBudgetExpense(int idExpense) {
		List<Participant> lstParticipants = participantService.getAllTheParticipantsOfExpense(idExpense);
		int budget = 0;
		for (Participant participant : lstParticipants) {
			budget += participant.getBudgetByMonth();
		}
		expenseDAO.updateBudgetExpense(idExpense, budget);
	}


	@Transactional
	@Override
	public void deleteExpense(int idExpense) {
		List<Participant> participants = participantService.getAllTheParticipantsOfExpense(idExpense);
		for (Participant participant : participants) {
			participantService.deleteParticipant(participant.getIdParticipant());
		}
		expenseDAO.deleteExpense(idExpense);
	}
}