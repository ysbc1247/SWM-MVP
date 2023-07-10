package com.swm.mvp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
@Getter
@Setter
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String link;

    @OneToMany(mappedBy = "video", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Transcript> transcriptList;

    @OneToMany(mappedBy = "video")
    private List<UserVideo> userVideos;

}