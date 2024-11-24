package fi.haagahelia.pet;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.security.test.context.support.WithMockUser;

@SpringBootTest
@AutoConfigureMockMvc
public class ExpenseControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "user1", authorities = "USER")
    public void testExpenseList() throws Exception {
        mockMvc.perform(get("/expenses")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user1", authorities = "USER")
    public void testShowAddForm() throws Exception {
        mockMvc.perform(get("/expenses/add")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user1", authorities = "USER")
    public void testShowEditForm() throws Exception {
        mockMvc.perform(get("/expenses/edit/1")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user1", authorities = "USER")
    public void testShowDeleteForm() throws Exception {
        mockMvc.perform(get("/expenses/delete/1")).andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(username = "user1", authorities = "USER")
    public void testExportExpenses() throws Exception {
        mockMvc.perform(get("/expenses/export")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user1", authorities = "USER")
    public void testFilterExpenses() throws Exception {
        mockMvc.perform(get("/expenses/filter")
                .param("type", "Housing")
                .param("year", "2023")
                .param("month", "10"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user1", authorities = "USER")
    public void testShowChartPage() throws Exception {
        mockMvc.perform(get("/expenses/chart")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user1", authorities = "USER")
    public void testGetAnnualExpenses() throws Exception {
        mockMvc.perform(get("/expenses/drawchart")
                .param("year", "2023"))
                .andExpect(status().isOk());
    }

}
