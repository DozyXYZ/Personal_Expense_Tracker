package fi.haagahelia.pet.web;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import fi.haagahelia.pet.domain.Expense;
import jakarta.servlet.http.HttpServletResponse;

public class FilesExporter {

    @Autowired
    private ExpenseService expenseService;

    public void exportToCSV(HttpServletResponse response, String username) throws IOException {
        response.setContentType("text/csv");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=expenses.csv";
        response.setHeader(headerKey, headerValue);

        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);

        String[] csvHeader = { "Date", "Type", "Description", "Amount (€)" };
        String[] nameMapping = { "date", "typeExpenseType", "description", "amount" };

        csvWriter.writeHeader(csvHeader);

        List<Expense> listExpenses = expenseService.getExpensesForUser(username);

        for (Expense expense : listExpenses) {
            csvWriter.write(expense, nameMapping);
        }

        csvWriter.close();
    }

}
