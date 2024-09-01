package tdr.pet.weblibrary.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import tdr.pet.weblibrary.model.dto.PublisherDTO;
import tdr.pet.weblibrary.model.mapper.PublisherMapper;
import tdr.pet.weblibrary.service.PublisherService;

@RestController("/api/v1/publisher")
@AllArgsConstructor
public class RestPublisherController {
    private final PublisherService publisherService;
    private final PublisherMapper publisherMapper;

    @GetMapping("/{publisherName}")
    public ResponseEntity<PublisherDTO> getPublisherByName(@PathVariable String publisherName, BindingResult bindingResult) {
        if (ObjectUtils.isEmpty(publisherName) || bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(publisherMapper.toDTO(publisherService.getPublisherByName(publisherName)));
    }

    @PostMapping("/create")
    public ResponseEntity<PublisherDTO> createPublisher(@Valid @RequestBody PublisherDTO publisherDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        publisherService.createNewPublisher(publisherMapper.toEntity(publisherDTO));
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{publisherName}")
    public ResponseEntity<PublisherDTO> updatePublisherByName(@PathVariable String publisherName, @Valid @RequestBody PublisherDTO publisherDTO, BindingResult bindingResult) {
        if (ObjectUtils.isEmpty(publisherName) || bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        publisherService.updatePublisherByName(publisherName, publisherMapper.toEntity(publisherDTO));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{publisherName}")
    public ResponseEntity<Void> deletePublisherByName(@PathVariable String publisherName, BindingResult bindingResult) {
        if (ObjectUtils.isEmpty(publisherName) || bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }
}
