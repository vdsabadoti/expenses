package fr.vds.expenses.bll;

import fr.vds.expenses.bo.*;
import fr.vds.expenses.dal.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TemporaryServiceImpl implements TemporaryService {

	private GroupDAO groupDAO;
	private ParticipantDAO participantDAO;
	private ExpenseDAO expenseDAO;
	private DetailDAO detailDAO;
	private UserDAO userDAO;

	private ParticipantService participantService;

	public TemporaryServiceImpl(UserDAO userDAO, DetailDAO detailDAO, ParticipantService participantService, ExpenseDAO expenseDAO, GroupDAO groupDAO, ParticipantDAO participantDAO) {
		this.expenseDAO = expenseDAO;
		this.groupDAO = groupDAO;
		this.participantDAO = participantDAO;
		this.participantService = participantService;
		this.detailDAO = detailDAO;
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
		Expense expense = expenseDAO.getLineFromExpense(expenseId);
		User user = this.userDAO.readUserById(expense.getPayor().getId());
		expense.setPayor(user);
		return expense;
	}
	@Override
	public Group getGroupById(int groupId) {
		//GET EXPENSE FROM DB
		Group group = groupDAO.getExpensesById(groupId);
		//GET THE PARTICIPANTS OF THE EXPENSE FROM DB THANKS TO ID
		List<Participant> allTheParticipantsOfTheExpense = participantService.getAllTheParticipantsOfGroup(groupId);
		//POPULATE THE PARTICIPANTS OF THE EXPENSE
		for (Participant participantOfTheExpense : allTheParticipantsOfTheExpense) {
			group.addParticipantToList(participantOfTheExpense);
		}
		//GET THE LINES OF THE EXPENSE FROM DB THANKS TO ID
		List<Expense> allTheLinesOfTheExpense = expenseDAO.getAllLinesFromExpense(groupId);
		//FOR EACH LINE, GET THE LINE DETAILS FROM DB THANKS TO ID
		for (Expense expense : allTheLinesOfTheExpense) {
			//GET THE LINEDETAILS OF THE LINE FROM DB THANKS TO ID
			List<Detail> detailsOfTheLine =  getDetails(expense.getId());
			//POPULATE LINE WITH LINEDETAILS
			expense.setDetailList(detailsOfTheLine);
			//POPULATE THE LINES OF THE EXPENSE
			group.getLineList().add(expense);
		}
		return group;
		//TODO OPTIMISATION : only one request in DB with jointures
	}

	@Override
	public void createGroup(Group newGroup) {
		groupDAO.createGroup(newGroup);
		int id = newGroup.getId();
		for (Participant participant : newGroup.getParticipantList()) {
			participant.setExpense(newGroup);
			participantDAO.createPaticipant(participant);
		}
	}

	@Override
	public void updateGroup(Group group){
		groupDAO.updateGroup(group);
		for (Participant participant : group.getParticipantList()) {
			participant.setExpense(group);
			participantDAO.updateParticipant(participant);
		}
	}

	@Override
	public void loadBudgetGroup(int groupId) {
		List<Participant> lstParticipants = participantService.getAllTheParticipantsOfGroup(groupId);
		int budget = 0;
		for (Participant participant : lstParticipants) {
			budget += participant.getBudgetByMonth();
		}
		groupDAO.updateBudgetExpense(groupId, budget);
	}

	@Override
	public List<Detail> getDetails(int expenseId){
		List<Detail> details = this.detailDAO.getLineDetailByLineId(expenseId);
		for (Detail line : details) {
			line.setUser(this.userDAO.readUserById(line.getUser().getId()));
		}
		return details;
	}

	@Transactional
	@Override
	public void deleteGroup(int groupId) {

		//TODO only the owner is authorized to delete its own group -> throw exception

		List<Participant> participants = participantService.getAllTheParticipantsOfGroup(groupId);
		for (Participant participant : participants) {
			participantService.deleteParticipant(participant.getId());
		}
		List<Expense> expenses = expenseDAO.getAllLinesFromExpense(groupId);
		for (Expense expense : expenses){
			List<Detail> details = this.detailDAO.getLineDetailByLineId(expense.getId());
			for (Detail detail : details){
				detailDAO.deleteDetail(detail);
			}
			expenseDAO.deleteExpense(expense.getId());
		}
		groupDAO.deleteExpense(groupId);
	}

	@Transactional
	@Override
	public void createExpense(int groupId, Expense expense){
		expenseDAO.createExpense(groupId, expense);
		for (Detail detail : expense.getLineDetailList()) {
			detailDAO.createDetail(detail, groupId, expense.getId());
		}

	}

	@Transactional
	@Override
	public void updateExpense(int groupId, Expense expense){
		for (Detail detail : expense.getLineDetailList()) {
			detailDAO.updateDetail(detail, expense.getId(), groupId);
		}
		expenseDAO.updateExpense(expense, groupId);
	}

	@Transactional
	@Override
	public void deleteExpense(int expenseId){
		List<Detail> details = this.detailDAO.getLineDetailByLineId(expenseId);
		for (Detail detail : details){
			detailDAO.deleteDetail(detail);
		}
		expenseDAO.deleteExpense(expenseId);
	}


}