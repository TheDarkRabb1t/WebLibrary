package tdr.pet.weblibrary.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tdr.pet.weblibrary.model.dto.PublisherDTO;
import tdr.pet.weblibrary.model.entity.Publisher;
import tdr.pet.weblibrary.model.mapper.PublisherMapper;
import tdr.pet.weblibrary.service.PublisherService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class TestRestPublisherController {

    @Mock
    private PublisherService publisherService;

    @Mock
    private PublisherMapper publisherMapper;

    @InjectMocks
    private RestPublisherController restPublisherController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(restPublisherController).build();
    }

    @Test
    void testFindPublishersByName() throws Exception {
        PublisherDTO publisherDTO = new PublisherDTO("Publisher Name", "Publisher Address");
        Publisher publisher = new Publisher();

        Set<Publisher> publishers = new HashSet<>(List.of(publisher));
        Set<PublisherDTO> publisherDTOs = new HashSet<>(List.of(publisherDTO));

        when(publisherService.findPublishersByName(anyString())).thenReturn(publishers);
        when(publisherMapper.toDTO(any(Publisher.class))).thenReturn(publisherDTO);

        mockMvc.perform(get("/api/v1/publisher/name/{name}", "Publisher Name"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0].name").value("Publisher Name"))
                .andExpect(jsonPath("$[0].address").value("Publisher Address"));
    }

    @Test
    void testFindPublishersByAddress() throws Exception {
        PublisherDTO publisherDTO = new PublisherDTO("Publisher Name", "Publisher Address");
        Publisher publisher = new Publisher();

        List<Publisher> publishers = List.of(publisher);
        List<PublisherDTO> publisherDTOs = List.of(publisherDTO);

        when(publisherService.findPublishersByAddress(anyString())).thenReturn(publishers);
        when(publisherMapper.toDTO(any(Publisher.class))).thenReturn(publisherDTO);

        mockMvc.perform(get("/api/v1/publisher/address/{address}", "Publisher Address"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0].name").value("Publisher Name"))
                .andExpect(jsonPath("$[0].address").value("Publisher Address"));
    }

    @Test
    void testCreateNewPublisher() throws Exception {
        PublisherDTO publisherDTO = new PublisherDTO("Publisher Name", "Publisher Address");
        Publisher publisher = new Publisher();

        when(publisherMapper.toEntity(any(PublisherDTO.class))).thenReturn(publisher);
        doNothing().when(publisherService).createNewPublisher(any(Publisher.class));

        mockMvc.perform(post("/api/v1/publisher")
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(publisherDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    void testUpdatePublisherById() throws Exception {
        PublisherDTO publisherDTO = new PublisherDTO("Publisher Name", "Publisher Address");
        Publisher publisher = new Publisher();

        when(publisherMapper.toEntity(any(PublisherDTO.class))).thenReturn(publisher);
        doNothing().when(publisherService).updatePublisherById(anyLong(), any(Publisher.class));

        mockMvc.perform(put("/api/v1/publisher/{id}", 1L)
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(publisherDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdatePublisherByName() throws Exception {
        PublisherDTO publisherDTO = new PublisherDTO("Publisher Name", "Publisher Address");
        Publisher publisher = new Publisher();

        when(publisherMapper.toEntity(any(PublisherDTO.class))).thenReturn(publisher);
        doNothing().when(publisherService).updatePublisherByName(anyString(), any(Publisher.class));

        mockMvc.perform(put("/api/v1/publisher/name/{name}", "Publisher Name")
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(publisherDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void testDeletePublisherById() throws Exception {
        doNothing().when(publisherService).deletePublisherById(anyLong());

        mockMvc.perform(delete("/api/v1/publisher/{id}", 1L))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeletePublisherByName() throws Exception {
        doNothing().when(publisherService).deletePublisherByName(anyString());

        mockMvc.perform(delete("/api/v1/publisher/name/{name}", "Publisher Name"))
                .andExpect(status().isNoContent());
    }
}
