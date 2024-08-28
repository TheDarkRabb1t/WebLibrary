package tdr.pet.weblibrary.service;

import tdr.pet.weblibrary.model.entity.Publisher;

import java.util.List;

public interface PublisherService {

    Publisher getPublisherByName(String name);

    List<Publisher> getPublishersByAddress(String address);

    void createNewPublisher(Publisher publisher);

    void updatePublisherById(Long id, Publisher publisher);

    void updatePublisherByName(String name, Publisher publisher);

    void deletePublisherById(Long id);

    void deletePublisherByName(String name);
}
