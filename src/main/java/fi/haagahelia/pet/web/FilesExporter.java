package fi.haagahelia.pet.web;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import fi.haagahelia.pet.domain.Expense;
import jakarta.servlet.http.HttpServletResponse;

/**
 * This file defines the FilesExporter class, which is used to export expenses
 * to a CSV file.
 * The class includes a method for exporting expenses to a CSV file.
 * The class uses the ExpenseService class.
 */
public class FilesExporter {

    @Autowired
    private ExpenseService expenseService;

    /**
     * Exports expenses to a CSV file.
     * 
     * @param response the HttpServletResponse object
     * @param username the username of the user whose expenses are to be exported
     * @param type     the type of expenses to be exported
     * @param year     the year of expenses to be exported
     * @param month    the month of expenses to be exported
     * @throws IOException if an I/O error occurs
     */
    public void exportToCSV(HttpServletResponse response, String username, String type, Integer year, Integer month)
            throws IOException {
        response.setContentType("text/csv");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=expenses.csv";
        response.setHeader(headerKey, headerValue);

        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);

        String[] csvHeader = { "Date", "Type", "Description", "Amount (€)" };
        String[] nameMapping = { "date", "typeExpenseType", "description", "amount" };

        csvWriter.writeHeader(csvHeader);

        List<Expense> listExpenses = expenseService.getExpensesByUserAndFilters(username, type, year, month);

        for (Expense expense : listExpenses) {
            csvWriter.write(expense, nameMapping);
        }

        csvWriter.close();
    }

}
