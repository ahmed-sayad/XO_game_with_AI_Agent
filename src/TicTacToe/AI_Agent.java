package TicTacToe;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class AI_Agent extends Agent {
        @Override
    protected void setup(){
        System.out.println("Game Started! AI will Play After you");
        
        String global = getAID().getName();
        String local = getAID().getLocalName();
        
        System.out.println("The Local Name of AI Agent that playing aganest you is: " + local);
        System.out.println("The Global Name of AI Agent that playing aganest you is: " + global);
        
        
        
            addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                
                ACLMessage bb = receive();
                if(bb != null){
                String con1 = bb.getContent();
                System.out.println("The Content Message is:" + con1);  
                System.out.println("The Sender Of Message is:" + bb.getSender());
                }
            }
        });
            
        
        ACLMessage acl = new ACLMessage();
        acl.addReceiver(new AID("Player",false));
        acl.setContent("AI will play after you");
        acl.setPerformative(ACLMessage.INFORM);
        send(acl);
}
    @Override
    protected void takeDown(){
       System.out.println("Game Over! AI Player Stopped"); 
}
}
