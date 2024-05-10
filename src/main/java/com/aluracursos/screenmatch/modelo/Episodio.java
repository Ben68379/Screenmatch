package com.aluracursos.screenmatch.modelo;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Episodio {
    private Integer temporada;
    private  String titulo;
    private Integer numeroEpisodio;
    private Double evaluacion;
    private LocalDate fechaDeLanzamiento;

    public Episodio(Integer temporada, EpisodioDatos datosEpisodio) {
        this.temporada = temporada;
        this.titulo = datosEpisodio.Titulo();
        this.numeroEpisodio = datosEpisodio.Episodio();
        try {
            this.evaluacion = Double.valueOf(datosEpisodio.Puntuacion()) ;
        } catch (NumberFormatException ex){
            this.evaluacion = 0.0;
        }
        try{
            this.fechaDeLanzamiento = LocalDate.parse(String.valueOf(datosEpisodio.FechaDeLanzamiento()));
        }catch (DateTimeParseException ex){
            this.fechaDeLanzamiento = null;
        }

    }

    @Override
    public String toString() {
        return
                "temporada=" + temporada +
                        ", titutlo='" + titulo + '\'' +
                        ", numeroEpisodio=" + numeroEpisodio +
                        ", evaluacion=" + evaluacion +
                        ", fechaDeLanzamiento=" + fechaDeLanzamiento;
    }

    public Integer getTemporada() {
        return temporada;
    }

    public void setTemporada(Integer temporada) {
        this.temporada = temporada;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getNumeroEpisodio() {
        return numeroEpisodio;
    }

    public void setNumeroEpisodio(Integer numeroEpisodio) {
        this.numeroEpisodio = numeroEpisodio;
    }

    public Double getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(Double evaluacion) {
        this.evaluacion = evaluacion;
    }

    public LocalDate getFechaDeLanzamiento() {
        return fechaDeLanzamiento;
    }

    public void setFechaDeLanzamiento(LocalDate fechaDeLanzamiento) {
        this.fechaDeLanzamiento = fechaDeLanzamiento;
    }
}
