package enaturev2;

//
//import jade.core.Agent;
//import jade.core.behaviours.CyclicBehaviour;
//import jade.lang.acl.ACLMessage;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class RecomendadorAgente extends Agent {
//
//    private Map<String, String[]> perfilesEstudiantes;
//
//    protected void setup() {
//        perfilesEstudiantes = new HashMap<>();
//
//        addBehaviour(new RecibirPerfilBehavior());
//    }
//
//    private class RecibirPerfilBehavior extends CyclicBehaviour {
//        public void action() {
//            ACLMessage mensaje = receive();
//            if (mensaje != null) {
//                // Obtener datos del mensaje
//                String[] datos = mensaje.getContent().split(",");
//                String estudiante = mensaje.getSender().getLocalName();
//                String estiloAprendizaje = datos[0];
//                String inteligenciasMultiples = datos[1];
//                //System.arraycopy(datos, 1, inteligenciasMultiples, 0, inteligenciasMultiples.length);
//
//                // Almacenar el perfil del estudiante
//                perfilesEstudiantes.put(estudiante, new String[]{estiloAprendizaje, String.join(",", inteligenciasMultiples)});
//
//                // Realizar recomendación basada en el estilo de aprendizaje e inteligencias múltiples
//                String recomendacion = recomendarActividad(estiloAprendizaje, inteligenciasMultiples);
//
//                // Enviar la recomendación al estudiante
//                enviarRecomendacionAlEstudiante(estudiante, recomendacion);
//            } else {
//                block();
//            }
//        }
//
//        private String recomendarActividad(String estiloAprendizaje, String inteligenciasMultiples) {
//            // Lógica de recomendación basada en el estilo de aprendizaje e inteligencias múltiples
//            // (Este es un algoritmo ficticio; debes reemplazarlo con tu lógica real)
//               String actividadRecomendada = "";
////
//            // Comprobamos si el estilo de aprendizaje es visual y la inteligencia múltiple es interpersonal
//            if (estiloAprendizaje.equalsIgnoreCase("Visual")
//                    && inteligenciasMultiples.equalsIgnoreCase("Interpersonal")) {
//                actividadRecomendada = "Ver Videos en YouTube del tema de interes";
//            } 
//            else if(estiloAprendizaje.equalsIgnoreCase("Auditivo")
//                    && inteligenciasMultiples.equalsIgnoreCase("Naturalista")) {
//                actividadRecomendada = "Escuchar potscast educativos y musica clasica para relajacion";
//            }
//            else {
//                actividadRecomendada = "Refuerzos con videos del tema a tratar";
//            }
//
//            return actividadRecomendada;
//         
//        }
//
//        private void enviarRecomendacionAlEstudiante(String estudiante, String recomendacion) {
//            // Enviar las recomendaciones al estudiante
//            ACLMessage mensaje = new ACLMessage(ACLMessage.INFORM);
//            mensaje.addReceiver(getAID(estudiante));
//            mensaje.setContent(recomendacion);
//            send(mensaje);
//
//            System.out.println("RecomendadorAgente: Enviando recomendación a perfilAgente - Te recomiendo: " + recomendacion);
//        }
//    }
//}

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RecomendadorAgente extends Agent {

    private List<String> actividadesRecomendadas;

    protected void setup() {
        actividadesRecomendadas = leerActividadesDesdeArchivo(System.getProperty("user.home") + File.separator + "Escritorio" + File.separator + "actividades_recomendadas.txt");

        addBehaviour(new RecibirPerfilBehavior());
    }

    private class RecibirPerfilBehavior extends CyclicBehaviour {
        public void action() {
            ACLMessage mensaje = receive();
            if (mensaje != null) {
                // Obtener datos del mensaje
                String[] datos = mensaje.getContent().split(",");
                String estudiante = mensaje.getSender().getLocalName();
                String estiloAprendizaje = datos[0];
                String inteligenciaMultiple = datos[1];

                // Realizar recomendación basada en el estilo de aprendizaje e inteligencia múltiple
                String recomendacion = recomendarActividad(estiloAprendizaje, inteligenciaMultiple);

                // Enviar la recomendación al estudiante
                enviarRecomendacionAlEstudiante(estudiante, recomendacion);
            } else {
                block();
            }
        }

        private String recomendarActividad(String estiloAprendizaje, String inteligenciaMultiple) {
            for (String actividad : actividadesRecomendadas) {
                String[] partes = actividad.split(":");
                if (partes.length == 2) {
                    String[] combinacion = partes[0].split(",");
                    String recomendacion = partes[1].trim();

                    // Verificar si la combinación coincide con el estilo de aprendizaje e inteligencia múltiple
                    if (combinacion.length == 2 && combinacion[0].equalsIgnoreCase(estiloAprendizaje)
                            && combinacion[1].equalsIgnoreCase(inteligenciaMultiple)) {
                        return recomendacion;
                    }
                }
            }
            return "No tengo una recomendación específica en este momento.";
        }

        private void enviarRecomendacionAlEstudiante(String estudiante, String recomendacion) {
            // Enviar las recomendaciones al estudiante
            ACLMessage mensaje = new ACLMessage(ACLMessage.INFORM);
            mensaje.addReceiver(getAID(estudiante));
            mensaje.setContent(recomendacion);
            send(mensaje);

            System.out.println("RecomendadorAgente: Enviando recomendación a " + estudiante + " - Te recomiendo: " + recomendacion);
        }
    }

    private List<String> leerActividadesDesdeArchivo(String rutaCompleta) {
        List<String> actividades = new ArrayList<>();

        try {
            File archivo = new File(rutaCompleta);
            Scanner scanner = new Scanner(archivo);

            while (scanner.hasNextLine()) {
                String actividad = scanner.nextLine();
                actividades.add(actividad);
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return actividades;
    }
}

