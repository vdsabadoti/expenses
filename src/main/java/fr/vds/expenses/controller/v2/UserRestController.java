package fr.vds.expenses.controller.v2;

import fr.vds.expenses.bll.ParticipantService;
import fr.vds.expenses.bll.TemporaryService;
import fr.vds.expenses.bo.ResponseService;
import fr.vds.expenses.bo.User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

// http://localhost:8080/swagger-ui/index.html#/
@CrossOrigin(origins = "http://localhost:4200")
@org.springframework.web.bind.annotation.RestController
public class UserRestController {

    private TemporaryService temporaryService;
    private ParticipantService participantService;

    UserRestController(TemporaryService temporaryService, ParticipantService participantService){
        this.temporaryService =temporaryService;
        this.participantService = participantService;
    }

    @RequestMapping(method = RequestMethod.GET, path= "/v2/getuser")
    public ResponseService<User> getUserByID(
            @RequestParam(name = "id") int idUser
    ){
        ResponseService<User> responseService = new ResponseService<>();
        responseService.code = "200";
        responseService.message = "Success";
        responseService.data = participantService.getUserFromDataBase(idUser);

        return responseService;
    }

    @RequestMapping(method = RequestMethod.GET, path= "/v2/getallusers")
    public ResponseService<List<User>> getAllUsers(){
        ResponseService<List<User>> responseService = new ResponseService<>();
        responseService.code = "200";
        responseService.message = "Success";
        responseService.data = participantService.getAllTheUsersFromDatabase();
        return responseService;
    }


}
