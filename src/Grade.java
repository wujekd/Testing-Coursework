

public class Grade {
	private final int points;

	public int getPoints() {
		return points;
	}

	public Grade(int p) throws IllegalArgumentException {
		// Check if the parameter passed to the constructor is within boundaries
		if(p<1 || p>20) 
			throw new IllegalArgumentException();
		// If checks pass assign the passed value to the instance variable
		points = p;
	}
	 
	// Your additions/changes below this line
	
	// Classification as specified in the MDX points - grades table
	public Classification classify() {
        if (points <= 4) return Classification.First;
        if (points <= 8) return Classification.UpperSecond;
        if (points <= 12) return Classification.LowerSecond;
        if (points <= 16) return Classification.Third;
        return Classification.Fail; // 17–20
	}
	
	// convert grade result in % to 1-20 point scale as specified by MDX uni grading table
	// return a new grade object with those points 
	public static Grade fromPercentage(int g) throws IllegalArgumentException {
		if (g < -1 || g > 100) {
			throw new IllegalArgumentException();
		}
		final int p;
        if      (g >= 80) p = 1;
        else if (g >= 75) p = 2;
        else if (g >= 71) p = 3;
        else if (g >= 70) p = 4;
        else if (g >= 67) p = 5;
        else if (g >= 64) p = 6;
        else if (g >= 61) p = 7;
        else if (g >= 60) p = 8;
        else if (g >= 57) p = 9;
        else if (g >= 54) p = 10;
        else if (g >= 51) p = 11;
        else if (g >= 50) p = 12;
        else if (g >= 47) p = 13;
        else if (g >= 44) p = 14;
        else if (g >= 41) p = 15;
        else if (g >= 40) p = 16;
        else if (g >= 35) p = 17;
        else if (g >= 30) p = 18;
        else if (g >= 0) p = 19;
        else              p = 20; // -1
        
        return new Grade(p);
	}

	// increase readability when grades are listed as test inputs
	@Override
	public String toString() {
	    return "grade: " + classify() + " |";
	}

	
}
