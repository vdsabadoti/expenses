package fr.vds.expenses.controller.v1;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.vds.expenses.adaptations.LocalDateTimeTypeAdapter;
import fr.vds.expenses.adaptations.LocalDateTypeAdapter;
import fr.vds.expenses.bll.ParticipantService;
import fr.vds.expenses.bll.TemporaryService;
import fr.vds.expenses.bo.Group;
import fr.vds.expenses.controller.v2.GroupRestController;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

// http://localhost:8080/swagger-ui/index.html#/
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class GroupRestControllerDeprecated {

    private TemporaryService temporaryService;
    private ParticipantService participantService;
    private GroupRestController groupRestController;

    GroupRestControllerDeprecated(TemporaryService temporaryService, ParticipantService participantService, GroupRestController groupRestController){
        this.temporaryService =temporaryService;
        this.participantService = participantService;
        this.groupRestController = groupRestController;
    }

    @Deprecated
    @RequestMapping(method = RequestMethod.GET, path= "/getgroups/{id}")
    public String getAllExpensesDepreceated(
           /* @RequestParam(name = "id") int idUser,*/
            @PathVariable("id") int id
    ){
        Gson g = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                .create();
        return g.toJson(this.groupRestController.getAllGroups(id).data);

    }

    @Deprecated
    @RequestMapping(method = RequestMethod.GET, path= "/getgroupbyid/{id}")
    public String getSingleExpenseDepreceated(
            @PathVariable("id") int idExpense
    ){
        Gson g = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                .create();
        return g.toJson(this.groupRestController.getGroupById(idExpense).data);
    }


    @Deprecated
    @RequestMapping(method = RequestMethod.POST, path= "/creategroup")
    public String createGroupDepreceated(
            @RequestBody Group group
    ){
        Gson g = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                .create();
        return g.toJson(this.groupRestController.createGroup(group).data);
    }

    @Deprecated
    @RequestMapping(method = RequestMethod.POST, path= "/updategroup")
    public String updateGroupDepreceated(
            @RequestBody Group group
    ){
        Gson g = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                .create();
        return g.toJson(this.groupRestController.updateGroup(group).data);
    }


    @Deprecated
    @RequestMapping(method = RequestMethod.DELETE, path= "/deletegroup/{id}")
    public String deleteGroupDepreceated(
            @PathVariable("id") int groupId
    ){
        Gson g = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                .create();
        return g.toJson(this.groupRestController.deleteGroup(groupId).data);
    }


}
