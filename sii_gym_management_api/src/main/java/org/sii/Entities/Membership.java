package org.sii.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.sii.Enums.MembershipType;
import org.sii.Model.Price;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Membership {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer membership_id;

    private String name;

    private Integer max_members;

    private Integer duration;

    @Enumerated(EnumType.STRING)
    private MembershipType membership_type;

    @Embedded
    private Price price;

    @ManyToOne
    @JoinColumn(name = "gym_id")
    private Gym gym;

    @OneToMany(mappedBy = "membership", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Member> members;

}
