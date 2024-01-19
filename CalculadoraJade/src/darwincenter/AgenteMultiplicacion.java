package darwincenter;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class AgenteMultiplicacion extends Agent {

    @Override
    protected void setup() {
        addBehaviour(new MultiplicacionBehaviour());
    }

    private class MultiplicacionBehaviour extends CyclicBehaviour {
        @Override
        public void action() {
            MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
            ACLMessage mensaje = receive(mt);

            if (mensaje != null) {
                try {
                    // Obtener los operandos de la solicitud
                    int operando1 = Integer.parseInt(mensaje.getContent().split(",")[0]);
                    int operando2 = Integer.parseInt(mensaje.getContent().split(",")[1]);

                    // Realizar la operación de multiplicacion
                    int resultado = operando1 * operando2;

                    // Enviar el resultado de vuelta
                    ACLMessage respuesta = mensaje.createReply();
                    respuesta.setPerformative(ACLMessage.INFORM);
                    respuesta.setContent(Integer.toString(resultado));
                    send(respuesta);
                } catch (NumberFormatException e) {
                    // Manejar error de conversión
                    System.err.println("Error: Los operandos no son números enteros.");
                }
            } else {
                // Si no hay mensajes, bloquear el comportamiento
                block();
            }
        }
    }
}
