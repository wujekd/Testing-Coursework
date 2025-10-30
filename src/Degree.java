import java.util.List;

public class Degree {
	// Your additions/changes below this line
	
	// declare variables to hold the profiles
	Profile level5profile;
	Profile level6profile;

	public Degree(List<Grade> year2, List<Grade> year3) {
		
		// Throw if illegal constructor parameters
		if (year2 == null || year3 == null) {
			throw new IllegalArgumentException("Missing grade list");
		} else if (year2.size() < 4 || year3.size() < 4) {
			throw new IllegalArgumentException("Each list must contain at least 4 grades.");
		}
		if(failInGradeList(year2) || failInGradeList(year3)) {
			throw new IllegalArgumentException("Cannot generate a degree with failed grades.");
		}
		
		// is checks passed create and store profiles from corresponding lists
		this.level5profile = new Profile(year2);
		this.level6profile = new Profile(year3);
	}
	
	// helper - returns true if theres a failed grade in the list
	private boolean failInGradeList(List<Grade> list) {
		for(Grade g : list){
			if(g.classify() == Classification.Fail) return true;
		}
		return false;
	}
	

	public Classification classify() {
	    Classification c5 = level5profile.classify();
	    Classification c6 = level6profile.classify();
	    
	    // Compare levels and return a classification accordingly
	    int cmp = c5.compareTo(c6);
	    if (cmp == 0) return c5; // levels 5 and 6 are equal
	    if (cmp < 0 && Math.abs(cmp) < 2 && level6profile.isClear()) return c6; // level6 better, no more than 1 grade away and clear
	    if (cmp > 0 && Math.abs(cmp) < 2 && level5profile.isClear()) return c5; // level5 better, no more than 1 grade away and clear
	    return Classification.Discretion; // return Discretion if none of the previous conditions are met
	}
}