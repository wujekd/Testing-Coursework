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
	
	// Null and empty list cases (no MethodSource needed)
	@ParameterizedTest
	@NullSource
	@EmptySource
	void constructorThrowsOnNullOrEmpty(List<Grade> grades) {
	  assertThrows(IllegalArgumentException.class, () -> new Profile(grades));
	}

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
				Arguments.of(
						List.of(new Grade(1)),
						Classification.First,
						true),
				Arguments.of(
						List.of(new Grade(1), new Grade(15)),
						Classification.First,
						false),
				Arguments.of(
						List.of(new Grade(5)),
						Classification.UpperSecond,
						true),
				Arguments.of(
						List.of(new Grade(7), new Grade(15)),
						Classification.UpperSecond,
						false),
				Arguments.of(
						List.of(new Grade(9)),
						Classification.LowerSecond,
						true),
				Arguments.of(
						List.of(new Grade(13)),
						Classification.Third,
						true));
				
	}
}