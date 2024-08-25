package tdr.pet.weblibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tdr.pet.weblibrary.model.entity.Publisher;

import java.util.List;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {
    List<Publisher> getPublishersByName(String name);

    List<Publisher> getPublishersByAddress(String address);

    List<Publisher> getPublisherByNameAndAddress(String name, String address);
}
