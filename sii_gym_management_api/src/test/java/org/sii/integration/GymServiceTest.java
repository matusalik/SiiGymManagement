package org.sii.integration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.sii.DTO.Gym.GymRequest;
import org.sii.DTO.Gym.GymResponse;
import org.sii.Entities.Gym;
import org.sii.Exceptions.GymAlreadyExistsException;
import org.sii.Repositories.GymRepository;
import org.sii.Services.GymService;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class GymServiceTest {
    @Mock
    private GymRepository gymRepository;

    @InjectMocks
    private GymService gymService;

    @Test
    @DisplayName("Should save gym when gymName is unique")
    void shouldSaveGymSuccessfully(){
        GymRequest dto = new GymRequest(
                "IronGym",
                "Katowice, ul.Mariacka 3",
                "123321231"
        );
        when(gymRepository.existsByNameIgnoreCase(anyString())).thenReturn(false);
        when(gymRepository.save(any(Gym.class))).thenAnswer(i -> i.getArguments()[0]);

        GymResponse res = gymService.addGym(dto);

        assertThat(res).isNotNull();
        assertThat(res.name()).isEqualTo("IronGym");
        verify(gymRepository, times(1)).save(any(Gym.class));
    }

    @Test
    @DisplayName("Should throw GymAlreadyExistsException when gym name already occurs")
    void shouldThrowExceptionWhenNameExists(){
        GymRequest dto = new GymRequest(
                "IronGym",
                "Katowice, ul.Mariacka 3",
                "123321231"
        );
        when(gymRepository.existsByNameIgnoreCase("IronGym")).thenReturn(true);

        assertThatThrownBy(() -> gymService.addGym(dto))
                .isInstanceOf(GymAlreadyExistsException.class)
                .hasMessageContaining("already exists");
        verify(gymRepository, never()).save(any());
    }
}
