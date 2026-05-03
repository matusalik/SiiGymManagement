package org.sii.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.sii.Enums.MemberStatus;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer member_id;

    private String first_name;

    private String last_name;

    private String email;

    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    @ManyToOne
    @JoinColumn(name = "membership_id")
    private Membership membership;
}
