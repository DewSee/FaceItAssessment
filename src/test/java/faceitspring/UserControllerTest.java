package faceitspring;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getUser() throws Exception {
        this.mockMvc.perform(get("/users/DoeDoe")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("\"email\" : \"johndoe00@gmail.com\"")));
    }

    @Test
    public void getUserByCountry() throws Exception {
        this.mockMvc.perform(get("/users/country/USA")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("\"email\":\"jonhrambo00@gmail.com\"")));
    }

    @Test
    public void addNewUser() throws Exception {
        User user = new User("Test", "Test", "Testy", "testword", "test@test.com", "TestVille");
        this.mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .param("firstName", "Test")
                .param("lastName", "Test")
                .param("nickName", "Testy")
                .param("password", "testword")
                .param("email", "test@test.com")
                .param("country", "TestVille"))
                .andExpect(status().isOk());
    }
}
