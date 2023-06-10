package com.swm.mvp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Transcript {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;
    private Double start;
    private Double duration;
    @Lob
    @Column(length = 16777215)
    private byte[] audio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "youtube_id", nullable = false)
    @JsonBackReference
    private Youtube youtube;
}