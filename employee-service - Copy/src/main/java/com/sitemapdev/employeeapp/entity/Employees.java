package com.sitemapdev.employeeapp.entity;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@EqualsAndHashCode(of = "uuid")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Employees {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    private String uuid = UUID.randomUUID().toString();

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private String bloodgroup;

}
