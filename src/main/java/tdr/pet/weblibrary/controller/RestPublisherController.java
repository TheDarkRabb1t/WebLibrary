package tdr.pet.weblibrary.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import tdr.pet.weblibrary.model.dto.PublisherDTO;
import tdr.pet.weblibrary.model.entity.Publisher;
import tdr.pet.weblibrary.model.mapper.PublisherMapper;
import tdr.pet.weblibrary.service.PublisherService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/publisher")
@Tag(name = "Publishers Controller")
public class RestPublisherController {
    private final PublisherService publisherService;
    private final PublisherMapper publisherMapper;

    @GetMapping("/name/{name}")
    public ResponseEntity<Set<PublisherDTO>> findPublishersByName(@PathVariable String name) {
        return ResponseEntity.ok(publisherService.findPublishersByName(name).stream()
                .map(publisherMapper::toDTO)
                .collect(Collectors.toSet()));
    }

    @GetMapping("/address/{address}")
    public ResponseEntity<List<PublisherDTO>> findPublishersByAddress(@PathVariable String address) {
        return ResponseEntity.ok(publisherService.findPublishersByAddress(address).stream()
                .map(publisherMapper::toDTO)
                .collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity<Void> createNewPublisher(@RequestBody @Valid PublisherDTO publisherDTO) {
        publisherService.createNewPublisher(publisherMapper.toEntity(publisherDTO));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePublisherById(@PathVariable Long id, @RequestBody @Valid PublisherDTO publisherDTO) {
        publisherService.updatePublisherById(id, publisherDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/name/{name}")
    public ResponseEntity<Void> updatePublisherByName(@PathVariable String name, @RequestBody @Valid PublisherDTO publisherDTO) {
        publisherService.updatePublisherByName(name, publisherDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePublisherById(@PathVariable Long id) {
        publisherService.deletePublisherById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/name/{name}")
    public ResponseEntity<Void> deletePublisherByName(@PathVariable String name) {
        publisherService.deletePublisherByName(name);
        return ResponseEntity.noContent().build();
    }
}
