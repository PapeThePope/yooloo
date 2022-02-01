// History of Change
// vernr    |date  | who | lineno | what
//  V0.106  |200107| cic |    -   | add history of change

package server;

import common.YoolooKarte;
import common.YoolooKartenspiel;
import common.YoolooSpieler;
import common.YoolooStich;
import server.YoolooServer.GameMode;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class YoolooSession {

	private int anzahlSpielerInRunde;
	private GameMode gamemode = GameMode.GAMEMODE_NULL;
	private YoolooKarte[][] spielplan;
	private YoolooKartenspiel aktuellesSpiel;
	private YoolooStich[] ausgewerteteStiche;
	private List<YoolooKarte> playedCards = new ArrayList<>();
	private Logger sessionLogger;

	public YoolooSession(int anzahlSpielerInRunde) {
		super();
		this.anzahlSpielerInRunde = anzahlSpielerInRunde;
		gamemode = GameMode.GAMEMODE_NULL;
		spielplan = new YoolooKarte[YoolooKartenspiel.maxKartenWert][anzahlSpielerInRunde];
		ausgewerteteStiche = new YoolooStich[YoolooKartenspiel.maxKartenWert];
		for (int i = 0; i < ausgewerteteStiche.length; i++) {
			ausgewerteteStiche[i] = null;
		}
		aktuellesSpiel = new YoolooKartenspiel();
	}

	public YoolooSession(int anzahlSpielerInRunde, GameMode gamemode, Logger logger) {
		this(anzahlSpielerInRunde);
		this.gamemode = gamemode;
		sessionLogger = logger;
	}

	public synchronized void spieleKarteAus(int stichNummer, int spielerID, YoolooKarte karte) {
		spielplan[spielerID][stichNummer] = karte;
	}

	public synchronized YoolooStich stichFuerRundeAuswerten(int stichNummer) {
		sessionLogger.info("Stich wird ausgewertet");
		String cheater="";
		if (ausgewerteteStiche[stichNummer] == null) {
			YoolooStich neuerStich = null;
			YoolooKarte[] karten = spielplan[stichNummer];
			for (int spielernummer = 0; spielernummer < spielplan[stichNummer].length; spielernummer++) {
				if (spielplan[stichNummer][spielernummer] == null) {
					karten = null;
				}
			}
			if (karten != null) {
				sessionLogger.info("Neuer Stich wird erstellt");
				neuerStich = new YoolooStich(karten);
				for(YoolooKarte karte:karten){
					if(!playedCards.contains(karte)){
						playedCards.add(karte);
					}else{
						cheater = getPlayerNameFromCard(karte);
						System.out.println(cheater + " IS CHEATING");
						sessionLogger.info(cheater + " schummelt");
						karte.setWert(0);
					}
					if(karte.getWert()>10){
						cheater = getPlayerNameFromCard(karte);
						System.out.println("NICE TRY CHEATER- " + cheater + " - BUT NOT WITH ME AS RULE CHECKER, NOW FEEL MY POWER");
						sessionLogger.info(cheater + " hat geschummelt");
						karte.setWert(0);
					}
				}
				neuerStich.setStichNummer(stichNummer);
				neuerStich.setSpielerNummer(aktuellesSpiel.berechneGewinnerIndex(karten));
				sessionLogger.info("Gewinner-Index wird berechnet für den" + stichNummer + ". Stich");
				ausgewerteteStiche[stichNummer] = neuerStich;
				System.out.println("Stich ausgewertet:" + neuerStich.toString());
			}
		}
		return ausgewerteteStiche[stichNummer];

	}

	private String getPlayerNameFromCard(YoolooKarte karte){
		List<YoolooSpieler> spieler=  this.getAktuellesSpiel().getSpielerliste();
		YoolooKartenspiel.Kartenfarbe color = karte.getFarbe();
		YoolooSpieler player = spieler.stream()
				.filter(card -> card.getSpielfarbe().equals(color))
				.findFirst()
				.get();

		return player.getName();
	}

	public YoolooKartenspiel getAktuellesSpiel() {
		return aktuellesSpiel;
	}

	public void setAktuellesSpiel(YoolooKartenspiel aktuellesSpiel) {
		this.aktuellesSpiel = aktuellesSpiel;
	}

	public int getAnzahlSpielerInRunde() {
		return anzahlSpielerInRunde;
	}

	public void setAnzahlSpielerInRunde(int anzahlSpielerInRunde) {
		this.anzahlSpielerInRunde = anzahlSpielerInRunde;
	}

	public GameMode getGamemode() {
		return gamemode;
	}

	public void setGamemode(GameMode gamemode) {
		sessionLogger.info("Spielmodus wird gesetzt");
		this.gamemode = gamemode;
	}

	public YoolooKarte[][] getSpielplan() {
		return spielplan;
	}

	public void setSpielplan(YoolooKarte[][] spielplan) {
		sessionLogger.info("Spielplan wird gesetzt");
		this.spielplan = spielplan;
	}

	public String getErgebnis() {
		sessionLogger.info("Ergebnis wird übergeben");
		// TODO mit Funktion fuellen
		String ergebnis = "Ergebnis:\n blabla";
		return ergebnis;
	}

}
