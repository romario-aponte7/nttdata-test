package com.nttdatates.entity;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "persons")
@EqualsAndHashCode(of = "personId")
@ToString(of = "personId")
@Builder
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID personId;

    private String name;

    private String gender;

    private Integer age;

    @NotNull
    @Column(unique = true)
    private String identification;

    private String direction;

    private String phone;
}
