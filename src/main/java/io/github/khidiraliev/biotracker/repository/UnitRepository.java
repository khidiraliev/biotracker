package io.github.khidiraliev.biotracker.repository;

import io.github.khidiraliev.biotracker.dto.unit.UnitCatalogResponse;
import io.github.khidiraliev.biotracker.dto.unit.UnitMainInfoResponse;
import io.github.khidiraliev.biotracker.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Long> {

    @Query("SELECT new io.github.khidiraliev.biotracker.dto.unit.UnitCatalogResponse(u.id, u.fullName) " +
            "FROM Unit u")
    List<UnitCatalogResponse> findAllUnitForCatalog();

    @Query("SELECT new io.github.khidiraliev.biotracker.dto.unit.UnitMainInfoResponse(u.id, u.fullName, u.shortName) " +
            "FROM Unit u " +
            "WHERE u.id = :id")
    Optional<UnitMainInfoResponse> findMainInfoById(Long id);

    Boolean existsByFullName(String fullName);

    @Modifying
    @Query("DELETE Unit u WHERE u.id = :id")
    int deleteByIdAndReturnCount(Long id);
}
