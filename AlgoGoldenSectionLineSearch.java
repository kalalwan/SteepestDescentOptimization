
public class SDGSS extends SteepestDescent {

	private final double _PHI_ = (1. + Math.sqrt(5))/2.;
	private double maxStep; // GSS max step
	private double minStep; // GSS minimum step
	private double delta; // GSS delta parameter
	
	// constructors
	public SDGSS () {
		
		super();
		this.maxStep = 1;
		this.minStep = 0.001;
		this.delta = 0.001;
	}
	public SDGSS (double eps, int maxIter, double x0, double maxStep, double minStep, double delta) {
		
		super(eps, maxIter, x0);
		this.maxStep = maxStep;
		this.minStep = minStep;
		this.delta = delta;
	}
	
	// getters
	public double getMaxStep () {
		
		return this.maxStep;
	}
	public double getMinStep () {
		
		return this.minStep;
	}
	public double getDelta () {
		
		return this.delta;
	}
	
	// setters
	public void setMaxStep (double a) {
		
		this.maxStep = a;
	}
	public void setMinStep (double a) {
		
		this.minStep = a;
	}
	public void setDelta (double a) {
		
		this.delta = a;
	}
	
	// other methods
	
	// step size from GSS
	@Override
	public double lineSearch (Polynomial P, double [] x) {
		
		// get the first c value 
		double c = this.getMinStep() + ((this.getMaxStep() - this.getMinStep()) / _PHI_);
		
		// return the recursive function GSS with appropriate arguments
		return GSS(this.getMinStep(), this.getMaxStep(), c, x, this.direction(P, x), P);
	}
	// get algorithm parameters from user
	@Override
	public boolean getParamsUser () {
		
		double MAX = Pro5_alalwank.getDouble("Enter GSS maximum step size (0 to cancel): ", 0, Double.MAX_VALUE);
		
		if (MAX == 0) {
			
			System.out.println("\nProcess canceled. No changes made to algorithm parameters.\n");
			return false;
		} else {
			
			this.setHasResults(false);
		}
		
		double MIN = Pro5_alalwank.getDouble("Enter GSS minimum step size (0 to cancel): ", 0, MAX);
		
		if (MIN == 0) {
			
			System.out.println("\nProcess canceled. No changes made to algorithm parameters.\n");
			return false;
		} else {
			
			this.setHasResults(false);
		}
		
		double DELTA = Pro5_alalwank.getDouble("Enter GSS delta (0 to cancel): ", 0, Double.MAX_VALUE);
		
		if (DELTA == 0) {
			
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
		} else {
			
			this.setHasResults(false);
		}
		
		// set the algorithm parameters
		this.setMaxStep(MAX);
		this.setMinStep(MIN);
		this.setDelta(DELTA);
		this.setEps(EPS);
		this.setMaxIter(ITER);
		this.setX0(startingPoint);
		
		System.out.println("\nAlgorithm parameters set!\n");
		
		return true;
	}
	// print parameters
	@Override
	public void print () {
		
		System.out.println("SD with a golden section line search:");
		System.out.println("Tolerance (epsilon): " + this.getEps());
		System.out.println("Maximum iterations: " + this.getMaxIter());
		System.out.println("Starting point (x0): " + this.getX0());
		System.out.println("GSS maximum step size: " + this.getMaxStep());
		System.out.println("GSS minimum step size: " + this.getMinStep());
		System.out.println("GSS delta: " + this.getDelta());
		System.out.println();
		
	}
 	// evaluate the
	private double GSS (double a, double b, double c, double [] x, double [] dir, Polynomial P) {
		
		// get f(a), f(b), and f(c)
		
		// calculate f(a) according to the function f(a) = f(x - a*[direction])
		double [] resA = new double [dir.length];
		
		for (int i = 0; i < dir.length; i++) {
			
			resA[i] = a * dir[i];
		}
		
		for (int i = 0; i < dir.length; i++) {
			
			resA[i] = x[i] + resA[i];
		}
		
		double fA = P.f(resA);
		
		// calculate f(b) according to the function f(a) = f(x - a*[direction])
		double [] resB = new double [dir.length];
		
		for (int i = 0; i < dir.length; i++) {
			
			resB[i] = b * dir[i];
		}
		
		for (int i = 0; i < dir.length; i++) {
			
			resB[i] = x[i] + resB[i];
		}
		
		double fB = P.f(resB);
		
		// calculate f(c) according to the function f(a) = f(x - a*[direction])
		double [] resC = new double [dir.length];
		
		for (int i = 0; i < dir.length; i++) {
			
			resC[i] = c * dir[i];
		}
		
		for (int i = 0; i < dir.length; i++) {
			
			resC[i] = x[i] + resC[i];
		}
		
		double fC = P.f(resC);
		
		// check the cases where the maximum step size doesn't capture the minimum
		
		if ((fC > fA) || (fC > fB)) {
			
			// check the case where the left end-point is larger than the right end-point
			if (fA >= fB) {
				
				return b;
			}
			
			if (fA < fB) {
				
				return a;
			}
		}
		
		
		// base case is when b - a < delta
		if (b - a < this.getDelta()) {
			
			return ((a + b) / 2);
		}
		
		// check to see which subinterval is larger
		
		// if the left subinterval is larger
		if ((Math.abs(c - a)) > (Math.abs(c - b))) {
			
			double y = a + ((c - a)/_PHI_);
			
			// calculate f(y) according to the function f(a) = f(x - a*[direction])
			double [] resY = new double [dir.length];
			
			for (int i = 0; i < dir.length; i++) {
				
				resY[i] = y * dir[i];
			}
			
			for (int i = 0; i < dir.length; i++) {
				
				resY[i] = x[i] + resY[i];
			}
			
			double fY = P.f(resY);
			
			// case #1 is when the value of the function at fC > fY
			if (fC > fY) {
				
				// calculate the new c value
				double cNew = a + (c - a) / _PHI_;
				// make x1 the new a value
				return GSS(a, c, cNew, x, dir, P);
			}
			// case #2 is when the value of the function at fC < fY
			if (fC < fY) {
				
				// calculate the new c value
				double cNew = y + (b - y) / _PHI_;
				// make x1 the new a value
				return GSS(y, b, cNew, x, dir, P);
			}
		}
		// if the right subinterval is larger
		else {
			
			double y = b - ((b - c)/_PHI_);
			
			// calculate f(y) according to the function f(a) = f(x - a*[direction])
			double [] resY = new double [dir.length];
			
			for (int i = 0; i < dir.length; i++) {
				
				resY[i] = y * dir[i];
			}
			
			for (int i = 0; i < dir.length; i++) {
				
				resY[i] = x[i] + resY[i];
			}
			
			double fY = P.f(resY);
			
			// case #1 is when the value of the function at fC > fY
			if (fC > fY) {
				
				// calculate the new c value
				double cNew = c + ((b - c) / _PHI_);
				// make x1 the new a value
				return GSS(c, b, cNew, x, dir, P);
			}
			// case #2 is when the value of the function at fC < fY
			if (fC < fY) {
				
				// calculate the new c value
				double cNew = a + ((y - a) / _PHI_);
				// make x1 the new a value
				return GSS(a, y, cNew, x, dir, P);
			}
		}
		
		return 0;
	}
}
