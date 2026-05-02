package io.github.khidiraliev.biotracker.domain;

import io.github.khidiraliev.biotracker.dto.unit.UnitCatalogResponse;
import io.github.khidiraliev.biotracker.dto.unit.UnitCreatingRequest;
import io.github.khidiraliev.biotracker.dto.unit.UnitMainInfoResponse;
import io.github.khidiraliev.biotracker.entity.Unit;
import io.github.khidiraliev.biotracker.exception.unit.UnitNameAlreadyExistsException;
import io.github.khidiraliev.biotracker.exception.unit.UnitNotFoundException;
import io.github.khidiraliev.biotracker.repository.UnitRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UnitService {
    private final UnitRepository repository;

    public List<UnitCatalogResponse> getAllForCatalog() {
        return repository.findAllUnitForCatalog();
    }

    public UnitMainInfoResponse getMainInfo(Long id) {
        return repository.findMainInfoById(id)
                .orElseThrow(() -> new UnitNotFoundException(id));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public UnitMainInfoResponse createNewUnit(UnitCreatingRequest request) {
        if (repository.existsByFullName(request.fullName())) {
            throw new UnitNameAlreadyExistsException(request.fullName());
        }
        Unit unit = new Unit();
        unit.setFullName(request.fullName());
        unit.setShortName(request.shortName());
        unit = repository.save(unit);

        log.info("Unit with id {} saved.", unit.getId());

        return formMainInfoResponse(unit);
    }

    public UnitMainInfoResponse formMainInfoResponse(Unit unit) {
        return new UnitMainInfoResponse(
                unit.getId(),
                unit.getFullName(),
                unit.getShortName()
        );
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void deleteById(Long id) {
        if (repository.deleteByIdAndReturnCount(id) == 0) {
            throw new UnitNotFoundException(id);
        }

        log.info("Unit with id {} deleted.", id);
    }
}
