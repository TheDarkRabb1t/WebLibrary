package tdr.pet.weblibrary.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tdr.pet.weblibrary.exception.publisher.PublisherNotFoundException;
import tdr.pet.weblibrary.model.entity.Publisher;
import tdr.pet.weblibrary.repository.PublisherRepository;
import tdr.pet.weblibrary.service.PublisherService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PublisherServiceImpl implements PublisherService {
    private final PublisherRepository publisherRepository;

    @Override
    public Set<Publisher> findPublishersByName(String name) {
        return new HashSet<>(publisherRepository.findPublishersByName(name));
    }

    @Override
    public List<Publisher> findPublishersByAddress(String address) {
        return publisherRepository.getPublishersByAddress(address);
    }

    @Override
    public boolean exists(String name) {
        return publisherRepository.existsByName(name);
    }

    @Override
    public void createNewPublisher(Publisher publisher) {
        publisherRepository.save(publisher);
    }

    @Override
    public void updatePublisherById(Long id, Publisher publisher) {
        if (publisherRepository.existsById(id)) {
            publisherRepository.updatePublisherById(id, publisher);
        } else {
            throw new PublisherNotFoundException();
        }
    }

    @Override
    public void updatePublisherByName(String name, Publisher publisher) {
        if (publisherRepository.existsByName(name)) {
            publisherRepository.updatePublisherByName(name, publisher);
        } else {
            throw new PublisherNotFoundException();
        }
    }

    @Override
    public void deletePublisherById(Long id) {
        if (publisherRepository.existsById((id))) {
            publisherRepository.deleteById(id);
        } else {
            throw new PublisherNotFoundException();
        }
    }

    @Override
    public void deletePublisherByName(String name) {
        if (publisherRepository.existsByName(name)) {
            publisherRepository.deletePublisherByName(name);
        } else {
            throw new PublisherNotFoundException();
        }
    }
}
