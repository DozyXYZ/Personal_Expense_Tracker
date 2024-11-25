package fi.haagahelia.pet.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import fi.haagahelia.pet.domain.TypeExpense;

/**
 * This file defines the TypeExpenseController class, which is a Spring MVC
 * controller class that handles HTTP requests related to expense types in the
 * application.
 * 
 * The class includes methods for displaying, adding, and editing expense types
 * for ADMIN role only.
 * 
 * The class utilizes the TypeExpenseService class.
 */
@Controller
public class TypeExpenseController {

    @Autowired
    private TypeExpenseService typeExpenseService;

    /**
     * Handles requests to the "/typeExpenses" URL.
     * Retrieves all expense types and adds them to the model.
     * 
     * @param model the model to which attributes are added
     * @return the name of the view to be rendered ("typeexpenses")
     */
    @GetMapping("/typeExpenses")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String getTypeExpenses(Model model) {
        model.addAttribute("typeExpenses", typeExpenseService.getAllTypeExpenses());
        return "typeexpenses";
    }

    /**
     * Handles requests to the "/typeExpenses/add" URL.
     * Adds a new TypeExpense object to the model.
     * 
     * @param model the model to which attributes are added
     * @return the name of the view to be rendered ("typeexpensesform")
     */
    @GetMapping("/typeExpenses/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String showTypeExpenseForm(Model model) {
        model.addAttribute("typeExpense", new TypeExpense());
        return "typeexpensesform";
    }

    /**
     * Handles requests to the "/typeExpenses/edit/{id}" URL.
     * Retrieves the TypeExpense object with the specified ID and adds it to the
     * model.
     * 
     * @param typeExpenseId the ID of the TypeExpense object to be retrieved
     * @param model         the model to which attributes are added
     * @return the name of the view to be rendered ("typeexpensesform")
     */
    @GetMapping("/typeExpenses/edit/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String showTypeExpenseEditForm(@PathVariable("id") Long typeExpenseId, Model model) {
        TypeExpense type = typeExpenseService.getAllTypeExpenses().stream().filter(e -> e.getId().equals(typeExpenseId))
                .findFirst().orElse(null);
        model.addAttribute("typeExpense", type);
        return "typeexpensesform";
    }

    /**
     * Handles POST requests to the "/typeExpenses/save" endpoint.
     * It saves the provided TypeExpense object using the typeExpenseService and
     * then redirects to the "/typeExpenses" endpoint.
     * 
     * @param typeExpense the TypeExpense object to be saved
     * @return a redirect string to the "/typeExpenses" endpoint
     */
    @PostMapping("/typeExpenses/save")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String saveTypeExpense(TypeExpense typeExpense) {
        typeExpenseService.saveTypeExpense(typeExpense);
        return "redirect:/typeExpenses";
    }
}
