package modules;

import common.YoolooSpieler;

public class Matchup {
    public YoolooSpieler player;
    public YoolooSpieler opponent;

    public Matchup(YoolooSpieler player, YoolooSpieler opponent) {
        this.player = player;
        this.opponent = opponent;
    }
    public boolean PlayerInMatchup(YoolooSpieler player){
        return this.player == player || this.opponent == player;
    }

}
