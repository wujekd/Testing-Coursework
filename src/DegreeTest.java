import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EmptySource;

import org.junit.jupiter.api.Test;

class DegreeTest {

// Three ways in which inputs can be invalid:
	
	// #1 - empty constructor
	@DisplayName("Constructor null as argument") 
	@MethodSource("nullArgs")
	@ParameterizedTest
	public void construtorThrowsOnNullParameter(List<Grade> gradesY2, List<Grade> gradesY3){
		assertThrows(IllegalArgumentException.class,
				() -> new Degree(gradesY2, gradesY3));
	}
	private static Stream<Arguments> nullArgs() {
	    List<Grade> valid = List.of(
	        new Grade(1), new Grade(1), new Grade(1), new Grade(1), new Grade(1)
	    );
	    return Stream.of(
	        Arguments.of(null, valid),
	        Arguments.of(valid, null),
	        Arguments.of(null, null)
	    );
	}
	
	// #2 - list.size < 4
	@DisplayName("Constructor not enough grades") 
	@ParameterizedTest
	@MethodSource("constructorNotEnoughGrades")
	public void constructorNotEnoughGrades(List<Grade> gradesY2, List<Grade> gradesY3) {
		assertThrows(IllegalArgumentException.class,
				() -> {
					new Degree(gradesY2, gradesY3);
				});
	}
	private static Stream<Arguments> constructorNotEnoughGrades(){
		return Stream.of(
				Arguments.of(
					List.of(new Grade(1),new Grade(1)),
					List.of(new Grade(4),new Grade(2),new Grade(2),new Grade(2),new Grade(2))),
				Arguments.of(
						List.of(new Grade(4),new Grade(2),new Grade(2),new Grade(2),new Grade(2)),
						List.of(new Grade(1),new Grade(1)))
				);
	}
	
	// #3 - fail in grades
	@DisplayName("Constructor Fail in grades") // #3
	@ParameterizedTest
	@MethodSource("failInGradesParams")
	public void failInGrades(List<Grade> gradesY2, List<Grade> gradesY3) {
		assertThrows(IllegalArgumentException.class,
				() -> {
					new Degree(gradesY2, gradesY3);
				});
	}
	private static Stream<Arguments> failInGradesParams(){
		return Stream.of(
				Arguments.of(
					List.of(new Grade(17),new Grade(17),new Grade(17),new Grade(17),new Grade(17)),
					List.of(new Grade(17),new Grade(17),new Grade(17),new Grade(17),new Grade(17))),
				Arguments.of(
						List.of(new Grade(1),new Grade(1),new Grade(1),new Grade(1),new Grade(1)),
						List.of(new Grade(17),new Grade(17),new Grade(17),new Grade(17),new Grade(17)))
				);
	};
	
	
// Test the classify method with possible classification outputs as equivalence classes
// Additional tests added to cover all branches
	@DisplayName("Classify method")
	@ParameterizedTest
	@MethodSource("classifyParams")
	public void classifiesCorrectly(List<Grade> gradesY2, List<Grade> gradesY3, Classification result) {
		Degree instance = new Degree(gradesY2, gradesY3);
		assertEquals(instance.classify(), result);
	}
	public static Stream<Arguments> classifyParams(){
		return Stream.of(
				Arguments.of( // Both have the same grade 
						List.of(new Grade(1),new Grade(1),new Grade(1),new Grade(4)),
						List.of(new Grade(1),new Grade(1),new Grade(1),new Grade(4)),
						Classification.First),
				Arguments.of( // Level 6 is better - clear
						List.of(new Grade(9),new Grade(9),new Grade(9),new Grade(9)),
						List.of(new Grade(5),new Grade(5),new Grade(5),new Grade(5)),
						Classification.UpperSecond),
				Arguments.of( // Level 5 is better - clear
						List.of(new Grade(9),new Grade(9),new Grade(9),new Grade(9)),
						List.of(new Grade(13),new Grade(14),new Grade(14),new Grade(14)),
						Classification.LowerSecond),
				Arguments.of(
						List.of(new Grade(16),new Grade(14),new Grade(14),new Grade(14)),
						List.of(new Grade(14),new Grade(14),new Grade(14),new Grade(14)),
						Classification.Third),
				Arguments.of(
						List.of(new Grade(4),new Grade(4),new Grade(4),new Grade(16),new Grade(16)),
						List.of(new Grade(6),new Grade(6),new Grade(6),new Grade(16), new Grade(16)),
						Classification.Discretion),
				Arguments.of( // Level 6 is better - not clear
						List.of(new Grade(9),new Grade(9),new Grade(9),new Grade(9)),
						List.of(new Grade(5),new Grade(5),new Grade(5),new Grade(14),new Grade(14)),
						Classification.Discretion),
				Arguments.of( // Level 5 is better - not clear
						List.of(new Grade(9),new Grade(9),new Grade(9),new Grade(9)),
						List.of(new Grade(13),new Grade(14),new Grade(14),new Grade(14)),
						Classification.LowerSecond),
				// test if the grades are not more than 1 level apart
				Arguments.of( // Level 6 is better - grade 6 more than 1 marking away 
						List.of(new Grade(1),new Grade(1),new Grade(1),new Grade(1)),
						List.of(new Grade(13),new Grade(14),new Grade(14),new Grade(14)),
						Classification.Discretion),
				Arguments.of( // Level 5 is better - grade 6 more than 1 marking away 
						List.of(new Grade(13),new Grade(14),new Grade(14),new Grade(14)),
						List.of(new Grade(1),new Grade(1),new Grade(1),new Grade(1)),
						Classification.Discretion)
				);
	}
}