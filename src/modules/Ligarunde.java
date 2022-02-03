package modules;

import common.YoolooSpieler;

import java.util.ArrayList;
import java.util.List;

public class Ligarunde {
    public List<Matchup> Matchups;
    private List<YoolooSpieler> players;

    public Ligarunde(List<YoolooSpieler> players) {

        this.players = players;
        this.Matchups = new ArrayList<>();
        LoadMatchups();
    }
    public void LoadMatchups(){
        for (YoolooSpieler player:players) {
            while(!PlayerMatchupsComplete(player)){
                int random = new java.util.Random().nextInt(players.size());
                YoolooSpieler opponent = players.get(random);
                Matchup matchup = new Matchup(player, opponent);
                if(!MatchupExists(player, opponent))
                    Matchups.add(matchup);
            }
        }
    }
    public boolean PlayerMatchupsComplete(YoolooSpieler player){
        return MatchupsContain(player) == players.size() -1;
    }

    public int MatchupsContain(YoolooSpieler player){
        int count = 0;
        for (Matchup matchup: Matchups) {
            if (matchup.player == player || matchup.opponent == player){
                count++;
            }
        }
        return count;
    }

    public boolean MatchupExists(YoolooSpieler player, YoolooSpieler opponent){
        for (Matchup matchup:Matchups) {
            if(((player == matchup.player) || (player == matchup.opponent)) && ((opponent == matchup.player) || (opponent == matchup.opponent)))
            return true;
        }
       return false;
    }
}
