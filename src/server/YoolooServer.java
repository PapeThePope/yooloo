// History of Change
// vernr    |date  | who | lineno | what
//  V0.106  |200107| cic |    -   | add history of change 

package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import common.YoolooKartenspiel;
import modules.LeagueMode;
import modules.Matchup;

public class YoolooServer {

	private final Dictionary<String, Integer> hashtable = new Hashtable<>();
    // Server Standardwerte koennen ueber zweite Konstruktor modifiziert werden!
    private int port = 44137;
    private int spielerProRunde = 8; // min 1, max Anzahl definierte Farben in Enum YoolooKartenSpiel.KartenFarbe)
    private GameMode serverGameMode = GameMode.GAMEMODE_SINGLE_GAME;

    public GameMode getServerGameMode() {
        return serverGameMode;
    }

    private Matchup currentMatchup;

    public Matchup CurrentMatchup() {
        return currentMatchup;
    }

    public void setServerGameMode(GameMode serverGameMode) {
        this.serverGameMode = serverGameMode;
    }

    private ServerSocket serverSocket = null;
    private boolean serverAktiv = true;

    // private ArrayList<Thread> spielerThreads;
    private ArrayList<YoolooClientHandler> clientHandlerList;

    private ExecutorService spielerPool;

    /**
     * Serverseitig durch ClientHandler angebotenen SpielModi. Bedeutung der
     * einzelnen Codes siehe Inlinekommentare.
     * <p>
     * Derzeit nur Modus Play Single Game genutzt
     */
    public enum GameMode {
        GAMEMODE_NULL, // Spielmodus noch nicht definiert
        GAMEMODE_SINGLE_GAME, // Spielmodus: einfaches Spiel
        GAMEMODE_PLAY_ROUND_GAME, // noch nicht genutzt: Spielmodus: Eine Runde von Spielen
        GAMEMODE_PLAY_LIGA, // Spielmodus: Jeder gegen jeden
        GAMEMODE_PLAY_POKAL, // noch nicht genutzt: Spielmodus: KO System
        GAMEMODE_PLAY_POKAL_LL // noch nicht genutzt: Spielmodus: KO System mit Lucky Looser
    }

    ;

    public YoolooServer(int port, int spielerProRunde, GameMode gameMode) {
        this.port = port;
        this.spielerProRunde = spielerProRunde;
        this.serverGameMode = gameMode;
    }

    public void startServer(Logger logger) {
        try {
            // Init
            serverSocket = new ServerSocket(port);
            spielerPool = Executors.newCachedThreadPool();
            clientHandlerList = new ArrayList<YoolooClientHandler>();
            System.out.println("Server gestartet - warte auf Spieler");
            
            logger.log(Level.INFO, "Server wartet auf Spieler");
            
            while (serverAktiv) {
                Socket client = null;

                // Neue Spieler registrieren
                try {
                    client = serverSocket.accept();
                    YoolooClientHandler clientHandler = new YoolooClientHandler(this, client);
                    clientHandlerList.add(clientHandler);
                    System.out.println("[YoolooServer] Anzahl verbundene Spieler: " + clientHandlerList.size());
                } catch (IOException e) {
                    System.out.println("Client Verbindung gescheitert");
                    logger.severe("Client Verbindung gescheitert");
                    e.printStackTrace();
                }

                // Neue Session starten wenn ausreichend Spieler verbunden sind!
                if (clientHandlerList.size() >= Math.min(spielerProRunde,
                        YoolooKartenspiel.Kartenfarbe.values().length)) {
                    // Init Session
                    YoolooSession yoolooSession = new YoolooSession(clientHandlerList.size(), serverGameMode, logger);
                    
                    logger.info("Neue Session beginnt mit " + clientHandlerList.size() + " Spielern");
                    
                    if (serverGameMode == GameMode.GAMEMODE_SINGLE_GAME)
                        // Starte pro Client einen ClientHandlerTread
                    	logger.info("Einfaches Spiel wird erstellt");
                    	for (int i = 0; i < clientHandlerList.size(); i++) {
                            YoolooClientHandler ch = clientHandlerList.get(i);
                            ch.setHandlerID(i);
                            ch.joinSession(yoolooSession);
                            logger.info("Spieler tritt der Session bei");
                            spielerPool.execute(ch); // Start der ClientHandlerThread - Aufruf der Methode run()
                        }
                    if (serverGameMode == GameMode.GAMEMODE_PLAY_LIGA) {
                        for (int i = 0; i < clientHandlerList.size(); i++) {
                            YoolooClientHandler ch = clientHandlerList.get(i);
                            ch.setHandlerID(i);
                            ch.joinSession(yoolooSession);
                            LeagueMode liga = new LeagueMode(clientHandlerList, 0, logger);
                            logger.info("Ligamodus wird erstellt");
                            
                            for (Matchup matchup : liga.hinrunde.Matchups
                            ) {
                                currentMatchup = matchup;
                                spielerPool.execute(ch);
                            }
                            for (Matchup matchup : liga.rueckrunde.Matchups
                            ) {
                                currentMatchup = matchup;
                                spielerPool.execute(ch);
                            }
                        }
                    }
                    // nuechste Runde eroeffnen
                    clientHandlerList = new ArrayList<YoolooClientHandler>();
                    logger.info("Nächste Runde wird eröffnet");
                }
            }
        } catch (IOException e1) {
            System.out.println("ServerSocket nicht gebunden");
            logger.warning("ServerSocket nicht gebunden");
            serverAktiv = false;
            e1.printStackTrace();
        }

    }

	public void Insert(String name, int value){
		Integer prevValue = hashtable.get(name);
		if (prevValue == null)
			prevValue = 0;
		hashtable.put(name, prevValue + value);
	}
    // TODO Dummy zur Serverterminierung noch nicht funktional
    public void shutDownServer(int code, Logger logger) {
        if (code == 543210) {
            this.serverAktiv = false;
            System.out.println("Server wird beendet");
            logger.info("Server wird beendet");
            spielerPool.shutdown();
        } else {
            System.out.println("Servercode falsch");
            logger.warning("Servercode falsch");
        }
    }
}
