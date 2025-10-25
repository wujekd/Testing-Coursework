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
		return Stream.of(Arguments.of(
					List.of(new Grade(1),new Grade(1)),
					List.of(new Grade(4),new Grade(2),new Grade(2),new Grade(2),new Grade(2))
				));
	}
	
	@DisplayName("Constructor Fail in grades")
	@ParameterizedTest
	@MethodSource("failInGradesParams")
	public void failInGrades(List<Grade> gradesY2, List<Grade> gradesY3) {
		assertThrows(IllegalArgumentException.class,
				() -> {
					new Degree(gradesY2, gradesY3);
				});
	}
	private static Stream<Arguments> failInGradesParams(){
		return Stream.of(Arguments.of(
					List.of(new Grade(17),new Grade(17),new Grade(17),new Grade(17),new Grade(17)),
					List.of(new Grade(17),new Grade(17),new Grade(17),new Grade(17),new Grade(17))
				));
	};
	
	
// Classifications as equivalence classes
	@DisplayName("Classify method")
	@ParameterizedTest
	@MethodSource("classifyParams")
	public void classifiesCorrectly(List<Grade> gradesY2, List<Grade> gradesY3, Classification result) {
		Degree instance = new Degree(gradesY2, gradesY3);
//		System.out.println("degree: " instance.classify());
//		System.out.print("profile5: " + instance.level5profile.classify() + "isClear: " + instance.level5profile.isClear());
//		System.out.println(" profile6: " + instance.level6profile.classify() + "isClear: " + instance.level5profile.isClear());
		assertEquals(instance.classify(), result);
	}
	public static Stream<Arguments> classifyParams(){
		return Stream.of(
				Arguments.of( // Both have the same grade 
						List.of(new Grade(1),new Grade(1),new Grade(1),new Grade(4)),
						List.of(new Grade(1),new Grade(1),new Grade(1),new Grade(4)),
						Classification.First),
				Arguments.of( // Level 6 is better
						List.of(new Grade(9),new Grade(9),new Grade(9),new Grade(9)),
						List.of(new Grade(5),new Grade(5),new Grade(5),new Grade(5)),
						Classification.UpperSecond),
				Arguments.of( // Level 5 is better
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
						Classification.Discretion)
				);
	}
}