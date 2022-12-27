
public class Polynomial {

	// no. of variables
	private int n;
	// degree of polynomial
	private int degree;
	// coefficients
	private double[][] coefs; //index gives the power on the parameter
	
	// constructors
	public Polynomial() {
		
		n = 0;
		degree = 0;
		coefs = new double[0][0];
	}
	public Polynomial(int n, int degree, double[][] coefs) {
		
		this.n = n;
		this.degree = degree;
		this.coefs = coefs;
	}

	// getters
	public int getN() {
		
		return this.n;
	}
	public int getDegree() {
		
		return this.degree;
	}
	public double [][] getCoefs() {
		
		return this.coefs;
	}
	
	// setters
	public void setN(int a) {
		
		this.n = a;
	}
	public void setDegree(int a) {
		
		this.degree = a;
	}
	public void setCoef(int j, int d, double a) {
		
		this.coefs[j][d] = a;
	}

	// other methods
	
	// initialize member arrays to correct size
	public void init () {
		
		this.coefs = new double[this.n][this.degree + 1];
	}
	// calculate function value at point x
	public double f(double[] x)	{
		
		double result = 0;
		//create a pointer to the coefficients array
		double[][] c = this.coefs;
		
		for (int i = 0; i < this.n; i++) {
			
			for (int j = 0; j < this.degree + 1; j++) {
	
				result += ((c[i][j]) * Math.pow(x[i], j));
			}
		}
		
		return result;
	}
	// calculate gradient at point x
	public double[] gradient(double[] x) {
		
		double[] gradient = new double[this.n];
		
		//create an array for the coefficients of the gradient
		double[][] derivative = new double[this.n][this.degree];
		
		//find the gradient coefficients
		for (int i = 0; i < this.n; i++) {
			
			for (int j = 1; j < this.degree + 1; j++) {
				
				derivative[i][j - 1] = (this.coefs[i][j] * j);
			}
		}
		
		//evaluate each row at the corresponding point x
		for (int i = 0; i < this.n; i++) {
			
			double temp = 0;
			
			for (int j = 0; j < this.degree; j++) {
				
				temp += ((derivative[i][j]) * Math.pow(x[i], j));
			}
			
			gradient[i] = temp;
		}
		
		return gradient;
	}
	// calculate norm of gradient at point x
	public double gradientNorm(double[] x) {
		
		double[] gradient = new double[this.n];
		
		//create an array for the coefficients of the gradient
		double[][] derivative = new double[this.n][this.degree];
		
		//find the gradient coefficients
		for (int i = 0; i < this.n; i++) {
			
			for (int j = 1; j < this.degree + 1; j++) {
				
				derivative[i][j - 1] = (this.coefs[i][j] * j);
			}
		}
		
		//evaluate each row at the corresponding point x
		for (int i = 0; i < this.n; i++) {
			
			double temp = 0;
			
			for (int j = 0; j < this.degree; j++) {
				
				temp += -1 * ((derivative[i][j]) * Math.pow(x[i], j));
			}
			
			gradient[i] = temp;
		}
		
		double result = 0, sum = 0;
		
		for (int i = 0; i < this.n; i++) {
			
			sum += Math.pow(gradient[i], 2);
		}
		
		result = Math.sqrt(sum);
		
		return result;
	}
	// indicate whether polynomial is set
	public boolean isSet() {
		
		if (this.n != 0 && this.degree != 0) {
			
			return false;
		} else {
			
			return true;
		}
	}
	// print out the polynomial
	public void print () {
		
		System.out.print("f(x) = ");
		
		for (int i = 0; i < this.n; i++) {
			
			System.out.print("( ");
			
			for (int j = this.degree; j >= 0; j--) {
				
				if (j == 0) {
					
					System.out.printf("%.2f ", this.coefs[i][j]);
				}
				
				if (/*this.coefs[i][j] != 0 &&*/ j != 0) {
					
					System.out.printf("%.2fx%d^%d + ", this.coefs[i][j], i+1, j);
				}
			}
			
			if (i == this.n - 1) {
				
				System.out.print(")");
			} else {
				
				System.out.print(") + ");
			}
		}
		System.out.println();
	}
}
