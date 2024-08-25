package tdr.pet.weblibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tdr.pet.weblibrary.model.entity.Publisher;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {
    Publisher getPublisherByName(String name);

    Publisher getPublisherByAddress(String address);
}
