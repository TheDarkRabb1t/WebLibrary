package tdr.pet.weblibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tdr.pet.weblibrary.model.entity.Publisher;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {
    Set<Publisher> findPublishersByName(String name);

    List<Publisher> getPublishersByAddress(String address);

    boolean existsByName(String name);

    void deletePublisherByName(String name);
}
