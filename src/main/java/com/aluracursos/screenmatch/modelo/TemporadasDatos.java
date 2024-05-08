package com.aluracursos.screenmatch.modelo;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TemporadasDatos(@JsonAlias("Season") Integer Temporadas,
                              @JsonAlias("Episodes") List<EpisodioDatos> episodios) {
}
