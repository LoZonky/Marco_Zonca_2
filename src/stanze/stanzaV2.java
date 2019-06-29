package stanze;

import java.util.ArrayList;

import eccezioni.OstacoloGiaPresenteException;
import eccezioni.SorpassoDimensioniStanzaException;
import ostacoli.ostacoloV2;
import prog.utili.*;

public class stanzaV2 {

//CAMPI
	
	private String nomeStanza;
	private Data ultimaPulitura;
	private Rettangolo dimensioniStanza;
	private static int cont=0;
	private sporco velocitaSporco;
	private static final String nomeCostante= "stanza";

	
	ArrayList<ostacoloV2> listaOstacoli= new ArrayList<ostacoloV2>();
	
//COSTRUTTORI	
	
	public stanzaV2(String n, double b, double h, Data d,sporco s) {
		this.nomeStanza=n;
		this.dimensioniStanza=new Rettangolo(b,h);
		this.velocitaSporco=s;
		this.ultimaPulitura=d;
	}
	
	public stanzaV2(double b, double h,sporco s) {
		this.nomeStanza= nomeCostante+(cont++);
		this.dimensioniStanza=new Rettangolo(b, h);
		this.velocitaSporco=s;
		this.ultimaPulitura=new Data();
	}
	
//METODI	
	
	// INSERIMENTO OSTACOLO	
	public void insOstacolo(ostacoloV2 o) throws SorpassoDimensioniStanzaException, OstacoloGiaPresenteException {
		if (!o.superoArea(dimensioniStanza.getBase(), dimensioniStanza.getAltezza()) && controlloPresenza(o.getNome()) && !o.getUtilizzato()) {
			// Se non supero l'area della stanza e non vi sono altri ostacoli con lo stesso nome
			// posso aggiungere l'ostacolo passato
			listaOstacoli.add(o);
			o.setUtilizzato();
		}
	}
	
	
	private boolean controlloPresenza(String nome) throws OstacoloGiaPresenteException {
		boolean trovato=false;
				for(ostacoloV2 X: listaOstacoli) {
					if(X.getNome().compareTo(nome)==0) {
						throw new OstacoloGiaPresenteException("L'ostacolo che si desidera inserire è giò presente nella stanza");
					}
				}
	return !trovato;
}
	
	public void spostaOstacolo(ostacoloV2 o, double x, double y) throws SorpassoDimensioniStanzaException {
		o.sposta(dimensioniStanza.getBase(), dimensioniStanza.getAltezza(), x, y);
	}
	
	public void eliminaOstacolo(ostacoloV2 n) {
		this.listaOstacoli.remove(n);
	}

	public String getNome() {
		return this.nomeStanza;
	}
	
	public ArrayList<ostacoloV2> getListaOstacoli(){
		return this.listaOstacoli;
	}

	// OTTENERE I VALORI DELLE AREE DI PULIZIA DELLE VARIE STANZE	
	public double getAreaPulizia() {
		double area=this.dimensioniStanza.getArea();
		for(ostacoloV2 x: listaOstacoli) {
			area-=x.getArea();
		}
		return area;
	}


	public void getAreeOstacoli() {
		for(ostacoloV2 x: listaOstacoli) {
			System.out.println("> "+x.getNome()+": "+x.getArea());
		}
		
	}

   public double getArea() {
	   return this.dimensioniStanza.getArea();
   }

   public double getQuantitaSporco() {
	   return velocitaSporco.getConstSporcizia()*((this.ultimaPulitura.quantoManca(new Data())/this.getAreaPulizia()))*(this.ultimaPulitura.quantoManca(new Data()));
   }
   
   public Data getData() {
	   return this.ultimaPulitura;
   }

}//END CLASS stanzaV2
