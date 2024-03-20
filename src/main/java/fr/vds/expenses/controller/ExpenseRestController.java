package fr.vds.expenses.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.vds.expenses.adaptations.LocalDateTimeTypeAdapter;
import fr.vds.expenses.adaptations.LocalDateTypeAdapter;
import fr.vds.expenses.bll.ParticipantService;
import fr.vds.expenses.bll.TemporaryService;
import fr.vds.expenses.bo.Expense;
import fr.vds.expenses.bo.Group;
import fr.vds.expenses.bo.CreateExpenseInterface;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@CrossOrigin(origins = "http://localhost:4200")
@org.springframework.web.bind.annotation.RestController
public class ExpenseRestController {

    private TemporaryService temporaryService;
    private ParticipantService participantService;

    ExpenseRestController(TemporaryService temporaryService, ParticipantService participantService){
        this.temporaryService =temporaryService;
        this.participantService = participantService;
    }

    @RequestMapping(method = RequestMethod.GET, path= "/getexpenses")
    public String getAllExpenses(
            @RequestParam(name = "id") int idUser
    ){
        Gson g = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                .create();
        return g.toJson(temporaryService.getGroupsFromUser(idUser));

    }

    @RequestMapping(method = RequestMethod.GET, path= "/getsingleexpense")
    public String getSingleExpense(
            @RequestParam(name = "id") int idExpense
    ){
        Gson g = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                .create();
        return g.toJson(temporaryService.getGroupById(idExpense));
    }

    @RequestMapping(method = RequestMethod.GET, path= "/getlinedetail")
    public String getLineDetail(
            @RequestParam(name = "id") int idLine
    ){
        Gson g = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                .create();
        return g.toJson(temporaryService.getDetails(idLine));
    }

    @RequestMapping(method = RequestMethod.GET, path= "/getline")
    public String getLine(
            @RequestParam(name = "id") int idLine
    ){
        Gson g = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                .create();
        return g.toJson(temporaryService.getExpenseById(idLine));
    }

    @RequestMapping(method = RequestMethod.POST, path= "/creategroup")
    public String createGroup(
            @RequestBody Group group
    ){
        temporaryService.createGroup(group);
        Gson g = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                .create();
        return g.toJson("OK");
    }
 // http://localhost:8080/swagger-ui/index.html#/

    @RequestMapping(method = RequestMethod.GET, path = "/getparticipants")
    public String getParticipantsOfGroup(
            @RequestParam(name = "groupid") int idGroup
    ) {
        Gson g = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                .create();
        return g.toJson(participantService.getAllTheParticipantsOfGroup(idGroup));
    }

    @RequestMapping(method = RequestMethod.POST, path= "/createexpense")
    public String createExpense(
            @RequestBody CreateExpenseInterface createExpenseInterface
    ){
        this.temporaryService.createExpense(createExpenseInterface.getId(), createExpenseInterface.getExpense());
        Gson g = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                .create();
        return g.toJson("OK");
    }

    @RequestMapping(method = RequestMethod.POST, path= "/updateexpense")
    public String updateExpense(
            @RequestBody CreateExpenseInterface createExpenseInterface
    ){
        this.temporaryService.updateExpense(createExpenseInterface.getId(), createExpenseInterface.getExpense());
        Gson g = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                .create();
        return g.toJson("OK");
    }
}
