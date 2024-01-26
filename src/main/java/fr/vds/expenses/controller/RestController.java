package fr.vds.expenses.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.vds.expenses.adaptations.LocalDateTimeTypeAdapter;
import fr.vds.expenses.adaptations.LocalDateTypeAdapter;
import fr.vds.expenses.bll.TemporaryService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.LocalDate;
import java.time.LocalDateTime;

@CrossOrigin(origins = "http://localhost:4200")
@org.springframework.web.bind.annotation.RestController
public class RestController {

    private TemporaryService temporaryService;

    RestController(TemporaryService temporaryService){
        this.temporaryService =temporaryService;
    }

    @RequestMapping(method = RequestMethod.GET, path= "/hello")
    public String HelloWorld(){
        Gson g = new Gson();
        return g.toJson("oui");
    }

    @RequestMapping(method = RequestMethod.GET, path= "/expense")
    public String ExpenseOne(){
        Gson g = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                .create();
        return g.toJson(temporaryService.getSingleExpense(3));
    }

}
