package io.github.khidiraliev.biotracker.domain;

import io.github.khidiraliev.biotracker.dto.unit.UnitMainInfoResponse;
import io.github.khidiraliev.biotracker.exception.unit.UnitNotFoundException;
import io.github.khidiraliev.biotracker.repository.UnitRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UnitServiceTest {
    @Mock
    UnitRepository repository;

    @InjectMocks
    UnitService service;

    @Nested
    class GetMainInfo {

        @Test
        void shouldReturnCorrectDto() {
            final Long id = 1L;
            final UnitMainInfoResponse expectedResponse = new UnitMainInfoResponse(id, "testUnit", "u");

            when(repository.findMainInfoById(id)).thenReturn(Optional.of(expectedResponse));

            assertThat(service.getMainInfo(id)).isEqualTo(expectedResponse);
            verify(repository, times(1)).findMainInfoById(id);
        }

        @Test
        void shouldThrowNotFoundExceptionWhenIdNotExists() {
            final Long id = 99L;
            when(repository.findMainInfoById(id)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> service.getMainInfo(id)).isInstanceOf(UnitNotFoundException.class);
            verify(repository, times(1)).findMainInfoById(id);
        }
    }
}