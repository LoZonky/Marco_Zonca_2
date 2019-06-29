package stanze;

public enum sporco {
 
	
	/*
	 * @param BASSA
	 * 			0.2g/m2
	 *  @param MEDIA
	 *  		1g/m2
	 *   @param ALTA
	 * 			1.5g/m2
	 */
	BASSA(0.2), MEDIA(1.0), ALTA(1.5);
	
	double vel_sporcizia;
	
	private sporco(double d) {
		this.vel_sporcizia=d;
	}
	
	public double getConstSporcizia() {
		return this.vel_sporcizia;
	}
	
	
	
	
}//end enum
