package fr.vds.expenses.controller.v2;

import fr.vds.expenses.bll.ParticipantService;
import fr.vds.expenses.bll.TemporaryService;
import fr.vds.expenses.bo.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// http://localhost:8080/swagger-ui/index.html#/
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class ExpenseRestController {

    private TemporaryService temporaryService;
    private ParticipantService participantService;

    ExpenseRestController(TemporaryService temporaryService, ParticipantService participantService){
        this.temporaryService =temporaryService;
        this.participantService = participantService;
    }

    //---------------------------------------------------------------------------------------------------- V2 : ResponseService + JSON auto

    @RequestMapping(method = RequestMethod.GET, path= "/v2/getdetails/{id}")
    public ResponseService<List<Detail>> getDetails(
            @PathVariable("id") int id
    ){
        ResponseService<List<Detail>> responseService = new ResponseService<>();
        responseService.code = "200";
        responseService.message = "Success";
        responseService.data = temporaryService.getDetails(id);

        return responseService;
    }

    @RequestMapping(method = RequestMethod.GET, path= "/v2/getexpensebyid/{id}")
    public ResponseService<Expense> getExpense(
            @PathVariable("id") int id
    ){
        ResponseService<Expense> responseService = new ResponseService<>();
        responseService.code = "200";
        responseService.message = "Success";
        responseService.data = temporaryService.getExpenseById(id);

        return responseService;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/v2/getparticipants")
    public ResponseService<List<Participant>> getParticipants(
            @RequestParam(name = "groupid") int id
    ) {
        ResponseService<List<Participant>> responseService = new ResponseService<>();
        responseService.code = "200";
        responseService.message = "Success";
        responseService.data = participantService.getAllTheParticipantsOfGroup(id);

        return responseService;

    }

    @RequestMapping(method = RequestMethod.POST, path= "/v2/createexpense")
    public ResponseService<Expense> newExpense(
            @RequestBody CreateExpenseInterface createExpenseInterface
    ){
        this.temporaryService.createExpense(createExpenseInterface.getId(), createExpenseInterface.getExpense());
        ResponseService<Expense> responseService = new ResponseService<>();
        responseService.code = "200";
        responseService.message = "Success";
        responseService.data = createExpenseInterface.getExpense();

        return responseService;
    }

    @RequestMapping(method = RequestMethod.POST, path= "/v2/updateexpense")
    public ResponseService<Expense> updtExpense(
            @RequestBody CreateExpenseInterface createExpenseInterface
    ){
        this.temporaryService.updateExpense(createExpenseInterface.getId(), createExpenseInterface.getExpense());
        ResponseService<Expense> responseService = new ResponseService<>();
        responseService.code = "200";
        responseService.message = "Success";
        responseService.data = createExpenseInterface.getExpense();

        return responseService;
    }

    @RequestMapping(method = RequestMethod.DELETE, path= "/v2/deleteexpense/{id}")
    public ResponseService<Expense> delExpense(
            @PathVariable("id") int expenseId
    ){
        this.temporaryService.deleteExpense(expenseId);
        ResponseService<Expense> responseService = new ResponseService<>();
        responseService.code = "200";
        responseService.message = "Success";
        responseService.data = null;

        return responseService;
    }

}
