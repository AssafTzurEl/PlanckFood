package assaf.Planck;

import com.assaft.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.startsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = PlanckFoodApplication.class)
@AutoConfigureMockMvc
public class PlanckFoodApiTests {

    @Test
    public void givenNoFoods_whenGetAll_shouldReturnEmptyList() throws Exception {

        mockMvc.perform(get(FoodController.BASE_PATH))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    public void whenAddFood_shouldReturnCreatedAndFoodObject() throws Exception {

        mockMvc.perform(post(FoodController.BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(FoodTestObjects.MAIN_COURSE_1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value(FoodTestObjects.MAIN_COURSE_1.getName()))
                .andExpect(jsonPath("$.type").value(FoodTestObjects.MAIN_COURSE_1.getType().toString()))
                .andExpect(jsonPath("$.rating").value(5L))
                .andExpect(header().string("location", startsWith(FoodController.BASE_PATH + "/")));
    }

    private static String toJson(Object obj) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(obj);
    }

    @Autowired
    private MockMvc mockMvc;
}
