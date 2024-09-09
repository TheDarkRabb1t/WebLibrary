package tdr.pet.weblibrary.service;

import tdr.pet.weblibrary.model.dto.PublisherDTO;
import tdr.pet.weblibrary.model.entity.Publisher;

import java.util.List;
import java.util.Set;

public interface PublisherService {

    Set<Publisher> findPublishersByName(String name);

    List<Publisher> findPublishersByAddress(String address);

    boolean exists(String name);

    void createNewPublisher(Publisher publisher);

    void updatePublisherById(Long id, PublisherDTO publisherDTO);

    void updatePublisherByName(String name, PublisherDTO publisherDTO);

    void deletePublisherById(Long id);

    void deletePublisherByName(String name);
}
