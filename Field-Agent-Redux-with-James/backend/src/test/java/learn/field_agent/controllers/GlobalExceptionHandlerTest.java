package learn.field_agent.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import learn.field_agent.data.LocationRepository;
import learn.field_agent.models.Location;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class GlobalExceptionHandlerTest {
    @MockBean
    LocationRepository repository;

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void shouldHandleExceptions() throws Exception {

        when(repository.add(any())).thenThrow(RuntimeException.class);

        Location location = new Location();
        location.setName("Test Location");
        location.setAddress("123 Test Ave.");
        location.setCity("Test City");
        location.setRegion("TEST");
        location.setCountryCode("TS");
        location.setPostalCode("TS-5555");
        location.setAgencyId(999);

        String locationJson = objectMapper.writeValueAsString(location);

        ErrorResponse errorResponse = new ErrorResponse("Something went wrong on our end. Your request failed. :(");

        String expectedJson = objectMapper.writeValueAsString(errorResponse);

        var request = post("/api/location")
                .contentType(MediaType.APPLICATION_JSON)
                .content(locationJson);

        mvc.perform(request)
                .andExpect(status().isInternalServerError())
                .andExpect(content().json(expectedJson));
    }

    @Test
    void shouldHandleDataIntegrityExceptions() throws Exception {

        when(repository.add(any())).thenThrow(DataIntegrityViolationException.class);

        Location location = new Location();
        location.setName("Test Location");
        location.setAddress("123 Test Ave.");
        location.setCity("Test City");
        location.setRegion("TEST");
        location.setCountryCode("TS");
        location.setPostalCode("TS-5555");
        location.setAgencyId(999);

        String locationJson = objectMapper.writeValueAsString(location);

        ErrorResponse errorResponse = new ErrorResponse("Something went wrong in the database. " +
                "Please ensure that any referenced records exist. Your request failed. :(");

        String expectedJson = objectMapper.writeValueAsString(errorResponse);

        var request = post("/api/location")
                .contentType(MediaType.APPLICATION_JSON)
                .content(locationJson);

        mvc.perform(request)
                .andExpect(status().isInternalServerError())
                .andExpect(content().json(expectedJson));
    }
}