package fr.vds.expenses.controller.v2;

import fr.vds.expenses.bll.TemporaryService;
import org.springframework.stereotype.Controller;

@Controller
public class TemporaryController {

    private TemporaryService temporaryService;

    public TemporaryController(TemporaryService temporaryService) {
        this.temporaryService = temporaryService;
    }

    public String viewExpenseList(){

        return "view-expene-list";
    }

}