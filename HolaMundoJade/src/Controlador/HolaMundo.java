package Controlador;

import jade.core.Agent;


public class HolaMundo extends Agent {
    
    @Override
    public void setup(){
        System.out.println("Hola mundo te saluda el agente: "+ getLocalName());
}
   
}

