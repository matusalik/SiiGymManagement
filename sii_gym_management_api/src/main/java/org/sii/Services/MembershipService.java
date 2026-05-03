package org.sii.Services;

import lombok.RequiredArgsConstructor;
import org.sii.Repositories.MembershipRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MembershipService {
    private final MembershipRepository membershipRepository;
}
