package com.swm.mvp.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
@Getter
@Setter

public class Youtube {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String link;

    @ElementCollection
    private List<Transcript> transcriptList;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "users_id", nullable = false)
    private Users users;
}
