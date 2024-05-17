package TicTacToe;

import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;

public class Player_behaviour extends TickerBehaviour {

    public Player_behaviour(Agent a, long period) {
        super(a, period);
    }

    @Override
    protected void onTick() {
      System.out.println("Notice: ten seconds left ago");
    
    }
    
}
