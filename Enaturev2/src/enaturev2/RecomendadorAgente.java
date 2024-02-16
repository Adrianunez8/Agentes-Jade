package enaturev2;

import jade.core.Agent;
import weka.core.*;
import weka.core.neighboursearch.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RecomendadorAgente extends Agent {

    private Instances data;

    protected void setup() {
        // Cargar los datos de actividades desde un archivo ARFF
        cargarDatosActividades(System.getProperty("user.home") + File.separator + "Escritorio" + File.separator + "actividades.arff");

        // Generar recomendaciones
        String estilo = "visual";
        String inteligencia = "linguistica";
        List<Recomendacion> recomendaciones = generarRecomendaciones(estilo, inteligencia);

        // Imprimir las recomendaciones
        for (Recomendacion recomendacion : recomendaciones) {
            System.out.println("Estudiante: " + recomendacion.getEstudiante() + ", Actividad: " + recomendacion.getActividad());
        }
    }

    private void cargarDatosActividades(String rutaArchivo) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(rutaArchivo));
            data = new Instances(reader);
            reader.close();
            data.setClassIndex(data.numAttributes() - 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Recomendacion> generarRecomendaciones(String estilo, String inteligencia) {
        List<Recomendacion> recomendaciones = new ArrayList<>();

        // Crear una nueva instancia con los atributos estilo e inteligencia
        Instance instancia = new DenseInstance(3);
        Attribute estiloAttr = data.attribute("estilo_aprendizaje");
        Attribute inteligenciaAttr = data.attribute("inteligencia_multiple");
        instancia.setValue(estiloAttr, estilo);
        instancia.setValue(inteligenciaAttr, inteligencia);

        // Calcular la distancia entre la instancia y todas las demás instancias
        NearestNeighbourSearch knn = new LinearNNSearch(data);
        try {
            Instances vecinos = knn.kNearestNeighbours(instancia, 3); // Obtener los 3 vecinos más cercanos
            // Recuperar las actividades y el estudiante de los vecinos más cercanos como recomendaciones
            for (int i = 0; i < vecinos.numInstances(); i++) {
                String estudiante = vecinos.instance(i).stringValue(vecinos.attribute("estudiante"));
                String actividad = vecinos.instance(i).stringValue(data.classAttribute());
                recomendaciones.add(new Recomendacion(estudiante, actividad));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return recomendaciones;
    }

    // Clase para representar una recomendación con el estudiante correspondiente
    private static class Recomendacion {
        private String estudiante;
        private String actividad;

        public Recomendacion(String estudiante, String actividad) {
            this.estudiante = estudiante;
            this.actividad = actividad;
        }

        public String getEstudiante() {
            return estudiante;
        }

        public String getActividad() {
            return actividad;
        }
    }
}


