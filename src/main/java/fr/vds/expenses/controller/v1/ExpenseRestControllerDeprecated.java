package fr.vds.expenses.controller.v1;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.vds.expenses.adaptations.LocalDateTimeTypeAdapter;
import fr.vds.expenses.adaptations.LocalDateTypeAdapter;
import fr.vds.expenses.bll.ParticipantService;
import fr.vds.expenses.bll.TemporaryService;
import fr.vds.expenses.bo.*;
import fr.vds.expenses.controller.v2.ExpenseRestController;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

// http://localhost:8080/swagger-ui/index.html#/
@CrossOrigin(origins = "http://localhost:4200")
@org.springframework.web.bind.annotation.RestController
public class ExpenseRestControllerDeprecated {

    private TemporaryService temporaryService;
    private ParticipantService participantService;
    private ExpenseRestController expenseRestController;

    ExpenseRestControllerDeprecated(TemporaryService temporaryService, ParticipantService participantService, ExpenseRestController expenseRestController){
        this.temporaryService =temporaryService;
        this.participantService = participantService;
        this.expenseRestController = expenseRestController;
    }

    @Deprecated
    @RequestMapping(method = RequestMethod.GET, path= "/getdetails/{id}")
    public String getLineDetail(
            @PathVariable("id") int idLine
    ){
        Gson g = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                .create();
        return g.toJson(this.expenseRestController.getDetails(idLine).data);
    }

    @Deprecated
    @RequestMapping(method = RequestMethod.GET, path= "/getexpensebyid/{id}")
    public String getLine(
            @PathVariable("id") int idLine
    ){
        Gson g = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                .create();
        return g.toJson(this.expenseRestController.getExpense(idLine).data);
    }

    @Deprecated
    @RequestMapping(method = RequestMethod.GET, path = "/getparticipants")
    public String getParticipantsOfGroup(
            @RequestParam(name = "groupid") int idGroup
    ) {
        Gson g = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                .create();
        return g.toJson(this.expenseRestController.getParticipants(idGroup).data);
    }

    @Deprecated
    @RequestMapping(method = RequestMethod.POST, path= "/createexpense")
    public String createExpense(
            @RequestBody CreateExpenseInterface createExpenseInterface
    ){

        Gson g = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                .create();
        return g.toJson(this.expenseRestController.newExpense(createExpenseInterface).data);
    }

    @Deprecated
    @RequestMapping(method = RequestMethod.POST, path= "/updateexpense")
    public String updateExpense(
            @RequestBody CreateExpenseInterface createExpenseInterface
    ){
        Gson g = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                .create();
        return g.toJson(this.expenseRestController.updtExpense(createExpenseInterface).data);
    }

    @Deprecated
    @RequestMapping(method = RequestMethod.DELETE, path= "/deleteexpense/{id}")
    public String deleteExpense(
            @PathVariable("id") int expenseId
    ){
        Gson g = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                .create();
        return g.toJson(this.expenseRestController.delExpense(expenseId).data);
    }

}
