package fr.vds.expenses.bll;

import fr.vds.expenses.bo.*;
import fr.vds.expenses.dal.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TemporaryServiceImpl implements TemporaryService {

	private ExpenseDAO expenseDAO;
	private ParticipantDAO participantDAO;
	private LineDAO lineDAO;
	private RefundAndDebtDAO refundAndDebtDAO;
	private UserDAO userDAO;

	private ParticipantService participantService;

	public TemporaryServiceImpl(UserDAO userDAO, RefundAndDebtDAO refundAndDebtDAO, ParticipantService participantService, LineDAO lineDAO, ExpenseDAO expenseDAO, ParticipantDAO participantDAO) {
		this.lineDAO = lineDAO;
		this.expenseDAO = expenseDAO;
		this.participantDAO = participantDAO;
		this.participantService = participantService;
		this.refundAndDebtDAO = refundAndDebtDAO;
		this.userDAO = userDAO;
	}

	@Override
	public List<Group> getExpensesFromUser(int idUser) {
		List<Participant> participantsByUserId = participantDAO.readParticipantsByUserId(idUser);
		List<Group> expensesLst = new ArrayList<>();

		for (Participant participants : participantsByUserId) {
			//FIND EXPENSE THANKS TO USER ID (THE USER IS THE PARTICIPANT)
			int expenseId = participants.getExpense().getId();
			Group group = getSingleExpense(expenseId);
			expensesLst.add(group);
		}
		return expensesLst;
	}

	@Override
	public Expense getLineFromExpense(int idLine){
		Expense expense = lineDAO.getLineFromExpense(idLine);
		User user = this.userDAO.readUserById(expense.getPayor().getId());
		expense.setPayor(user);
		return expense;
	}
	@Override
	public Group getSingleExpense(int idExpense) {
		//GET EXPENSE FROM DB
		Group group = expenseDAO.getExpensesById(idExpense);
		//GET THE PARTICIPANTS OF THE EXPENSE FROM DB THANKS TO ID
		List<Participant> allTheParticipantsOfTheExpense = participantService.getAllTheParticipantsOfExpense(idExpense);
		//POPULATE THE PARTICIPANTS OF THE EXPENSE
		for (Participant participantOfTheExpense : allTheParticipantsOfTheExpense) {
			group.addParticipantToList(participantOfTheExpense);
		}
		//GET THE LINES OF THE EXPENSE FROM DB THANKS TO ID
		List<Expense> allTheLinesOfTheExpense = lineDAO.getAllLinesFromExpense(idExpense);
		//FOR EACH LINE, GET THE LINE DETAILS FROM DB THANKS TO ID
		for (Expense expense : allTheLinesOfTheExpense) {
			//GET THE LINEDETAILS OF THE LINE FROM DB THANKS TO ID
			List<Detail> detailsOfTheLine =  getLineDetailByLineExpenseId(expense.getId());
			//POPULATE LINE WITH LINEDETAILS
			expense.setLineDetailList(detailsOfTheLine);
			//POPULATE THE LINES OF THE EXPENSE
			group.getLineList().add(expense);
		}
		return group;
		//TODO OPTIMISATION : only one request in DB with jointures
	}

	@Override
	public void createGroup(Group newGroup) {
		expenseDAO.createExpense(newGroup);
		int id = newGroup.getId();
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

	@Override
	public List<Detail> getLineDetailByLineExpenseId(int lineExpenseId){
		List<Detail> details = this.refundAndDebtDAO.getLineDetailByLineId(lineExpenseId);
		for (Detail line : details) {
			line.setUser(this.userDAO.readUserById(line.getUser().getId()));
		}
		return details;
	}

	@Transactional
	@Override
	public void deleteExpense(int idExpense) {
		List<Participant> participants = participantService.getAllTheParticipantsOfExpense(idExpense);
		for (Participant participant : participants) {
			participantService.deleteParticipant(participant.getId());
		}
		expenseDAO.deleteExpense(idExpense);
	}
}