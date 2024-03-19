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
	public List<Group> getGroupsFromUser(int userId) {
		List<Participant> participantsByUserId = participantDAO.readParticipantsByUserId(userId);
		List<Group> expensesLst = new ArrayList<>();

		for (Participant participants : participantsByUserId) {
			//FIND EXPENSE THANKS TO USER ID (THE USER IS THE PARTICIPANT)
			int expenseId = participants.getExpense().getId();
			Group group = getGroupById(expenseId);
			expensesLst.add(group);
		}
		return expensesLst;
	}

	@Override
	public Expense getExpenseById(int expenseId){
		Expense expense = lineDAO.getLineFromExpense(expenseId);
		User user = this.userDAO.readUserById(expense.getPayor().getId());
		expense.setPayor(user);
		return expense;
	}
	@Override
	public Group getGroupById(int groupId) {
		//GET EXPENSE FROM DB
		Group group = expenseDAO.getExpensesById(groupId);
		//GET THE PARTICIPANTS OF THE EXPENSE FROM DB THANKS TO ID
		List<Participant> allTheParticipantsOfTheExpense = participantService.getAllTheParticipantsOfGroup(groupId);
		//POPULATE THE PARTICIPANTS OF THE EXPENSE
		for (Participant participantOfTheExpense : allTheParticipantsOfTheExpense) {
			group.addParticipantToList(participantOfTheExpense);
		}
		//GET THE LINES OF THE EXPENSE FROM DB THANKS TO ID
		List<Expense> allTheLinesOfTheExpense = lineDAO.getAllLinesFromExpense(groupId);
		//FOR EACH LINE, GET THE LINE DETAILS FROM DB THANKS TO ID
		for (Expense expense : allTheLinesOfTheExpense) {
			//GET THE LINEDETAILS OF THE LINE FROM DB THANKS TO ID
			List<Detail> detailsOfTheLine =  getDetails(expense.getId());
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
		for (Participant participant : newGroup.getParticipantList()) {
			participant.setExpense(newGroup);
			participantDAO.createPaticipant(participant);
		}
	}

	@Override
	public void loadBudgetGroup(int groupId) {
		List<Participant> lstParticipants = participantService.getAllTheParticipantsOfGroup(groupId);
		int budget = 0;
		for (Participant participant : lstParticipants) {
			budget += participant.getBudgetByMonth();
		}
		expenseDAO.updateBudgetExpense(groupId, budget);
	}

	@Override
	public List<Detail> getDetails(int expenseId){
		List<Detail> details = this.refundAndDebtDAO.getLineDetailByLineId(expenseId);
		for (Detail line : details) {
			line.setUser(this.userDAO.readUserById(line.getUser().getId()));
		}
		return details;
	}

	@Transactional
	@Override
	public void deleteGroup(int groupId) {
		List<Participant> participants = participantService.getAllTheParticipantsOfGroup(groupId);
		for (Participant participant : participants) {
			participantService.deleteParticipant(participant.getId());
		}
		expenseDAO.deleteExpense(groupId);
	}
}