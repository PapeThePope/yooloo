package modules;

import common.YoolooKartenspiel;
import common.YoolooSpieler;

import java.util.HashMap;
import java.util.List;

public class LeagueMode {
    private List<YoolooSpieler> players;
    private Ligarunde hinrunde;
    private Ligarunde rueckrunde;
    private HashMap<YoolooSpieler, Integer> score;


    public LeagueMode(List<YoolooSpieler> InitialPlayers) {
        this.players.addAll(InitialPlayers);
        hinrunde = new Ligarunde(players);
        rueckrunde = new Ligarunde(players);
    }

    public void StartLeague() {
        for (Matchup matchup : hinrunde.Matchups
        ) {
            YoolooKartenspiel game = new YoolooKartenspiel();
            game.spielerRegistrieren(matchup.player);
            game.spielerRegistrieren(matchup.opponent);
            // starte Spiel
            //Ergebnisse zu score hinzufügen
            AddScore(null, 0, null, 0);
            System.out.println("Winner: " + FindWinner().getName());
        }
    }

    public YoolooSpieler FindWinner() {
        YoolooSpieler winner = null;
        int high = 0;
        for (YoolooSpieler player : players
        ) {
            if (score.get(player) > high) {
                high = score.get(player);
                winner = player;
            }
        }
        return winner;
    }


    private void AddScore(YoolooSpieler player, int playerScore, YoolooSpieler opponent, int opponentScore) {
        score.merge(player, playerScore, Integer::sum);
        score.merge(opponent, opponentScore, Integer::sum);
    }

}
