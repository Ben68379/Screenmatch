package com.aluracursos.screenmatch.Principal;

import com.aluracursos.screenmatch.modelo.EpisodioDatos;
import com.aluracursos.screenmatch.modelo.SerieDatos;
import com.aluracursos.screenmatch.modelo.TemporadasDatos;
import com.aluracursos.screenmatch.service.ConsumoAPI;
import com.aluracursos.screenmatch.service.ConvierteDatos;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private final String URL = "https://www.omdbapi.com/?t=";
    private final String APIKEY = "&apikey=dfcd67d8";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    public void muestraElMenu(){
        System.out.println("Escribe el nombre de la série que deseas buscar");
        var nombreSerie = teclado.nextLine();
        var json = consumoAPI.obtenerDatos(URL + nombreSerie.replace(" ", "+") + APIKEY);
        //https://www.omdbapi.com/?t=game+of+thrones&apikey=4fc7c187
        SerieDatos datos = conversor.obtenerDatos(json, SerieDatos.class);
        System.out.println(datos);

        List<TemporadasDatos> temporadas = new ArrayList<>();

        for (int i = 1; i <= datos.totalDeTemporadas(); i++) {
            json = consumoAPI.obtenerDatos(URL + nombreSerie.replace(" ", "+") + "&Season=" + i + APIKEY);

            TemporadasDatos datosTemporada = conversor.obtenerDatos(json, TemporadasDatos.class);
            temporadas.add(datosTemporada);
        }
        temporadas.forEach(System.out::println);

        for (int i = 0; i < datos.totalDeTemporadas(); i++) {
            List<EpisodioDatos> episodiosTemporadas = temporadas.get(i).episodios();
            for (int j = 0; j < episodiosTemporadas.size(); j++) {
                System.out.println(episodiosTemporadas.get(j).Titulo());
            }
        }
        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.Titulo())));
    }

}
