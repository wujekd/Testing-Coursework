import static org.junit.jupiter.api.Assertions.*;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GradeTest {

// req1 – TWO tests for inputs below and above the valid range for the constructor.

	@DisplayName("Constructor invalid boundaries")
	@ParameterizedTest
	@MethodSource("invalidConstructorParams")
	public void invalidBoudariesTest(int percent){
		assertThrows(IllegalArgumentException.class,
				() ->{
					new Grade(percent);
				});
	}
	private static Stream<Arguments> invalidConstructorParams(){
		return Stream.of(
				//Upper boundary and above
				Arguments.of(21),
				Arguments.of(233),
				// Lower boundary and below
				Arguments.of(0),
				Arguments.of(-44)
				);
	}
	
	
// req2 – ONE test for a valid input, checking that getPoints returns the right value.
	@DisplayName("Constructor valid boundaries")
	@ParameterizedTest
	@MethodSource("validConstructorParams")
	public void constructsAtValidBoundaries(int points) {
		Grade instance = new Grade(points);
		assertEquals(instance.getPoints(), points);
	}
	private static Stream<Arguments> validConstructorParams(){
		return Stream.of(
				Arguments.of(15),
				// Additional tests to cover the boundaries
				Arguments.of(1),
				Arguments.of(20));
	}
	
	
// req3 - five tests for classify using Classifications as equivalence classes
	@DisplayName("Classify Method")
	@ParameterizedTest
	@MethodSource("classifyBVAparams")
	public void classifyMethod(int boundary, Classification result, int stepsFromBoundary){
		
		Grade instance = new Grade(boundary);
		assertEquals(instance.classify(), result);
		
	}
	private static Stream<Arguments> classifyBVAparams(){
		return Stream.of(
				Arguments.of(1, Classification.First, 0),
				Arguments.of(5, Classification.UpperSecond, 0),
				Arguments.of(9, Classification.LowerSecond, 0),
				Arguments.of(13, Classification.Third, 0),
				Arguments.of(17, Classification.Fail, 0));
	}
	
	
// req4 - two tests  for inputs beyond and above the valid range for fromPercentage
	@DisplayName("fromPercentage Invalid Inputs")
	@ParameterizedTest
	@MethodSource("fromPercentageInvalidParams")
	public void fromPercentageThrowsInvalidInput(int percent) {
		assertThrows(IllegalArgumentException.class, 
				() -> {
					Grade.fromPercentage(percent);
				});
	}
	private static Stream<Arguments> fromPercentageInvalidParams(){
		return Stream.of(
				Arguments.of(-2),
				Arguments.of(101));
	}

	
// req5 - TWENTY tests for fromPercentage, using each point in the 20-point scale as an equivalence class.
	// int percentage -> int grade 1-20	
	
	@DisplayName("fromPercentage valid boundaries")
	@ParameterizedTest
	@MethodSource("fromPercentageParams")
	public void fromPercentage(int percent, int result) {
		
		Grade instance = Grade.fromPercentage(percent);
		assertEquals(instance.getPoints(), result);
	}
	private static Stream<Arguments> fromPercentageParams(){
		return Stream.of(
				Arguments.of(80,1),
				Arguments.of(76,2),
				Arguments.of(73,3),
				Arguments.of(70,4),
				Arguments.of(67,5),
				Arguments.of(65,6),
				Arguments.of(62,7),
				Arguments.of(60,8),
				Arguments.of(57,9),
				Arguments.of(55,10),
				Arguments.of(52,11),
				Arguments.of(50,12),
				Arguments.of(47,13),
				Arguments.of(45,14),
				Arguments.of(42,15),
				Arguments.of(40,16),
				Arguments.of(35,17),
				Arguments.of(30,18),
				Arguments.of(0, 19),
				Arguments.of(-1,20)
				);
	}
}