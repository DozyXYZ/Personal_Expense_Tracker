package fi.haagahelia.pet.web;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.PathVariable;

import fi.haagahelia.pet.domain.AppUser;
import fi.haagahelia.pet.domain.AppUserRepository;
import fi.haagahelia.pet.domain.Expense;
import fi.haagahelia.pet.domain.TypeExpense;
import fi.haagahelia.pet.domain.TypeExpenseRepository;
import jakarta.servlet.http.HttpServletResponse;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

@Controller
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    FilesExporter exporter;

    @Autowired
    private TypeExpenseRepository typeRepository;

    @Autowired
    private AppUserRepository userRepository;

    // private static final Logger logger =
    // LoggerFactory.getLogger(ExpenseController.class);

    // Principal is used to get the username of the currently logged in user
    @GetMapping({ "/", "/expenses" })
    public String expenseList(Model model, Principal principal) {
        String username = principal.getName();

        AppUser user = userRepository.findByUsername(username);
        String recoveryCode = user.getRecoveryCode();
        model.addAttribute("username", username);
        model.addAttribute("recoveryCode", recoveryCode);

        model.addAttribute("expenses", expenseService.getExpensesForUser(username));
        List<TypeExpense> typeExpenses = (List<TypeExpense>) typeRepository.findAll();
        typeExpenses.add(0, new TypeExpense()); // Add an empty TypeExpense at the beginning of the list
        model.addAttribute("typeExpenses", typeExpenses);

        return "expenses";
    }

    @GetMapping("/expenses/delete/{id}")
    public String deleteExpense(@PathVariable("id") Long expenseId, Principal principal) {
        String username = principal.getName();
        expenseService.deleteExpenseForUser(expenseId, username);
        return "redirect:/expenses";
    }

    @GetMapping("/expenses/add")
    public String showAddForm(Model model) {
        model.addAttribute("expense", new Expense());
        model.addAttribute("typeExpenses", typeRepository.findAll());
        return "expenseform";
    }

    @GetMapping("/expenses/edit/{id}")
    public String showEditForm(@PathVariable("id") Long expenseId, Principal principal, Model model) {
        String username = principal.getName();
        Expense expense = expenseService.getExpensesForUser(username).stream().filter(e -> e.getId().equals(expenseId))
                .findFirst().orElse(null);
        model.addAttribute("expense", expense);
        model.addAttribute("typeExpenses", typeRepository.findAll());
        return "expenseform";
    }

    @PostMapping("/expenses/save")
    public String save(Expense expense, Principal principal) {
        String username = principal.getName();
        expenseService.saveExpenseForUser(expense, username);
        return "redirect:/expenses";
    }

    @GetMapping("/expenses/export")
    public void expensesToCSV(
            HttpServletResponse response,
            Principal principal,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month) throws IOException {
        String username = principal.getName();
        exporter.exportToCSV(response, username, type, year, month);
    }

    @GetMapping("/expenses/filter")
    public String filterExpenses(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month,
            Principal principal,
            Model model) {

        String username = principal.getName();

        // System.out.println("Filter parameters - Type: " + type + ", Year: " + year +
        // ", Month: " + month);

        List<Expense> expenses = expenseService.getExpensesByUserAndFilters(username, type, year, month);
        model.addAttribute("expenses", expenses);

        List<TypeExpense> typeExpenses = (List<TypeExpense>) typeRepository.findAll();
        typeExpenses.add(0, new TypeExpense()); // Add an empty TypeExpense at the beginning of the list
        model.addAttribute("typeExpenses", typeExpenses);

        AppUser user = userRepository.findByUsername(username);
        String recoveryCode = user.getRecoveryCode();
        model.addAttribute("username", username);
        model.addAttribute("recoveryCode", recoveryCode);

        return "expenses";
    }

    @GetMapping("/expenses/chart")
    public String showChartPage() {
        return "expenseschart";
    }

    @GetMapping("/expenses/drawchart")
    public String getAnnualExpenses(
            @RequestParam Integer year,
            @RequestParam(required = false) Integer month,
            Principal principal,
            Model model) {

        String username = principal.getName();

        Map<Integer, Double> monthlyExpenses = expenseService.getMonthlyExpensesForYear(username, year);
        // logger.info("Monthly Expenses: {}", monthlyExpenses);
        model.addAttribute("monthlyExpenses", monthlyExpenses);
        model.addAttribute("year", year);

        if (month != null) {
            Map<String, Double> monthlyExpenseDetails = expenseService.getMonthlyExpenseDetails(username, year, month);
            // logger.info("Monthly Expense Details: {}", monthlyExpenseDetails);
            model.addAttribute("monthlyExpenseDetails", monthlyExpenseDetails);
            model.addAttribute("month", month);
        }

        return "expenseschart";
    }

}
