package org.sii.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Gym {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer gymId;

    @Column(unique = true, nullable = false)
    private String name;

    private String address;

    private String phoneNumber;

    @OneToMany(mappedBy = "gym", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Membership> memberships;

}
