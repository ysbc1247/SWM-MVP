package com.swm.mvp.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Lob;

@Embeddable
@Getter
@Setter
public class Transcript {
    private String text;
    private Double start;
    private Double duration;
    @Lob
    @Column(length = 16777215)
    private byte[] audio;
}
