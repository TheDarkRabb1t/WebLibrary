package tdr.pet.weblibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tdr.pet.weblibrary.model.entity.Publisher;

import java.util.List;
import java.util.Optional;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {
    Optional<Publisher> getPublisherByName(String name);

    List<Publisher> getPublishersByAddress(String address);

    Publisher updatePublisherById(Long id, Publisher publisher);

    void createPublisher(Publisher publisher);

    void deletePublisherById(Long id);
}
