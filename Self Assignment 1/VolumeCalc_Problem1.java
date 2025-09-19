/*the following code calculates the volumes of the Sun and the Earth, knowing their respective diameters and treating both as spheres.
 * The volume of a sphere is calculated: V=(4/3)*pi*r^3
*/
public class VolumeCalc_Problem1 {
	public static void main (String[] args) 
	{
		final double sunDiameter = 865000; //miles
		final double earthDiameter = 7600; //miles
		
		double sunRad = sunDiameter/2; //radius=diameter/2
		double earthRad = earthDiameter/2;//radius=diameter/2
		
		double sunVol=0;
		double earthVol=0;
		double ratioVol = 0;
		

		//calculates the volume with the formula for a sphere V=(4/3)*pi*r^3
		sunVol = (4*Math.PI*Math.pow(sunRad, 3))/3; //the Math.pow returns the the 1st argument to the power of the second argument
		earthVol = (4*Math.PI*Math.pow(earthRad, 3))/3;
	
		//calculates the ratio of the Sun Volume to the Earth Volume
		ratioVol = sunVol/earthVol; 
		
		//print results
		System.out.println("The volume of the the Earth is "+ earthVol + " cubic miles, the volume of the sun is " + sunVol+ 
				" cubic miles,\n and the ratio of the volume of the Sun to the volumen of the Earth is "
				+ ratioVol);	
	}

}
