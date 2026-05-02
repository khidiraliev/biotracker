package io.github.khidiraliev.biotracker.repository;

import io.github.khidiraliev.biotracker.dto.unit.UnitCatalogResponse;
import io.github.khidiraliev.biotracker.entity.Unit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class UnitRepositoryTest {
    @Autowired
    TestEntityManager entityManager;

    @Autowired
    UnitRepository repository;

    @Nested
    class FindAllUnitForCatalog {
        static final int ENTITY_COUNT = 10;

        final List<Unit> savedUnits = new ArrayList<>();

        @BeforeEach
        void initDb() {
            for (int i = 1; i <= ENTITY_COUNT; i++) {
                savedUnits.add(entityManager.persist(new Unit(null, "test_unit" + i, null)));
            }
        }

        @Test
        void shouldReturnCorrectDtoList() {
            List<UnitCatalogResponse> responses = savedUnits.stream()
                    .map(unit -> new UnitCatalogResponse(unit.getId(), unit.getFullName()))
                    .toList();

            assertThat(repository.findAllUnitForCatalog()).containsAll(responses);
        }
    }
}