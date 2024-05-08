package com.aluracursos.screenmatch.modelo;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public record EpisodioDatos(@JsonAlias("Title") String Titulo,
                            @JsonAlias("Year") Integer FechaDeLanzamiento,
                            @JsonAlias("Episode") Integer Episodio,
                            @JsonAlias("imdbRating") String Puntuacion){
}
