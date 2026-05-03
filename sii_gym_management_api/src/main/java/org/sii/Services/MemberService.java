package org.sii.Services;

import lombok.RequiredArgsConstructor;
import org.sii.Repositories.MemberRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
}
