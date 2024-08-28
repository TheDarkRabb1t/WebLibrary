package tdr.pet.weblibrary.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tdr.pet.weblibrary.model.entity.Publisher;
import tdr.pet.weblibrary.repository.PublisherRepository;
import tdr.pet.weblibrary.service.impl.PublisherServiceImpl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TestPublisherService {

    @Mock
    private PublisherRepository publisherRepository;

    @InjectMocks
    private PublisherServiceImpl publisherService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getPublisherByName() {
        String name = "Penguin Books";
        Publisher publisher = new Publisher();
        publisher.setName(name);
        when(publisherRepository.getPublisherByName(name)).thenReturn(Optional.of(publisher));

        Publisher result = publisherService.getPublisherByName(name);

        assertNotNull(result);
        assertEquals(name, result.getName());
        verify(publisherRepository, times(1)).getPublisherByName(name);
    }

    @Test
    void getPublishersByAddress() {
        String address = "123 st";
        Publisher publisher = new Publisher();
        publisher.setAddress(address);
        when(publisherRepository.getPublishersByAddress(address)).thenReturn(Collections.singletonList(publisher));

        List<Publisher> publishers = publisherService.getPublishersByAddress(address);

        assertNotNull(publishers);
        assertEquals(1, publishers.size());
        assertEquals(address, publishers.get(0).getAddress());
        verify(publisherRepository, times(1)).getPublishersByAddress(address);
    }

    @Test
    void exists() {
        String name = "Penguin Books";
        when(publisherRepository.existsByName(name)).thenReturn(true);

        boolean exists = publisherService.exists(name);

        assertTrue(exists);
        verify(publisherRepository, times(1)).existsByName(name);
    }

    @Test
    void createNewPublisher() {
        Publisher publisher = new Publisher();
        publisher.setName("Penguin Books");
        publisher.setAddress("123 st");

        publisherService.createNewPublisher(publisher);

        verify(publisherRepository, times(1)).save(publisher);
    }

    @Test
    void updatePublisherById() {
        Long id = 1L;
        Publisher publisher = new Publisher();
        publisher.setName("Penguin Books");
        publisher.setAddress("123 st");

        publisherService.updatePublisherById(id, publisher);

        verify(publisherRepository, times(1)).updatePublisherById(id, publisher);
    }

    @Test
    void updatePublisherByName() {
        String name = "Penguin Books";
        Publisher publisher = new Publisher();
        publisher.setAddress("123 st");

        publisherService.updatePublisherByName(name, publisher);

        verify(publisherRepository, times(1)).updatePublisherByName(name, publisher);
    }

    @Test
    void deletePublisherById() {
        Long id = 1L;

        publisherService.deletePublisherById(id);

        verify(publisherRepository, times(1)).deleteById(id);
    }
}
