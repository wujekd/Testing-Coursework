import java.util.List;

public class Degree {
	// Your additions/changes below this line
	Profile level5profile;
	Profile level6profile;

	public Degree(List<Grade> year2, List<Grade> year3) {
		
		if (year2 == null || year3 == null) {
			throw new IllegalArgumentException("Missing grade list");
		} else if (year2.size() < 4 || year3.size() < 4) {
			throw new IllegalArgumentException("Each list must contain at least 4 grades.");
		}
		if(failInGradeList(year2) || failInGradeList(year3)) {
			throw new IllegalArgumentException("Cannot generate a degree with failed grades.");
		}
		this.level5profile = new Profile(year2);
		this.level6profile = new Profile(year3);
	}
	private boolean failInGradeList(List<Grade> list) {
		for(Grade g : list){
			if(g.classify() == Classification.Fail) return true;
		}
		return false;
	}
		
	public Classification classify() {
	    Classification c5 = level5profile.classify();
	    Classification c6 = level6profile.classify();
	    int cmp = c5.compareTo(c6);

	    System.out.println("---");
	    System.out.println("level5: " + level5profile.classify() + " isclear: " + level5profile.isClear());
	    System.out.println("level6: " + level6profile.classify() +  " isclear " + level6profile.isClear());
	    System.out.println("cmp " + cmp);
	    
	    if (cmp == 0) return c5;
	    if (cmp < 0 && level6profile.isClear()) return c6; // level6 better or clear
	    if (cmp > 0 && level5profile.isClear()) return c5; // level5 better or clear
	    return Classification.Discretion;
	}
}