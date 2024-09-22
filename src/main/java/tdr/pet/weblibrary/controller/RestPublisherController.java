package tdr.pet.weblibrary.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tdr.pet.weblibrary.model.dto.AuthorDTO;
import tdr.pet.weblibrary.model.dto.PublisherDTO;
import tdr.pet.weblibrary.model.mapper.PublisherMapper;
import tdr.pet.weblibrary.service.PublisherService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/publisher")
@Tag(name = "Publishers Controller", description = "Endpoints for managing publishers")
public class RestPublisherController {

    private final PublisherService publisherService;
    private final PublisherMapper publisherMapper;

    @Operation(summary = "Get publishers", description = "Returns a list of publishers based on page values")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthorDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request params")
    })
    @GetMapping(value = "/list", produces = "application/json")
    public ResponseEntity<List<PublisherDTO>> getPublishers(@RequestParam Integer pageNumber, Integer pageSize) {
        return ResponseEntity.ok(publisherService.getPublishers(PageRequest.of(pageNumber, pageSize)).stream()
                .map(publisherMapper::toDTO)
                .collect(Collectors.toList()));
    }

    @Operation(summary = "Find publishers by name", description = "Returns a set of publishers based on the provided name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PublisherDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid name supplied"),
            @ApiResponse(responseCode = "404", description = "Publisher not found")})
    @GetMapping(value = "/name/{name}", produces = "application/json")
    public ResponseEntity<Set<PublisherDTO>> findPublishersByName(@PathVariable String name) {
        return ResponseEntity.ok(publisherService.findPublishersByName(name).stream()
                .map(publisherMapper::toDTO)
                .collect(Collectors.toSet()));
    }

    @Operation(summary = "Find publishers by address", description = "Returns a list of publishers based on the provided address")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PublisherDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid address supplied"),
            @ApiResponse(responseCode = "404", description = "Publisher not found")})
    @GetMapping(value = "/address/{address}", produces = "application/json")
    public ResponseEntity<List<PublisherDTO>> findPublishersByAddress(@PathVariable String address) {
        return ResponseEntity.ok(publisherService.findPublishersByAddress(address).stream()
                .map(publisherMapper::toDTO)
                .collect(Collectors.toList()));
    }

    @Operation(summary = "Create a new publisher", description = "Creates a new publisher with the given details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Publisher created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PublisherDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")})
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Void> createNewPublisher(@RequestBody @Valid PublisherDTO publisherDTO) {
        publisherService.createNewPublisher(publisherMapper.toEntity(publisherDTO));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Update a publisher by ID", description = "Updates an existing publisher by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Publisher updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PublisherDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid ID or input"),
            @ApiResponse(responseCode = "404", description = "Publisher not found")})
    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Void> updatePublisherById(@PathVariable Long id, @RequestBody @Valid PublisherDTO publisherDTO) {
        publisherService.updatePublisherById(id, publisherDTO);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Update a publisher by name", description = "Updates an existing publisher by their name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Publisher updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PublisherDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid name or input"),
            @ApiResponse(responseCode = "404", description = "Publisher not found")})
    @PutMapping(value = "/name/{name}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Void> updatePublisherByName(@PathVariable String name, @RequestBody @Valid PublisherDTO publisherDTO) {
        publisherService.updatePublisherByName(name, publisherDTO);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Delete a publisher by ID", description = "Deletes an existing publisher by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Publisher deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
            @ApiResponse(responseCode = "404", description = "Publisher not found")})
    @DeleteMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Void> deletePublisherById(@PathVariable Long id) {
        publisherService.deletePublisherById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete a publisher by name", description = "Deletes an existing publisher by their name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Publisher deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid name supplied"),
            @ApiResponse(responseCode = "404", description = "Publisher not found")})
    @DeleteMapping(value = "/name/{name}", produces = "application/json")
    public ResponseEntity<Void> deletePublisherByName(@PathVariable String name) {
        publisherService.deletePublisherByName(name);
        return ResponseEntity.noContent().build();
    }
}
