package TicTacToe;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class Player_Agent extends Agent {
    
    @Override
    protected void setup(){
        System.out.println("Game Started! Selcet any cell");
        
//        Player_behaviour P = new Player_behaviour();

    addBehaviour(new Player_behaviour(this, 10000));
    
    addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                
                ACLMessage aa = receive();
                if(aa!=null){
                String con = aa.getContent();
                System.out.println("The Content Message is:" + con);  
                System.out.println("The Sender Of Message is:" + aa.getSender());
                }
            }
        });
    
    
    ACLMessage acl = new ACLMessage();
        acl.addReceiver(new AID("AI",false));
        acl.setContent("Player will Play After you");
        acl.setPerformative(ACLMessage.INFORM);
        send(acl);
        
    
}
        @Override
    protected void takeDown(){
       System.out.println("Game Over"); 
}
}



