package modules;

import common.YoolooKartenspiel;
import common.YoolooSpieler;

import java.util.List;

public class LeagueMode {
    private List<YoolooSpieler> players;
    private Ligarunde hinrunde;
    private Ligarunde rueckrunde;


    public LeagueMode(List<YoolooSpieler> InitialPlayers){
        for (YoolooSpieler player:InitialPlayers) {
            this.players.add(player);
        }
        hinrunde = new Ligarunde(players);
        rueckrunde = new Ligarunde(players);
    }
    public void StartLeague()
    {
        for (Matchup matchup: hinrunde.Matchups
             ) {
            YoolooKartenspiel game = new YoolooKartenspiel();
            game.spielerRegistrieren(matchup.player);
            game.spielerRegistrieren(matchup.opponent);

        }
    }

}
