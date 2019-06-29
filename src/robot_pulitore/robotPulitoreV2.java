package robot_pulitore;

import java.util.ArrayList;
import java.util.Iterator;
import eccezioni.*;
import ostacoli.*;
import prog.io.FileInputManager;
import prog.utili.*;
import stanze.*;

public class robotPulitoreV2 {

//CAMPI
	
	private ArrayList<stanzaV2> listaStanze= new ArrayList<stanzaV2>();
	private final static String nome_robot="NeteMé";
	
//COSTRUTTORI
	
	public robotPulitoreV2() {}	
	
//METODI
	
	// Caricamente delle stanze, con i rispettivi ostacoli, da un file 
	public void caricaDaFile(String path) throws Exception {
		FileInputManager in= new FileInputManager(path);
		String righe, campi[],stanzaAppartenente = null;
		do {
			righe=in.readLine();
			if(righe!=null) {
				campi=righe.split(";");
				String scelta= campi[0];
				//System.out.println(scelta);
				
				// CREAZIONE DELLA STANZA
				if(scelta.compareTo("stanza")==0) { 
					
					// stanza;Salotto1;base;altezza;data_ultima_pulitura;livello_sporco
					
					sporco s = null;
					if(campi[5].compareTo("BASSA")==0) s=sporco.BASSA;
					else if(campi[5].compareTo("MEDIA")==0) s=sporco.MEDIA;
					else if(campi[5].compareTo("ALTA")==0) s=sporco.ALTA;
					else throw new Exception("SPORCO NON VALIDO... dovrei creare un'altra eccezzione ma sbatta");
					insStanza(new stanzaV2(campi[1], Double.parseDouble(campi[2]), Double.parseDouble(campi[3]), new Data(campi[4]), s));
					stanzaAppartenente=campi[1];
				}
				// CREAZIONE DELL'OSTACOLO, CON INSERIMENTO ALLA STANZA A CUI APPARTIENE
				else {
					
					// nome_ostacolo,Tipo_di_forma; posX ; posY; base/lato; altezza;
					
					//System.out.println(campi[0]+"|"+campi[1]+"|"+campi[2]+"|"+campi[3]+"|"+campi[4]+"|"+campi[5]);
					if(campi[1].compareTo("Rettangolo")==0) {
						insOstacolo(stanzaAppartenente, new ostacoloRettangolareV2(campi[0].toString(), Double.parseDouble(campi[2]), Double.parseDouble(campi[3]), Double.parseDouble(campi[4]), Double.parseDouble(campi[5])));
					}else if(campi[1].compareTo("Quadrato")==0) {
						insOstacolo(stanzaAppartenente, new ostacoloQuadratoV2(campi[0], Double.parseDouble(campi[2]), Double.parseDouble(campi[3]), Double.parseDouble(campi[4])));
					}else if(campi[1].compareTo("Cerchio")==0) {
						insOstacolo(stanzaAppartenente, new ostacoloCircolareV2(campi[0], Double.parseDouble(campi[2]), Double.parseDouble(campi[3]), Double.parseDouble(campi[4])));
					}else throw new Exception("OSTACOLO NON VALIDO... dovrei creare un'altra eccezzione ma sbatta");
					
				}
				
			}
			
		}while(righe!=null);
	}
	
	//INSERIMENTO DELLA STANZA
	public void insStanza(stanzaV2 s) throws StanzaGiaEsistenteException {
		for(stanzaV2 X: listaStanze) {
			if(X.getNome().compareTo(s.getNome())==0) throw new StanzaGiaEsistenteException("La stanza che si desidera inserire è già presente");
		}
		listaStanze.add(s);
	}
	
	// INSERIMENTO OSTACOLO ALLA STANZA
	public void insOstacolo(String nStanza, ostacoloV2 o) throws SorpassoDimensioniStanzaException, OstacoloGiaPresenteException, StanzaNonEsistenteException {
		for(stanzaV2 X: listaStanze) {
			if(X.getNome().compareTo(nStanza)==0) {
				X.insOstacolo(o);
				return; 
			}
		}
		throw new StanzaNonEsistenteException("La stanza passata per l'inserimento dell'ostacolo, non esiste");
	}
	
	// ELIMINAZIONE OSTACOLO 
	public void rimuoviOstacolo(String nOstacolo) throws OstacoloNonEsistenteException {
		boolean trovato=false;
		for(stanzaV2 X: listaStanze) {
			for(ostacoloV2 O: X.getListaOstacoli()) {
				if(O.getNome().compareTo(nOstacolo)==0) {
					X.eliminaOstacolo(O);
					O.resetUtilizzato();
					return;
				}
			}
		} 
		if(!trovato) throw new OstacoloNonEsistenteException("l'ostacolo che si desidera eliminare non è presente nelle stanze");		
	}
	
	//SPOSTA L'OSTACOLO
	public void spostaOstacolo(String nOstacolo, double x, double y) throws OstacoloNonEsistenteException, SorpassoDimensioniStanzaException {
		boolean trovato=false;
		for(stanzaV2 X: listaStanze) {
			for(ostacoloV2 O: X.getListaOstacoli()) {
				if(O.getNome().compareTo(nOstacolo)==0) {
					X.spostaOstacolo(O,x,y);
					return;
				}
			}
		} 
		if(!trovato) throw new OstacoloNonEsistenteException("l'ostacolo che si desidera eliminare non è presente nelle stanze");		
	}
	
	
	//STAMPA DELL'AREA DA PULIRE DELL'APPARTAMENTO
	public void getAreaPulizia() {
		double AreaToatale = 0;
		for(stanzaV2 x: listaStanze) {
			AreaToatale+=x.getAreaPulizia();
		}
		System.out.println("L'area totale da pulire è: "+AreaToatale);
	}
	
	//STAMPA DELLE SINGOLE AREE:  STANZA, OSTACOLO1, OSTACOLO2...
	public void StampaAreeStanze() {
		for(stanzaV2 x: listaStanze) {
			System.out.println("L'area della stanza "+x.getNome()+" è: "+x.getArea());
			System.out.println("Mentre i suoi ostacoli: ");
			// getAreaOstacoli mi restiuisce la stampa a video delle singole aree degli ostacoli appartenti alla stanza puntata da x
			x.getAreeOstacoli();
			System.out.println();
		}
	}
	
	// STAMPA ELENCO STANZE IN ORDINE DI DIMENSIONI DELLE AREE DA PULIRE
	public void StampaStanze() {
		stanzaV2[] a = new stanzaV2[listaStanze.size()];
		this.listaStanze.toArray(a);
		System.out.println("L'ordine delle stanze in ordine dell'area da pulire è: ");
		ordina(a,true);
		stampa(a,true);
		
		
	}
	
	private void stampa(stanzaV2[] a,boolean X) {
		for(int i=0; i<a.length;i++) {
			if(X) System.out.println("> "+a[i].getNome());
			else System.out.println("> "+a[i].getNome()+": "+a[i].getQuantitaSporco()+"g di sporco su "+a[i].getArea()+" m2");
		}
		System.out.print("\n");
	}

	// BUBBLESORT
	private static void ordina(stanzaV2 []a,boolean X) {

		stanzaV2 temp=null;
		boolean scambiato;
		do {
			scambiato = false;
			for(int i=0; i<a.length-1; i++) {
				if(X && a[i].getAreaPulizia()>a[i+1].getAreaPulizia()) {
					temp=a[i];
					a[i]=a[i+1];
					a[i+1]=temp;
					scambiato = true;
				}else if (!X && a[i].getQuantitaSporco()<a[i+1].getQuantitaSporco()) {
					temp=a[i];
					a[i]=a[i+1];
					a[i+1]=temp;
					scambiato = true;
				}
			}
			
		}while(scambiato);

	}
	
	
	// STAMPA DELLE STANZE CHE DEVONO ESSERE PULITE IN QUESTO MOMENTO + grammi DI SPORCO
	// (dalla più sporca alla più pulita)
	public void StampaStanzeSporche() {
		int i = 0;
		ArrayList<stanzaV2> temp= listaStanze;

	       Iterator<stanzaV2> iterator = temp.iterator();
	       while(iterator.hasNext()){
	    	   stanzaV2 s= iterator.next();
	    	   if(s.getQuantitaSporco()==0) iterator.remove();

	       }
		
		stanzaV2[] a = new stanzaV2[temp.size()];
		this.listaStanze.toArray(a);
		ordina(a,false);
		System.out.println("Le stanze che devono essere pulite sono: ");
		stampa(a,false);

	}
	
	

	public String getNomeRobot() {
		return robotPulitoreV2.nome_robot;
	}
}
