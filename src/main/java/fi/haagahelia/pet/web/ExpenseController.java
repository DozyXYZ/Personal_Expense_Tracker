package fi.haagahelia.pet.web;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import fi.haagahelia.pet.domain.AppUser;
import fi.haagahelia.pet.domain.AppUserRepository;
import fi.haagahelia.pet.domain.Expense;
import fi.haagahelia.pet.domain.TypeExpenseRepository;

@Controller
public class ExpenseController {

    @Autowired
    private UserCreationService creationService;

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private TypeExpenseRepository typeRepository;

    @Autowired
    private AppUserRepository userRepository;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register";
    }

    // Principal is used to get the username of the currently logged in user
    @GetMapping({ "/", "/expenses" })
    public String expenseList(Model model, Principal principal) {
        String username = principal.getName();
        AppUser user = userRepository.findByUsername(username);
        String recoveryCode = user.getRecoveryCode();
        model.addAttribute("username", username);
        model.addAttribute("recoveryCode", recoveryCode);
        model.addAttribute("expenses", expenseService.getExpensesForUser(username));
        return "expenses";
    }

    @GetMapping("/delete/{id}")
    public String deleteExpense(@PathVariable("id") Long expenseId, Principal principal) {
        String username = principal.getName();
        expenseService.deleteExpenseForUser(expenseId, username);
        return "redirect:/expenses";
    }

    @GetMapping("/addExpense")
    public String showAddForm(Model model) {
        model.addAttribute("expense", new Expense());
        model.addAttribute("typeExpenses", typeRepository.findAll());
        return "expenseform";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long expenseId, Principal principal, Model model) {
        String username = principal.getName();
        Expense expense = expenseService.getExpensesForUser(username).stream().filter(e -> e.getId().equals(expenseId))
                .findFirst().orElse(null);
        model.addAttribute("expense", expense);
        model.addAttribute("typeExpenses", typeRepository.findAll());
        return "expenseform";
    }

    @PostMapping("/save")
    public String save(Expense expense, Principal principal) {
        String username = principal.getName();
        expenseService.saveExpenseForUser(expense, username);
        return "redirect:/expenses";
    }

    @PostMapping("/createUser")
    public String createUser(@RequestParam String username, @RequestParam String password, @RequestParam String email) {
        creationService.createUser(username, password, email);
        return "redirect:/login";
    }

}
