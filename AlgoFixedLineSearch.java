
public class SDFixed extends SteepestDescent {

	private double alpha; // fixed step size
	
	// constructors
	public SDFixed () {
		
		super();
		this.alpha = 0.01;
	}
	public SDFixed (double eps, int maxIter, double x0, double alpha) {
		
		super(eps, maxIter, x0);
		this.alpha = alpha;
	}
	
	// getters
	public double getAlpha () {
		
		return this.alpha;
	}
	
	// setters
	public void setAlpha (double a) {
		
		this.alpha = a;
	}
	
	// other methods
	@Override
	public double lineSearch (Polynomial P, double [] x) {
		
		return this.alpha;
	}
	// get algorithm parameters from user
	@Override
	public boolean getParamsUser () {
		
		double alpha = Pro5_alalwank.getDouble("Enter fixed step size (0 to cancel): ", 0, Double.MAX_VALUE);
		
		if (alpha == 0) {
			
			System.out.println("\nProcess canceled. No changes made to algorithm parameters.\n");
			return false;
		} else {
			
			this.setHasResults(false);
		}
		
		double EPS = Pro5_alalwank.getDouble("Enter tolerance epsilon (0 to cancel): ", 0, Double.MAX_VALUE);
		
		if (EPS == 0) {
			
			System.out.println("\nProcess canceled. No changes made to algorithm parameters.\n");
			return false;
		} else {
			
			this.setHasResults(false);
		}
		
		int ITER = Pro5_alalwank.getInteger("Enter maximum number of iterations (0 to cancel): ", 0, 10000);
		
		if (ITER == 0) {
			
			System.out.println("\nProcess canceled. No changes made to algorithm parameters.\n");
			return false;
		} else {
			
			this.setHasResults(false);
		}
		
		double startingPoint = Pro5_alalwank.getDouble("Enter value for starting point (0 to cancel): ", -1 * Double.MAX_VALUE, Double.MAX_VALUE);
		
		if (startingPoint == 0) {
			
			System.out.println("\nProcess canceled. No changes made to algorithm parameters.\n");
			return false;
		} else {
			
			this.setHasResults(false);
		}
		
		// set the algorithm parameters for the fixed step size
		
		this.setAlpha(alpha);
		this.setEps(EPS);
		this.setMaxIter(ITER);
		this.setX0(startingPoint);
		
		System.out.println("\nAlgorithm parameters set!\n");
		
		return true;
	}
	// print parameters
	@Override
	public void print () {
		
		System.out.println("SD with a fixed line search:");
		System.out.println("Tolerance (epsilon): " + this.getEps());
		System.out.println("Maximum iterations: " + this.getMaxIter());
		System.out.println("Starting point (x0): " + this.getX0());
		System.out.println("Fixed step size (alpha): " + this.getAlpha());
		System.out.println();
	}
	
}
