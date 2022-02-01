package modules;

import client.YoolooClient;
import common.YoolooKartenspiel;
import common.YoolooSpieler;
import server.YoolooServer;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import static server.YoolooServer.GameMode.GAMEMODE_PLAY_LIGA;

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
        StartRunde(hinrunde);
        StartRunde(rueckrunde);
        Finalize();
    }

    private void StartRunde(Ligarunde runde) {
        for (Matchup matchup : runde.Matchups
        ) {
            YoolooKartenspiel game = new YoolooKartenspiel();
            game.spielerRegistrieren(matchup.player);
            game.spielerRegistrieren(matchup.opponent);
            startLigaServer();
            startLigaClient();
            AddScore(matchup.player, matchup.opponent);
            System.out.println("Winner Hinrunde: " + FindWinner().getName());
        }
    }

    public void startLigaServer(){
        YoolooServer server = new YoolooServer(44137,2,GAMEMODE_PLAY_LIGA);
        server.startServer();
    }

    public void startLigaClient(){
        Scanner hostnameScanner = new Scanner(System.in);
        String hostname = hostnameScanner.nextLine();

        YoolooClient client1 = new YoolooClient(hostname, 44137);
        YoolooClient client2 = new YoolooClient(hostname, 44137);
        client1.startClient();
        client2.startClient();
    }

    private void Finalize(){
        System.out.println("Winner Total: " + FindWinner().getName());
        score.clear();
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


    private void AddScore(YoolooSpieler player, YoolooSpieler opponent) {
        score.merge(player, player.getPunkte(), Integer::sum);
        score.merge(opponent, opponent.getPunkte(), Integer::sum);
    }

}
