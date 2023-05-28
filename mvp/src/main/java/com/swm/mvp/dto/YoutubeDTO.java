package com.swm.mvp.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public record YoutubeDTO(String link) implements Serializable {
}
