package robot_pulitore;

import stanze.stanzaV2;
import eccezioni.OstacoloGiaPresenteException;
import eccezioni.SorpassoDimensioniStanzaException;
import eccezioni.StanzaGiaEsistenteException;
import eccezioni.StanzaNonEsistenteException;
import ostacoli.ostacoloCircolareV2;
import ostacoli.ostacoloQuadratoV2;
import ostacoli.ostacoloRettangolareV2;
import prog.utili.Data;
import stanze.sporco;

public class Main {
	public static void main(String[] args) throws Exception {
	System.out.println("AOOOOO");	
	
	robotPulitoreV2 MarkI = new robotPulitoreV2();
	System.out.println("Il robot pulitore si chiama: "+ MarkI.getNomeRobot());
	
	stanzaV2 primaStanza = new stanzaV2("Cucina", 10.7, 8.4,new Data("25.06.2019"), sporco.MEDIA);
	stanzaV2 secondaStanza = new stanzaV2("Camera",8.8,9.7,new Data("24.06.2019"), sporco.ALTA);
	stanzaV2 terzaStanza = new stanzaV2("Salotto",11.11,10.10,new Data("23.06.2019"),sporco.BASSA);
	stanzaV2 qStanza = new stanzaV2("sgabuzzino",2.0,3.0,new Data("29.06.2019"),sporco.ALTA);
	System.out.println(primaStanza.getNome());	
	
	
	MarkI.insStanza(primaStanza);
	MarkI.insStanza(secondaStanza);
    MarkI.insStanza(terzaStanza);
    MarkI.insStanza(qStanza);
	
	MarkI.getAreaPulizia();
	
	MarkI.insOstacolo("Camera", new ostacoloCircolareV2("Puff", 3.4, 4.8, 0.5));
	MarkI.insOstacolo("Camera", new ostacoloRettangolareV2("letto", 2.4, 1.4, 2.0, 0.7));
	MarkI.insOstacolo("Camera", new ostacoloQuadratoV2("Sedia", 7.4, 7.4, 0.3));
	MarkI.insOstacolo("Camera", new ostacoloRettangolareV2("Tavolo", 7.5, 7.8, 2.0, 0.5));
	
	
	MarkI.insOstacolo("Cucina", new ostacoloRettangolareV2("Piano cottura", 2.0, 0.2, 4.0, 0.4));
	MarkI.insOstacolo("Cucina", new ostacoloRettangolareV2("tavolo", 5.0, 5.2, 5.46, 4.97));
	
	MarkI.insOstacolo("Salotto", new ostacoloRettangolareV2("divano", 5.0, 5.2, 4.00, 1.0));
	MarkI.insOstacolo("Salotto", new ostacoloRettangolareV2("tavolo", 7.4, 7.5, 2.0, 0.6));
	MarkI.insOstacolo("Salotto", new ostacoloRettangolareV2("Mobile televisore", 1.5, 5.2, 0.6, 4.97));
	
	MarkI.getAreaPulizia();
	
	MarkI.StampaAreeStanze();
	
	
	System.out.println("Costante Pulizia: "+primaStanza.getQuantitaSporco());
	
	
	MarkI.caricaDaFile("data.txt");
	
	MarkI.spostaOstacolo("Piano cottura4", 0.1, 0.1);
	MarkI.rimuoviOstacolo("Sedia2");
	
	//MarkI.StampaAreeStanze();
	
	MarkI.StampaStanze();
	MarkI.StampaStanzeSporche();
	
	}
}

