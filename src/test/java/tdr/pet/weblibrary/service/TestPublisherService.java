package tdr.pet.weblibrary.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tdr.pet.weblibrary.model.entity.Publisher;
import tdr.pet.weblibrary.repository.PublisherRepository;
import tdr.pet.weblibrary.service.impl.PublisherServiceImpl;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TestPublisherService {

    @Mock
    private PublisherRepository publisherRepository;

    @InjectMocks
    private PublisherServiceImpl publisherServiceImpl;

    private Publisher publisher;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        publisher = new Publisher();
        publisher.setId(1L);
        publisher.setName("Sample Publisher");
        publisher.setAddress("123 Main St");
    }

    @Test
    void testFindPublishersByName() {
        when(publisherRepository.findPublishersByName("Sample Publisher")).thenReturn(Set.of(publisher));

        Set<Publisher> publishers = publisherServiceImpl.findPublishersByName("Sample Publisher");

        assertNotNull(publishers);
        assertEquals(1, publishers.size());
        assertTrue(publishers.contains(publisher));
        verify(publisherRepository, times(1)).findPublishersByName("Sample Publisher");
    }

    @Test
    void testFindPublishersByAddress() {
        when(publisherRepository.getPublishersByAddress("123 Main St")).thenReturn(List.of(publisher));

        List<Publisher> publishers = publisherServiceImpl.findPublishersByAddress("123 Main St");

        assertNotNull(publishers);
        assertEquals(1, publishers.size());
        assertEquals(publisher.getAddress(), publishers.get(0).getAddress());
        verify(publisherRepository, times(1)).getPublishersByAddress("123 Main St");
    }

    @Test
    void testExists() {
        when(publisherRepository.existsByName("Sample Publisher")).thenReturn(true);

        boolean exists = publisherServiceImpl.exists("Sample Publisher");

        assertTrue(exists);
        verify(publisherRepository, times(1)).existsByName("Sample Publisher");
    }

    @Test
    void testCreateNewPublisher() {
        when(publisherRepository.save(any(Publisher.class))).thenReturn(publisher);

        publisherServiceImpl.createNewPublisher(publisher);

        verify(publisherRepository, times(1)).save(publisher);
    }

    @Test
    void testUpdatePublisherById() {
        when(publisherRepository.existsById(1L)).thenReturn(true);
        doNothing().when(publisherRepository).updatePublisherById(1L, publisher);


        publisherServiceImpl.updatePublisherById(1L, publisher);

        verify(publisherRepository, times(1)).updatePublisherById(1L, publisher);
    }

    @Test
    void testUpdatePublisherByName() {
        when(publisherRepository.existsByName("Sample Publisher")).thenReturn(true);
        doNothing().when(publisherRepository).updatePublisherByName("Sample Publisher", publisher);

        publisherServiceImpl.updatePublisherByName("Sample Publisher", publisher);

        verify(publisherRepository, times(1)).updatePublisherByName("Sample Publisher", publisher);
    }

    @Test
    void testDeletePublisherById() {
        when(publisherRepository.existsById(1L)).thenReturn(true);
        doNothing().when(publisherRepository).deleteById(1L);

        publisherServiceImpl.deletePublisherById(1L);

        verify(publisherRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeletePublisherByName() {
        when(publisherRepository.existsByName("Sample Publisher")).thenReturn(true);
        doNothing().when(publisherRepository).deletePublisherByName("Sample Publisher");

        publisherServiceImpl.deletePublisherByName("Sample Publisher");

        verify(publisherRepository, times(1)).deletePublisherByName("Sample Publisher");
    }
}
