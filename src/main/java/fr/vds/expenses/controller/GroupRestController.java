package fr.vds.expenses.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.vds.expenses.adaptations.LocalDateTimeTypeAdapter;
import fr.vds.expenses.adaptations.LocalDateTypeAdapter;
import fr.vds.expenses.bll.ParticipantService;
import fr.vds.expenses.bll.TemporaryService;
import fr.vds.expenses.bo.Group;
import fr.vds.expenses.bo.ResponseService;
import fr.vds.expenses.bo.User;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

// http://localhost:8080/swagger-ui/index.html#/
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class GroupRestController {

    private TemporaryService temporaryService;
    private ParticipantService participantService;

    GroupRestController(TemporaryService temporaryService, ParticipantService participantService){
        this.temporaryService =temporaryService;
        this.participantService = participantService;
    }

    @RequestMapping(method = RequestMethod.GET, path= "/v2/getgroups/{id}")
    public ResponseService<List<Group>> getAllGroups(
            /* @RequestParam(name = "id") int idUser,*/
            @PathVariable("id") int id
    ){
        ResponseService<List<Group>> responseService = new ResponseService<>();
        responseService.code = "200";
        responseService.message = "Success";
        responseService.data = temporaryService.getGroupsFromUser(id);

        return responseService;

    }

    @RequestMapping(method = RequestMethod.GET, path= "/v2/getgroupbyid/{id}")
    public ResponseService<Group> getGroupById(
            @PathVariable("id") int id
    ){
        ResponseService<Group> responseService = new ResponseService<>();
        responseService.code = "200";
        responseService.message = "Success";
        responseService.data = temporaryService.getGroupById(id);

        return responseService;
    }

    @RequestMapping(method = RequestMethod.POST, path= "/v2/creategroup")
    public ResponseService<Group> createGroup(
            @RequestBody Group group
    ){
        temporaryService.createGroup(group);
        ResponseService<Group> responseService = new ResponseService<>();
        responseService.code = "200";
        responseService.message = "Group created";
        responseService.data = temporaryService.getGroupById(group.getId());

        return responseService;
    }

    @RequestMapping(method = RequestMethod.POST, path= "/v2/updategroup")
    public ResponseService<Group> updateGroup(
            @RequestBody Group group
    ){
        temporaryService.updateGroup(group);
        ResponseService<Group> responseService = new ResponseService<>();
        responseService.code = "200";
        responseService.message = "Group updated";
        responseService.data = temporaryService.getGroupById(group.getId());

        return responseService;
    }

    @RequestMapping(method = RequestMethod.DELETE, path= "/v2/deletegroup/{id}")
    public ResponseService<Group> deleteGroup(
            @PathVariable("id") int groupId
    ){
        this.temporaryService.deleteGroup(groupId);
        ResponseService<Group> responseService = new ResponseService<>();
        responseService.code = "200";
        responseService.message = "Group deleted";
        responseService.data = null;

        return responseService;
    }


}
