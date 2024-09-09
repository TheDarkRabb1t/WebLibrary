package tdr.pet.weblibrary.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tdr.pet.weblibrary.exception.publisher.MultiplePublishersFoundException;
import tdr.pet.weblibrary.exception.publisher.PublisherNotFoundException;
import tdr.pet.weblibrary.model.dto.PublisherDTO;
import tdr.pet.weblibrary.model.entity.Publisher;
import tdr.pet.weblibrary.model.mapper.PublisherMapper;
import tdr.pet.weblibrary.repository.PublisherRepository;
import tdr.pet.weblibrary.service.impl.PublisherServiceImpl;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TestPublisherService {

    @Mock
    private PublisherRepository publisherRepository;

    @Mock
    private PublisherMapper publisherMapper;

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
    void testUpdatePublisherById_Success() {
        Long publisherId = 1L;
        PublisherDTO publisherDTO = new PublisherDTO();
        Publisher foundPublisher = new Publisher();

        when(publisherRepository.findById(publisherId)).thenReturn(Optional.of(foundPublisher));

        publisherServiceImpl.updatePublisherById(publisherId, publisherDTO);

        verify(publisherMapper).update(publisherDTO, foundPublisher);
        verify(publisherRepository).save(foundPublisher);
    }

    @Test
    void testUpdatePublisherById_PublisherNotFound() {
        Long publisherId = 1L;
        PublisherDTO publisherDTO = new PublisherDTO();

        when(publisherRepository.findById(publisherId)).thenReturn(Optional.empty());

        assertThrows(PublisherNotFoundException.class, () ->
                publisherServiceImpl.updatePublisherById(publisherId, publisherDTO)
        );

        verify(publisherRepository, never()).save(any(Publisher.class));
    }

    @Test
    void testUpdatePublisherByName_Success() {
        String name = "Example Publisher";
        PublisherDTO publisherDTO = new PublisherDTO();
        Publisher foundPublisher = new Publisher();
        Set<Publisher> publishers = Collections.singleton(foundPublisher);

        when(publisherRepository.findPublishersByName(name)).thenReturn(publishers);

        publisherServiceImpl.updatePublisherByName(name, publisherDTO);

        verify(publisherMapper).update(publisherDTO, foundPublisher);
        verify(publisherRepository).save(foundPublisher);
    }

    @Test
    void testUpdatePublisherByName_MultiplePublishersFound() {
        String name = "Example Publisher";
        PublisherDTO publisherDTO = new PublisherDTO();
        Set<Publisher> publishers = new HashSet<>(Arrays.asList(new Publisher(), new Publisher()));

        when(publisherRepository.findPublishersByName(name)).thenReturn(publishers);

        assertThrows(MultiplePublishersFoundException.class, () ->
                publisherServiceImpl.updatePublisherByName(name, publisherDTO)
        );

        verify(publisherRepository, never()).save(any(Publisher.class));
    }

    @Test
    void testUpdatePublisherByName_PublisherNotFound() {
        String name = "Example Publisher";
        PublisherDTO publisherDTO = new PublisherDTO();
        Set<Publisher> publishers = Collections.emptySet();

        when(publisherRepository.findPublishersByName(name)).thenReturn(publishers);

        assertThrows(PublisherNotFoundException.class, () ->
                publisherServiceImpl.updatePublisherByName(name, publisherDTO)
        );

        verify(publisherRepository, never()).save(any(Publisher.class));
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
