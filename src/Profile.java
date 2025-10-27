import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Profile {
	// Your additions/changes below this line
	private final List<Grade> grades; 

	public Profile(List<Grade> g) {
		
//		if (g.isEmpty() || g == null) {  // ERROR couldn't check for empty if null
		if (g == null || g.isEmpty()) {
		throw new IllegalArgumentException("List empty or null!");
		}
		for (Grade grade : g) {
			if (grade.classify() == Classification.Fail) {
				throw new IllegalArgumentException("Grade cannot be below pass");
			} 
		}
		this.grades = Collections.unmodifiableList(new ArrayList<>(g));
	}
	
	
	// Figure 2 (top row):
    // If ≥50% FIRST → FIRST
    // else if ≥50% UPPER_SECOND or above → UPPER_SECOND
    // else if ≥50% LOWER_SECOND or above → LOWER_SECOND
    // else → THIRD
    public Classification classify() {
        int n = grades.size();
        int first = 0, upperOrAbove = 0, lowerOrAbove = 0;

        for (Grade g : grades) {
            Classification c = g.classify();
            if (c == Classification.First) {
                first++;
                upperOrAbove++;
                lowerOrAbove++;
            } else if (c == Classification.UpperSecond) {
                upperOrAbove++;
                lowerOrAbove++;
            } else if (c == Classification.LowerSecond) {
                lowerOrAbove++;
            } // THIRD contributes to none of these thresholds
        }

        if (first * 2 >= n) return Classification.First;
        if (upperOrAbove * 2 >= n) return Classification.UpperSecond;
        if (lowerOrAbove * 2 >= n) return Classification.LowerSecond;
        return Classification.Third;
    }

 // Figure 2 (bottom row):
    // - If profile class is FIRST or UPPER_SECOND → clear iff THIRD grades ≤ 25% of total
    // - LOWER_SECOND and THIRD profiles are always clear
    public boolean isClear() {
        Classification pc = classify();
        if (pc == Classification.LowerSecond || pc == Classification.Third) {
            return true;
        }
        // count THIRD grades
        int thirds = 0;
        for (Grade g : grades) {
            if (g.classify() == Classification.Third) thirds++;
        }
        // "no more than 25%" → thirds / n ≤ 0.25  ⇒ 4 * thirds ≤ n
        return 4 * thirds <= grades.size();
    }
}