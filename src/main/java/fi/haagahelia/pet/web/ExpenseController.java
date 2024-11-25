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

/**
 * This file defines the ExpenseController class, which is a Spring MVC
 * controller class that handles HTTP requests related to expenses in the
 * application.
 * 
 * The class includes methods for displaying, adding, editing, and deleting
 * expenses, as well as exporting expenses to a CSV file, filtering expenses by
 * type, year, and month, and draw charts.
 * 
 * The class utilizes the ExpenseService, FilesExporter, TypeExpenseRepository,
 * and AppUserRepository classes.
 */

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

    /**
     * Handles requests to the root ("/") and "/expenses" URLs.
     * Retrieves the current user's username and recovery code, adds them to the
     * model, along with the user's expenses and a list of expense types.
     * 
     * @param model     the model to which attributes are added
     * @param principal the security principal of the currently authenticated user
     * @return the name of the view to be rendered ("expenses")
     */
    @GetMapping({ "/", "/expenses" })
    public String expenseList(Model model, Principal principal) {
        String username = principal.getName();

        AppUser user = userRepository.findByUsername(username);
        String recoveryCode = user.getRecoveryCode();
        model.addAttribute("username", username);
        model.addAttribute("recoveryCode", recoveryCode);

        model.addAttribute("expenses", expenseService.getExpensesForUser(username));
        List<TypeExpense> typeExpenses = (List<TypeExpense>) typeRepository.findAll();
        typeExpenses.add(0, new TypeExpense());
        model.addAttribute("typeExpenses", typeExpenses);

        return "expenses";
    }

    /**
     * Handles requests to delete an expense by its ID.
     * 
     * @param expenseId the ID of the expense to be deleted
     * @param principal the security principal of the currently authenticated user
     * @return the new "expenses" view to be redirected to after deletion
     */
    @GetMapping("/expenses/delete/{id}")
    public String deleteExpense(@PathVariable("id") Long expenseId, Principal principal) {
        String username = principal.getName();
        expenseService.deleteExpenseForUser(expenseId, username);
        return "redirect:/expenses";
    }

    /**
     * Handles requests to show the form for adding a new expense.
     * Adds a new Expense object and a list of expense types to the model.
     * 
     * @param model the model to which attributes are added
     * @return the name of the view to be rendered ("expenseform")
     */
    @GetMapping("/expenses/add")
    public String showAddForm(Model model) {
        model.addAttribute("expense", new Expense());
        model.addAttribute("typeExpenses", typeRepository.findAll());
        return "expenseform";
    }

    /**
     * Handles requests to show the form for editing an existing expense.
     * Retrieves the expense by its ID, adds it to the model, along with a list of
     * expense types.
     * 
     * @param expenseId the ID of the expense to be edited
     * @param principal the security principal of the currently authenticated user
     * @param model     the model to which attributes are added
     * @return the name of the view to be rendered ("expenseform")
     */
    @GetMapping("/expenses/edit/{id}")
    public String showEditForm(@PathVariable("id") Long expenseId, Principal principal, Model model) {
        String username = principal.getName();
        Expense expense = expenseService.getExpensesForUser(username).stream().filter(e -> e.getId().equals(expenseId))
                .findFirst().orElse(null);
        model.addAttribute("expense", expense);
        model.addAttribute("typeExpenses", typeRepository.findAll());
        return "expenseform";
    }

    /**
     * Handles requests to save a new or edited expense.
     * Saves the expense for the currently authenticated user and redirects to the
     * "expenses" view.
     * 
     * @param expense   the expense to be saved
     * @param principal the security principal of the currently authenticated user
     * @return the new "expenses" view to be redirected to after saving
     */
    @PostMapping("/expenses/save")
    public String save(Expense expense, Principal principal) {
        String username = principal.getName();
        expenseService.saveExpenseForUser(expense, username);
        return "redirect:/expenses";
    }

    /**
     * Handles requests to export expenses to a CSV file.
     * 
     * @param response  the HTTP response to which the CSV file is written
     * @param principal the security principal of the currently authenticated user
     * @param type      the type of expense to filter by
     * @param year      the year to filter by
     * @param month     the month to filter by
     * @throws IOException if an I/O error occurs
     */
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

    /**
     * Handles requests to filter expenses by type, year, and month.
     * Retrieves the current user's username, adds them to the model, along with the
     * filtered expenses.
     * 
     * @param type      the type of expense to filter by
     * @param year      the year to filter by
     * @param month     the month to filter by
     * @param principal the security principal of the currently authenticated user
     * @param model     the model to which attributes are added
     * @return the name of the view to be rendered ("expenses")
     */
    @GetMapping("/expenses/filter")
    public String filterExpenses(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month,
            Principal principal,
            Model model) {

        String username = principal.getName();

        List<Expense> expenses = expenseService.getExpensesByUserAndFilters(username, type, year, month);
        model.addAttribute("expenses", expenses);

        List<TypeExpense> typeExpenses = (List<TypeExpense>) typeRepository.findAll();
        typeExpenses.add(0, new TypeExpense());
        model.addAttribute("typeExpenses", typeExpenses);

        AppUser user = userRepository.findByUsername(username);
        String recoveryCode = user.getRecoveryCode();
        model.addAttribute("username", username);
        model.addAttribute("recoveryCode", recoveryCode);

        return "expenses";
    }

    /**
     * Handles requests to show the chart page.
     * 
     * @return the name of the view to be rendered ("expenseschart")
     */
    @GetMapping("/expenses/chart")
    public String showChartPage() {
        return "expenseschart";
    }

    /**
     * Handles requests to draw a chart of annual expenses and a chart for monthly
     * expenses in a specific year.
     * 
     * Retrieves the current user's username, adds them to the model, along with the
     * monthly expenses for the specified year.
     * 
     * @param year      the year to draw the chart for
     * @param month     the month to draw the chart for
     * @param principal the security principal of the currently authenticated user
     * @param model     the model to which attributes are added
     * @return the name of the view to be rendered ("expenseschart")
     */
    @GetMapping("/expenses/drawchart")
    public String getAnnualExpenses(
            @RequestParam Integer year,
            @RequestParam(required = false) Integer month,
            Principal principal,
            Model model) {

        String username = principal.getName();

        Map<Integer, Double> monthlyExpenses = expenseService.getMonthlyExpensesForYear(username, year);
        model.addAttribute("monthlyExpenses", monthlyExpenses);
        model.addAttribute("year", year);

        if (month != null) {
            Map<String, Double> monthlyExpenseDetails = expenseService.getMonthlyExpenseDetails(username, year, month);
            model.addAttribute("monthlyExpenseDetails", monthlyExpenseDetails);
            model.addAttribute("month", month);
        }

        return "expenseschart";
    }

}
