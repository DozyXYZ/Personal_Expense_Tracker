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
public class TypeExpenseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "user1", authorities = "USER")
    public void testUserAccessTypeExpenseList() throws Exception {
        mockMvc.perform(get("/typeExpenses")).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "user1", authorities = "USER")
    public void testUserAccessTypeExpenseForm() throws Exception {
        mockMvc.perform(get("/typeExpenses/add")).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin", authorities = "ADMIN")
    public void testAdminAccessTypeExpenseList() throws Exception {
        mockMvc.perform(get("/typeExpenses")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", authorities = "ADMIN")
    public void testAdminAccessTypeExpenseForm() throws Exception {
        mockMvc.perform(get("/typeExpenses/add")).andExpect(status().isOk());
    }

}
