package ostacoli;

import eccezioni.DatoNulloException;
import eccezioni.SorpassoDimensioniStanzaException;
import prog.utili.*;

public abstract class ostacoloV2 implements Comparable<ostacoloV2> {
	
// CAMPI
	
	private String nome;
	private Centro centro;
	private Figura figura;
	private static int cont=0;
	private boolean utilizzato=false;
	
// COSTRUTTORI

	// Costruttore per i Rettangoli
	public ostacoloV2(String n, double x, double y, double b, double h) {
		this.figura=new Rettangolo(b, h);
		this.centro=new Centro(x,y);
		this.nome=n+cont++;
	}
	
	// Costruttore per i Quadrati e Cerchi
	public ostacoloV2(String n, double x, double y, double l, boolean b) {
		this.figura = b ? new Quadrato(l) : new Cerchio(l);
		this.centro=new Centro(x,y);
		this.nome=n+cont++; 
		}
	
// METODI
	
	
	public void trasla(double x, double y) {
		this.centro.trasla(x, y);
	}
	
	public double getArea() {
		return this.figura.getArea();
	}
	
	private boolean isRettangolo() {
		if (this.figura instanceof Rettangolo) return true;
		else return false;
	}
	
	/*
	 *  Controllo che l'ostacolo non superi le dimensioni della stanza 
	 *  
	 *  @param b = base della stanza
	 *  @param h = altezza della stanza
	 *  @param boolean x = selettore per calcolo spazio in altezza (y) oppure in larghezza (x)
	 *  
	 */
	public boolean superoArea(double b, double h) throws SorpassoDimensioniStanzaException {
		if (sorpassoX(b, h,false,0.0,0.0) || sorpassoY(b, h,false,0.0,0.0)) throw new SorpassoDimensioniStanzaException("L'ostacolo inserito supera le dimensioni della stanza");
		else return false;	
	}
	
	// VERFICA SE E' POSSIBILE EFFETTUARE LO SPOSTAMENTO
	public void sposta(double b,double h, double x, double y) throws SorpassoDimensioniStanzaException {
		if(sorpassoX(b,h,true,x,y) || sorpassoY(b,h,true,x,y)) throw new SorpassoDimensioniStanzaException("Lo spostamento non può essere effettuato");
		else this.centro.trasla(x, y);	
	}
	
	private boolean sorpasso(double b, double h,double x,double y, boolean ins,boolean spost) {
		double centro = ins ? this.centro.getValoreX() : this.centro.getValoreY();
		double cost = ins ? getSemiLatoX() : getSemiLatoY();
		double max = ins ? b : h ; 
		if(spost) centro += ins ? x : y;
		return ( (centro + cost) > max || (centro - cost) < 0);
	}
	
	private boolean sorpassoX(double b, double h,boolean spost, double x, double y) {
		if(spost) return sorpasso(b,h,x,y,true,true);
		else return sorpasso(b,h,x,y,true,false);
	}

	private boolean sorpassoY(double b, double h,boolean spost,double x,double y) {
		if(spost) return sorpasso(b,h,x,y,false,true);
		else return sorpasso(b,h,x,y,false,false);
	}
	
	private double getSemilato(boolean x) {
		if (isRettangolo()) return x ? ((Rettangolo)this.figura).getBase()/2.0 : ((Rettangolo)this.figura).getAltezza()/2.0;
		else if (this.figura instanceof Quadrato) return ((Quadrato)this.figura).getLato()/2.0;
		else return ((Cerchio)this.figura).getRaggio()/2.0;
	}
	
	
	public double getSemiLatoX() {
		return getSemilato(true);
	}
	
	public double getSemiLatoY() {
		return getSemilato(false);
	}
	
	public double getSemiLato() throws DatoNulloException {
		if (this.figura instanceof Quadrato) return ((Quadrato)this.figura).getLato()/2.0;
		else throw new DatoNulloException("");
	} 
	
	
	// Ottenere i campi privati della classe
	
	public String getNome() {
		return this.nome;
	}
	
	public double getCentroX() {
		return this.centro.getValoreX();
	}
	
	public double getCentroY() {
		return this.centro.getValoreY();
	}

	public void setUtilizzato() {
		this.utilizzato=true;
		
	}
	
	public void resetUtilizzato() {
		this.utilizzato=false;	
	}
	
	public boolean getUtilizzato() {
		return this.utilizzato;
	}

	
}
