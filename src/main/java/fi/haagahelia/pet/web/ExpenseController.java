package fi.haagahelia.pet.web;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fi.haagahelia.pet.domain.Expense;
import fi.haagahelia.pet.domain.TypeExpenseRepository;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private TypeExpenseRepository typeRepository;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // Principal is used to get the username of the currently logged in user
    @GetMapping({ "/", "/expenses" })
    public String expenseList(Model model, Principal principal) {
        String username = principal.getName();
        model.addAttribute("username", username);
        model.addAttribute("expenses", expenseService.getExpensesForUser(username));
        return "expenses";
    }

    @GetMapping("/addExpense")
    public String showForm(Model model) {
        model.addAttribute("expense", new Expense());
        model.addAttribute("typeExpenses", typeRepository.findAll());
        return "addExpense";
    }

    @PostMapping("/save")
    public String save(Expense expense, Principal principal) {
        String username = principal.getName();
        expenseService.addExpenseForUser(expense, username);
        return "redirect:/expenses";
    }

}
