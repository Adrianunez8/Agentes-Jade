package enaturev2;

//import jade.core.Agent;
//import java.util.ArrayList;
//import java.util.List;
//import jade.core.behaviours.OneShotBehaviour;
//import jade.core.behaviours.SequentialBehaviour;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Scanner;
//
//public class PerfilAgente extends Agent {
//
//    private Map<String, String[]> perfilesEstudiantes;
//
//    protected void setup() {
//        perfilesEstudiantes = leerDatosDesdeArchivo(System.getProperty("user.home") + File.separator + "Escritorio" + File.separator + "datos_estudiantes.txt");
//
//        SequentialBehaviour secuencia = new SequentialBehaviour();
//        secuencia.addSubBehaviour(new EnviarPerfilBehavior());
//        addBehaviour(secuencia);
//    }
//
//    private class EnviarPerfilBehavior extends OneShotBehaviour {
//        public void action() {
//            // Enviar información de perfil al agente recomendador
//            for (Map.Entry<String, String[]> entry : perfilesEstudiantes.entrySet()) {
//                String estudiante = entry.getKey();
//                String[] perfil = entry.getValue();
//
//                System.out.println("PerfilEstudiante: Enviando perfil a AgenteRecomendador - Estudiante: " + estudiante +
//                        ", EstiloAprendizaje: " + perfil[0] + ", InteligenciasMultiples: " + perfil[1]);
//
//                // Crear mensaje
//                String contenidoMensaje = perfil[0] + "," + perfil[1];
//                enviarMensajeAgenteRecomendador(estudiante, contenidoMensaje);
//
//                // Esperar un poco antes de enviar otro perfil (puedes ajustar esto según tus necesidades)
//                block(5000);  // Esperar 5 segundos
//            }
//        }
//
//        private void enviarMensajeAgenteRecomendador(String estudiante, String contenidoMensaje) {
//            jade.lang.acl.ACLMessage mensaje = new jade.lang.acl.ACLMessage(jade.lang.acl.ACLMessage.INFORM);
//            mensaje.addReceiver(new jade.core.AID("RecomendadorAgente", jade.core.AID.ISLOCALNAME));
//            mensaje.setContent(contenidoMensaje);
//            send(mensaje);
//        }
//    }
//
//    private Map<String, String[]> leerDatosDesdeArchivo(String rutaCompleta) {
//        Map<String, String[]> perfiles = new HashMap<>();
//
//        try {
//            File archivo = new File(rutaCompleta);
//            Scanner scanner = new Scanner(archivo);
//
//            while (scanner.hasNextLine()) {
//                String linea = scanner.nextLine();
//                String[] partes = linea.split(",");
//                if (partes.length == 3) {
//                    String estudiante = partes[0];
//                    String estiloAprendizaje = partes[1];
//                    String inteligenciasMultiples = partes[2];
//
//                    // Conectar los datos a las variables de inteligencias múltiples
//                    perfiles.put(estudiante, new String[]{estiloAprendizaje, inteligenciasMultiples});
//                }
//            }
//
//            scanner.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        return perfiles;
//    }
//}




///// vale con KNN

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.core.AID;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class PerfilAgente extends Agent {

    protected void setup() {
        addBehaviour(new EnviarDatosPerfilBehaviour());
    }

    private class EnviarDatosPerfilBehaviour extends OneShotBehaviour {
        public void action() {
            ArrayList<String> datosEstudiantes = leerDatosEstudiantesDesdeArchivo(System.getProperty("user.home") + File.separator + "Escritorio" + File.separator + "perfil_estudiante.arff");
            if (!datosEstudiantes.isEmpty()) {
                // Crear mensaje
                ACLMessage mensaje = new ACLMessage(ACLMessage.INFORM);
                mensaje.setContent(String.join(",", datosEstudiantes));

                // Definir destinatario
                AID idAgenteRecomendador = new AID("RecomendadorAgente", AID.ISLOCALNAME);
                mensaje.addReceiver(idAgenteRecomendador);

                // Enviar mensaje
                send(mensaje);
            } else {
                System.out.println("No se encontraron datos de estudiantes en el archivo.");
            }
        }

        private ArrayList<String> leerDatosEstudiantesDesdeArchivo(String rutaArchivo) {
            ArrayList<String> datosEstudiantes = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    datosEstudiantes.add(linea);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return datosEstudiantes;
        }
    }
}





