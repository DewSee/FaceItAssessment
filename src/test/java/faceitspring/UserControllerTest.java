package faceitspring;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

//    Has conflicts with updateUser(), yet to be figured out.
//    On it's own it runs, but the project doesn't build if both of them exists.
    @Test
    public void getUser() throws Exception {
        this.mockMvc.perform(get("/users/JaneJane")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is("janedoe00@gmail.com")));
    }

    @Test
    public void getUserByCountry() throws Exception {
        this.mockMvc.perform(get("/users/country/USA")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("\"email\":\"jonhrambo00@gmail.com\"")));

    }

    @Test
    public void addNewUser() throws Exception {
        String userJson = "{\"firstName\":\"Test\",\"lastName\":\"Test\",\"nickName\":\"Testy\",\"password\":\"testword\",\"email\":\"test@test.com\",\"country\":\"TestVille\"}";

        this.mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().isOk());

        this.mockMvc.perform(get("/users/Testy")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is("test@test.com")));
    }

    @Test
    public void deleteUser() throws Exception {
        this.mockMvc.perform(delete("/users/DoeDoe")).andDo(print()).andExpect(status().isOk());

        this.mockMvc.perform(get("/users/DoeDoe")).andDo(print()).andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Could not find user DoeDoe")));

    }

    //Throws exception, which is expected, have to be caught.
//    @Test
//    public void addSameUserTwice() throws Exception {
//        String userJson = "{\"firstName\":\"Test\",\"lastName\":\"Test\",\"nickName\":\"Testy\",\"password\":\"testword\",\"email\":\"test@test.com\",\"country\":\"TestVille\"}";
//
//        this.mockMvc.perform(post("/users")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(userJson))
//                .andExpect(status().isOk());
//
//        this.mockMvc.perform(post("/users")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(userJson))
//                .andExpect(status().is(500));
//
//    }
    @Test
    public void updateUser() throws Exception {
        String userJson = "{\"firstName\":\"John\",\"lastName\":\"Doe\",\"nickName\":\"DoeDoe\",\"password\":\"password\",\"email\":\"updatedoe@gmail.com\",\"country\":\"UpdateLand\"}";
        this.mockMvc.perform(put("/users/update/DoeDoe")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        this.mockMvc.perform(get("/users/DoeDoe")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is("updatedoe@gmail.com")));

    }
}
