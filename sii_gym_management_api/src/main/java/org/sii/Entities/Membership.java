package org.sii.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.sii.Enums.MembershipType;
import org.sii.Model.Price;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Membership {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer membershipId;

    private String name;

    private Integer maxMembers;

    private Integer duration;

    @Enumerated(EnumType.STRING)
    private MembershipType membershipType;

    @Embedded
    private Price price;

    @ManyToOne
    @JoinColumn(name = "gym_id")
    private Gym gym;

    @OneToMany(mappedBy = "membership", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Member> members = new ArrayList<>();

}
