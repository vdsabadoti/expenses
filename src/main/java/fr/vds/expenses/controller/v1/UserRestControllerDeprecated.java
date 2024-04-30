package fr.vds.expenses.controller.v1;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.vds.expenses.adaptations.LocalDateTimeTypeAdapter;
import fr.vds.expenses.adaptations.LocalDateTypeAdapter;
import fr.vds.expenses.bll.ParticipantService;
import fr.vds.expenses.bll.TemporaryService;
import fr.vds.expenses.controller.v2.UserRestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;

// http://localhost:8080/swagger-ui/index.html#/
@CrossOrigin(origins = "http://localhost:4200")
@org.springframework.web.bind.annotation.RestController
public class UserRestControllerDeprecated {

    private TemporaryService temporaryService;
    private ParticipantService participantService;
    private UserRestController userRestController;

    UserRestControllerDeprecated(TemporaryService temporaryService, ParticipantService participantService, UserRestController userRestController){
        this.temporaryService =temporaryService;
        this.participantService = participantService;
        this.userRestController = userRestController;
    }

    @Deprecated
    @RequestMapping(method = RequestMethod.GET, path= "/getallusers")
    public String getAllUsersDepreceated(){
        Gson g = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                .create();
        return g.toJson(this.userRestController.getAllUsers().data);
    }

    @Deprecated
    @RequestMapping(method = RequestMethod.GET, path= "/getuser")
    public String getUserByIDDepreceated(
            @RequestParam(name = "id") int idUser
    ){
        Gson g = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                .create();
        return g.toJson(this.userRestController.getUserByID(idUser).data);
    }


}
