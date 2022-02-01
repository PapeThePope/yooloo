package modules;

import common.YoolooKartenspiel;
import common.YoolooSpieler;
import server.YoolooClientHandler;

import java.util.*;

public class LeagueMode {
    private List<YoolooSpieler> players;
    public Ligarunde hinrunde;
    public Ligarunde rueckrunde;
    private List<YoolooClientHandler> clientHandlerList;


    public LeagueMode(List<YoolooSpieler> InitialPlayers) {
        this.players = new ArrayList<YoolooSpieler>();
        this.players.addAll(InitialPlayers);
        hinrunde = new Ligarunde(players);
        rueckrunde = new Ligarunde(players);
    }
    public LeagueMode(List<YoolooClientHandler> InitialPlayers, int i) {
        this.clientHandlerList = InitialPlayers;
        this.players = new ArrayList<YoolooSpieler>();
        InitialPlayers.forEach(yoolooClientHandler -> players.add(yoolooClientHandler.GetSpieler()));
        hinrunde = new Ligarunde(players);
        rueckrunde = new Ligarunde(players);
    }

    private static List<YoolooSpieler> TestSpieler  = new ArrayList<YoolooSpieler>() {{
        YoolooSpieler player = new YoolooSpieler("Maik", 10);
        player.setClientHandlerId(0);
        add(player);

        player = new YoolooSpieler("Tobias",10);
        player.setClientHandlerId(2);
        add(player);

        player = new YoolooSpieler("Jakob",10);
        player.setClientHandlerId(3);
        add(player);

        player = new YoolooSpieler("Matthias",10);
        player.setClientHandlerId(4);
        add(player);

        player = new YoolooSpieler("Jakub",10);
        player.setClientHandlerId(5);
        add(player);
    }};

    public static void main(String[] args){
        new LeagueMode(TestSpieler).StartSimulationLeague();
    }

    public void StartSimulationLeague() {
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
            game.spielerSortierungFestlegen();
            game.spieleRunden();
        }
    }

    private void PlayMatchup(Matchup m){

    }

    private void Finalize(){
        List<YoolooSpieler> yoolooSpielers = FindWinner();
        if (yoolooSpielers.size() == 1)
        System.out.println("Winner Liga: " + yoolooSpielers.get(0).getName() + " mit " + yoolooSpielers.get(0).getPunkte() + " Punkten");
        else {
            final String[] winners = {""};
            yoolooSpielers.forEach(yoolooSpieler -> winners[0] += yoolooSpielers.get(0).getName() + " und ");

            System.out.println("Winner Liga: GLEICHSTAND " + winners + " mit " + yoolooSpielers.get(0).getPunkte() + " Punkten");
        }
        System.out.println("Endstand:");
        players.sort(Comparator.reverseOrder());
        players.forEach(yoolooSpieler -> System.out.println(yoolooSpieler.getName()+" : " + yoolooSpieler.getPunkte()));
    }

    public List<YoolooSpieler> FindWinner() {
        List<YoolooSpieler> winner = new ArrayList<>();
        int high = 0;
        for (YoolooSpieler player : players
        ) {
            if(player.getPunkte() == high)
            {
                winner.add(player);
            }else
            if (player.getPunkte() > high) {
                high = player.getPunkte();
                winner.clear();
                winner.add(player);
            }
        }

        return winner;
    }


}
