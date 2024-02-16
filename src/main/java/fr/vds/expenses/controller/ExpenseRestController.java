package fr.vds.expenses.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.vds.expenses.adaptations.LocalDateTimeTypeAdapter;
import fr.vds.expenses.adaptations.LocalDateTypeAdapter;
import fr.vds.expenses.bll.ParticipantService;
import fr.vds.expenses.bll.TemporaryService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
        return g.toJson(temporaryService.getExpensesFromUser(idUser));

    }

    @RequestMapping(method = RequestMethod.GET, path= "/getsingleexpense")
    public String getSingleExpense(
            @RequestParam(name = "id") int idExpense
    ){
        Gson g = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                .create();
        return g.toJson(temporaryService.getSingleExpense(idExpense));
    }

    @RequestMapping(method = RequestMethod.GET, path= "/getlinedetail")
    public String getLineDetail(
            @RequestParam(name = "id") int idLine
    ){
        Gson g = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                .create();
        return g.toJson(temporaryService.getLineDetailByLineExpenseId(idLine));
    }

    @RequestMapping(method = RequestMethod.GET, path= "/getline")
    public String getLine(
            @RequestParam(name = "id") int idLine
    ){
        Gson g = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                .create();
        return g.toJson(temporaryService.getLineFromExpense(idLine));
    }

}
