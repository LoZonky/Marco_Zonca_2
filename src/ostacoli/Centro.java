package ostacoli;

public class Centro {
	private double x,y;
	
	public Centro(double x, double y) {
		this.x=x;
		this.y=y;
	}
	
	public double getValoreX() {
		return x;
	}
	
	public double getValoreY(){
		return y;
	}
	
	public void trasla(double x,double y) {
		this.x+=x;
		this.y+=y;
	}
}
