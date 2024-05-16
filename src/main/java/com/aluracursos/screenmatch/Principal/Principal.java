package com.aluracursos.screenmatch.Principal;
import com.aluracursos.screenmatch.modelo.Episodio;
import com.aluracursos.screenmatch.modelo.Serie;
import com.aluracursos.screenmatch.modelo.SerieDatos;
import com.aluracursos.screenmatch.modelo.TemporadasDatos;
import com.aluracursos.screenmatch.repository.SerieRepository;
import com.aluracursos.screenmatch.service.ConsumoAPI;
import com.aluracursos.screenmatch.service.ConvierteDatos;

import java.util.*;
import java.util.stream.Collectors;
public class Principal {

    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=dfcd67d8";
    private ConvierteDatos conversor = new ConvierteDatos();
    private List<SerieDatos> datosSeries = new ArrayList<>();
    private List<Serie> series = new ArrayList<>();



    private SerieRepository repository;
    public Principal(SerieRepository repository) {
        this.repository = repository;
    }
    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - Buscar series 
                    2 - Buscar episodios
                    3 - Mostrar series buscadas
                                  
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarSerieWeb();
                    break;
                case 2:
                    buscarEpisodioPorSerie();
                    break;
                case 3:
                    listarSeriesBuscadas();
                    break;

                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }

    }

    private SerieDatos getDatosSerie() {
        System.out.println("Escribe el nombre de la serie que deseas buscar");
        var nombreSerie = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + nombreSerie.replace(" ", "+") + API_KEY);
        System.out.println(json);
        SerieDatos datos = conversor.obtenerDatos(json, SerieDatos.class);
        return datos;
    }
    private void buscarEpisodioPorSerie() {
        listarSeriesBuscadas();
        System.out.println("Escriba el nombre de la serie que desea ver");
        var nombreSerie = teclado.nextLine();
        Optional<Serie> serie = series.stream()
                .filter(s -> s.getTitulo().toLowerCase().contains(nombreSerie.toLowerCase()))
                .findFirst();
        if (serie.isPresent()) {
            var serieBuscada = serie.get();
            List<TemporadasDatos> temporadas = new ArrayList<>();
            for (int i = 1; i <= serieBuscada.getTotalTemporadas(); i++) {
                var json = consumoApi.obtenerDatos(URL_BASE + serieBuscada.getTitulo().replace(" ", "+") + "&season=" + i + API_KEY);
                TemporadasDatos datosTemporada = conversor.obtenerDatos(json, TemporadasDatos.class);
                temporadas.add(datosTemporada);
            }
//            temporadas.forEach(System.out::println);
            List<Episodio> episodios = temporadas.stream()
                    .flatMap(d -> d.episodios().stream()
                            .map(e -> new Episodio(d.Temporadas(), e)))
                    .collect(Collectors.toList());

            serieBuscada.setEpisodios(episodios);
            repository.save(serieBuscada);
        }
    }
    private void buscarSerieWeb() {
        SerieDatos datos = getDatosSerie();
        Serie serie = new Serie(datos);
        repository.save(serie);
        //datosSeries.add(datos);
        System.out.println(datos);
    }
    private void listarSeriesBuscadas() {
        series = repository.findAll();
        series.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);

    }
    }

