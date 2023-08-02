package com.sitemapdev.addressservice.entity;

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
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    private String uuid = UUID.randomUUID().toString();

    @Column
    private String lane1;

    @Column
    private String lane2;

    @Column
    private String state;

    @Column
    private String zip;
    
    @Column(unique = true)
    private Long employeeId;
}
