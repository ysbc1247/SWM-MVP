package com.swm.mvp.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.Entity;

@Embeddable
@Getter
@Setter
public class Transcript {
    private String text;
    private Double start;
    private Double duration;
}
