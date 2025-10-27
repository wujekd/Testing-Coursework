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

class ProfileTest {
	
// 1 - three tests for each way the input can be invalid
	// ways 1,2 Null and empty list cases
	@ParameterizedTest
	@NullSource
	@EmptySource
	void constructorThrowsOnNullOrEmpty(List<Grade> grades) {
	  assertThrows(IllegalArgumentException.class, () -> new Profile(grades));
	}
	// way 3 grade too low
	@DisplayName("Constructor invalid params")
	@ParameterizedTest
	@MethodSource("constructorInvalidParams")
	public void construtorThrowsOnInvalidParams(List<Grade> grades) {
		assertThrows(IllegalArgumentException.class,
				() -> {
					new Profile(grades);
				});
	}
	private static Stream<List<Grade>> constructorInvalidParams(){
		return Stream.of(
				List.of(new Grade(18)));
	}
	
// 2 - combination of classification x isClear as equivalence classes
	@DisplayName("Classification + isClear")
	@ParameterizedTest
	@MethodSource("classifyAndIsClearParams")
	public void classifyIsClear(List<Grade> grades, Classification classify, boolean isClear) {
		Profile instance = new Profile(grades);
		Assertions.assertAll(
				() -> assertEquals(instance.classify(), classify),
				() -> assertEquals(instance.isClear(), isClear)
				);
	}
	private static Stream<Arguments> classifyAndIsClearParams() {
		return Stream.of(
				Arguments.of(  // First + Clear
						List.of(new Grade(1),new Grade(1),new Grade(1),new Grade(1)),
						Classification.First,
						true),
				Arguments.of(  // First + not Clear
						List.of(new Grade(1), new Grade(15),new Grade(1), new Grade(15)),
						Classification.First,
						false),
				Arguments.of( // Upper Second + Clear
						List.of(new Grade(5),new Grade(5),new Grade(5),new Grade(5)),
						Classification.UpperSecond,
						true),
				Arguments.of( // Upper Second + not Clear
						List.of(new Grade(7), new Grade(15),new Grade(7), new Grade(15)),
						Classification.UpperSecond,
						false),
				Arguments.of( // Lower second (always clear)
						List.of(new Grade(9),new Grade(9),new Grade(9),new Grade(9)),
						Classification.LowerSecond,
						true),
				Arguments.of( // Third (always clear)
						List.of(new Grade(13),new Grade(13),new Grade(13),new Grade(13)),
						Classification.Third,
						true));
				
	}
}