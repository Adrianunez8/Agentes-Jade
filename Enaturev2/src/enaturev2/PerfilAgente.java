package enaturev2;

//import jade.core.Agent;
//import jade.core.behaviours.CyclicBehaviour;
//import jade.lang.acl.ACLMessage;
//
//import java.util.Scanner;
//
//public class PerfilAgente extends Agent {
//    private String estiloAprendizaje;
//    private String inteligenciaMultiple;
//    private String nombre;
//    private String edad;
//
//    protected void setup() {
//        addBehaviour(new IngresarDatosBehavior());
//    }
//
//    private class IngresarDatosBehavior extends CyclicBehaviour {
//        public void action() {
//            Scanner scanner = new Scanner(System.in);
//            System.out.println("*****Bienvenido al AGENTE SMA Enature****** ");
//            System.out.println(getLocalName() + ": Ingrese el nombre: ");
//            nombre = scanner.nextLine();
//
//            System.out.println(getLocalName() + ": Ingrese la edad: ");
//            edad = scanner.nextLine();
//
//            System.out.println(getLocalName() + ": Ingresa el estilo de aprendizaje (visual, auditivo, kinestesico): ");
//            estiloAprendizaje = scanner.nextLine();
//
//            System.out.println(getLocalName() + ": Ingresa la inteligencia múltiple (logico-matematica, linguistica, interpersonal): ");
//            inteligenciaMultiple = scanner.nextLine();
//
//            // Esperamos un momento para asegurarnos de que el agente Recomendador esté registrado
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//            enviarDatosAlRecomendador();
//
//            // Terminamos este comportamiento
//            myAgent.removeBehaviour(this);
//        }
//
//        private void enviarDatosAlRecomendador() {
//            ACLMessage mensaje = new ACLMessage(ACLMessage.REQUEST);
//            mensaje.setContent(estiloAprendizaje + "," + inteligenciaMultiple);
//            mensaje.addReceiver(getAID("RecomendadorAgente"));
//            send(mensaje);
//        }
//    }
//}

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.SequentialBehaviour;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class PerfilAgente extends Agent {

    private Map<String, String[]> perfilesEstudiantes;

    protected void setup() {
        perfilesEstudiantes = leerDatosDesdeArchivo(System.getProperty("user.home") + File.separator + "Escritorio" + File.separator + "datos_estudiantes.txt");

        SequentialBehaviour secuencia = new SequentialBehaviour();
        secuencia.addSubBehaviour(new EnviarPerfilBehavior());
        addBehaviour(secuencia);
    }

    private class EnviarPerfilBehavior extends OneShotBehaviour {
        public void action() {
            // Enviar información de perfil al agente recomendador
            for (Map.Entry<String, String[]> entry : perfilesEstudiantes.entrySet()) {
                String estudiante = entry.getKey();
                String[] perfil = entry.getValue();

                System.out.println("PerfilEstudiante: Enviando perfil a AgenteRecomendador - Estudiante: " + estudiante +
                        ", EstiloAprendizaje: " + perfil[0] + ", InteligenciasMultiples: " + perfil[1]);

                // Crear mensaje
                String contenidoMensaje = perfil[0] + "," + perfil[1];
                enviarMensajeAgenteRecomendador(estudiante, contenidoMensaje);

                // Esperar un poco antes de enviar otro perfil (puedes ajustar esto según tus necesidades)
                block(5000);  // Esperar 5 segundos
            }
        }

        private void enviarMensajeAgenteRecomendador(String estudiante, String contenidoMensaje) {
            jade.lang.acl.ACLMessage mensaje = new jade.lang.acl.ACLMessage(jade.lang.acl.ACLMessage.INFORM);
            mensaje.addReceiver(new jade.core.AID("RecomendadorAgente", jade.core.AID.ISLOCALNAME));
            mensaje.setContent(contenidoMensaje);
            send(mensaje);
        }
    }

    private Map<String, String[]> leerDatosDesdeArchivo(String rutaCompleta) {
        Map<String, String[]> perfiles = new HashMap<>();

        try {
            File archivo = new File(rutaCompleta);
            Scanner scanner = new Scanner(archivo);

            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine();
                String[] partes = linea.split(",");
                if (partes.length == 3) {
                    String estudiante = partes[0];
                    String estiloAprendizaje = partes[1];
                    String inteligenciasMultiples = partes[2];

                    // Conectar los datos a las variables de inteligencias múltiples
                    perfiles.put(estudiante, new String[]{estiloAprendizaje, inteligenciasMultiples});
                }
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return perfiles;
    }
}
