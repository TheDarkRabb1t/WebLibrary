package tdr.pet.weblibrary.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import tdr.pet.weblibrary.exception.publisher.MultiplePublishersFoundException;
import tdr.pet.weblibrary.exception.publisher.PublisherNotFoundException;
import tdr.pet.weblibrary.model.dto.PublisherDTO;
import tdr.pet.weblibrary.model.entity.Publisher;
import tdr.pet.weblibrary.model.mapper.PublisherMapper;
import tdr.pet.weblibrary.repository.PublisherRepository;
import tdr.pet.weblibrary.service.PublisherService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class PublisherServiceImpl implements PublisherService {
    private final PublisherRepository publisherRepository;
    private final PublisherMapper publisherMapper;

    @Override
    public Page<Publisher> getPublishers(PageRequest pageRequest) {
        return publisherRepository.findAll(pageRequest);
    }

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
    public void updatePublisherById(Long id, PublisherDTO publisherDTO) {
        Publisher foundAuthor = publisherRepository.findById(id)
                .orElseThrow(() -> new PublisherNotFoundException("Couldn't found publisher by id"));
        publisherMapper.update(publisherDTO, foundAuthor);
        publisherRepository.save(foundAuthor);
    }

    @Override
    public void updatePublisherByName(String name, PublisherDTO publisherDTO) {
        Set<Publisher> publishers = publisherRepository.findPublishersByName(name);
        if (publishers.size() > 1) {
            throw new MultiplePublishersFoundException();
        }
        Publisher publisher = publishers.stream().findFirst()
                .orElseThrow(() -> new PublisherNotFoundException("Couldn't found publisher by name"));
        publisherMapper.update(publisherDTO, publisher);
        publisherRepository.save(publisher);
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
