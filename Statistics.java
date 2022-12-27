
public class Statistics {

	// function for getting the average of elements in a double array
	public static double avgDouble (double [] x) {
		
		double avg = 0;
		
		for (int i = 0; i < x.length; i++) {
			
			avg += x[i];
		}
		
		avg = avg/(x.length);
		
		return avg;
	}
	
	// function for getting the average of elements in a long array
	public static double avgLong (long [] x) {
		
		double avg = 0;
		
		for (int i = 0; i < x.length; i++) {
			
			avg += (double)x[i];
		}
		
		avg = avg/(x.length);
		
		return avg;
	}
	
	// function for getting the average of elements in an integer array -> returns a double
	public static double avgInt (int [] x) {
		
		double avg = 0;
		
		for (int i = 0; i < x.length; i++) {
			
			avg += (double)x[i];
		}
		
		avg = avg/(x.length);
		
		return avg;
	}

	// function for calculating the standard deviation in a double array
	public static double stDevDouble (double [] x) {
		
		double result;
		
		// population average
		double avg = avgDouble(x);
		
		double sum = 0;
		
		for (int i = 0; i < x.length; i++) {
			
			sum += Math.pow(Math.abs(x[i] - avg), 2);
		}
		
		result = sum/(x.length - 1);
		
		return Math.sqrt(result);
		
	}
	
	// function for calculating the standard deviation in a long array
	public static double stDevLong (long [] x) {
		
		double result;
		
		//population average
		double avg = (double)avgLong(x);
		
		double sum = 0;
		
		for (int i = 0; i < x.length; i++) {
			
			sum += Math.pow(Math.abs((double)x[i] - avg), 2);
		}
		
		result = sum/(x.length - 1);
		
		return Math.sqrt(result);
	}
	
	// function for calculating the standard deviation in an integer array
	public static double stDevInt (int [] x) {
		
		double result;
		
		//population average
		double avg = (double)avgInt(x);
		
		double sum = 0;
		
		for (int i = 0; i < x.length; i++) {
			
			sum += Math.pow(Math.abs((double)x[i] - avg), 2);
		}
		
		result = sum/(x.length - 1);
		
		return Math.sqrt(result);
	}

	// function for calculating the minimum of a double array
	public static double minDouble (double [] x) {
		
		double temp = x[0];
		
		for (int i = 0; i < x.length; i++) {
			
			if (x[i] < temp) {
				
				temp = x[i];
			}
		}
		
		return temp;
	}
	
	// function for calculating the minimum of a long array
	public static int minLong (long [] x) {
		
		long temp = x[0];
		
		for (int i = 0; i < x.length; i++) {
			
			if (x[i] < temp) {
				
				temp = x[i];
			}
		}
		
		return (int)temp;
	}
	
	// function for calculating the minimum of an integer array
	public static int minInt (int [] x) {
		
		int temp = x[0];
		
		for (int i = 0; i < x.length; i++) {
			
			if (x[i] < temp) {
				
				temp = x[i];
			}
		}
		
		return temp;
	}
	
	// function for calculating the maximum of a double array
	public static double maxDouble (double [] x) {
		
		double temp = x[0];
		
		for (int i = 0; i < x.length; i++) {
			
			if (x[i] > temp) {
				
				temp = x[i];
			}
		}
		
		return temp;
	}
	
	// function for calculating the maximum of a long array
	public static int maxLong (long [] x) {
		
		long temp = x[0];
		
		for (int i = 0; i < x.length; i++) {
			
			if (x[i] > temp) {
				
				temp = x[i];
			}
		}
		
		return (int)temp;
	}
	
	// function for calculating the maximum of an integer array
	public static int maxInt (int [] x) {
		
		int temp = x[0];
		
		for (int i = 0; i < x.length; i++) {
			
			if (x[i] > temp) {
				
				temp = x[i];
			}
		}
		
		return temp;
	}
}
