package io.github.khidiraliev.biotracker.controller;

import io.github.khidiraliev.biotracker.domain.UnitService;
import io.github.khidiraliev.biotracker.dto.unit.UnitCatalogRequest;
import io.github.khidiraliev.biotracker.dto.unit.UnitCreatingRequest;
import io.github.khidiraliev.biotracker.dto.unit.UnitMainInfoResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/units")
@RequiredArgsConstructor
public class UnitController {
    private final UnitService service;

    @GetMapping
    public List<UnitCatalogRequest> getCatalog() {
        return service.getAllForCatalog();
    }

    @GetMapping("/{id}")
    public UnitMainInfoResponse getMainInfo(@PathVariable @Positive Long id) {
        return service.getMainInfo(id);
    }

    @PostMapping
    public UnitMainInfoResponse createUnit(@RequestBody @Valid @NotNull UnitCreatingRequest request) {
        return service.createNewUnit(request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUnit(@PathVariable @Positive Long id) {
        service.deleteById(id);
    }
}
