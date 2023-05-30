package com.swm.mvp.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class Transcript {
    private String text;
    private Double start;
    private Double duration;
}
