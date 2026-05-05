package org.sii.integration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.sii.Common.AppConstraints;
import org.sii.DTO.Gym.GymRequest;
import org.sii.DTO.Membership.MembershipRequest;
import org.sii.DTO.Membership.MembershipResponse;
import org.sii.Entities.Gym;
import org.sii.Entities.Membership;
import org.sii.Exceptions.UnsupportedCurrencyException;
import org.sii.Repositories.GymRepository;
import org.sii.Repositories.MembershipRepository;
import org.sii.Services.MembershipService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class MembershipServiceTest {
    @Mock
    private MembershipRepository membershipRepository;

    @Mock
    private GymRepository gymRepository;

    @Mock
    private AppConstraints appConstraints;

    @InjectMocks
    private MembershipService membershipService;

    @Test
    @DisplayName("Should save membership when currency is valid and gym exists")
    void shouldSaveMembershipSuccessfully(){
        Integer gymId = 1;

        MembershipRequest membershipDto = new MembershipRequest(
                "IronGym premium",
                10,
                "PREMIUM",
                new BigDecimal("500.00"),
                "USD",
                1
        );

        Gym mockGym = new Gym();
        mockGym.setGymId(1);
        mockGym.setName("IronGym");

        when(appConstraints.getSupportedCurrencies()).thenReturn(List.of("PLN", "EUR", "USD"));

        when(gymRepository.findById(gymId)).thenReturn(Optional.of(mockGym));

        when(membershipRepository.save(any(Membership.class))).thenAnswer(invocation -> {
            Membership m = invocation.getArgument(0);
            m.setMembershipId(100);
            return m;
        });

        MembershipResponse res = membershipService.addMembership(membershipDto);

        assertThat(res).isNotNull();
        assertThat(res.membershipId().equals(100));
        assertThat(res.currency().equals("USD"));

        verify(membershipRepository, times(1)).save(any(Membership.class));

        verify(gymRepository).findById(gymId);
    }

    @Test
    @DisplayName("Should throw exception when currency is NOT supported")
    void shouldThrowExceptionForInvalidCurrency() {
        Integer gymId = 1;
        MembershipRequest membershipDto = new MembershipRequest(
                "IronGym premium",
                10,
                "PREMIUM",
                new BigDecimal("500.00"),
                "USD",
                1
        );

        assertThatThrownBy(() -> membershipService.addMembership(membershipDto))
                .isInstanceOf(UnsupportedCurrencyException.class);

        verify(membershipRepository, never()).save(any());
    }
}
