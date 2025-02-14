package calculator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class ConverterTest {

    private final Converter converter = new Converter();

    @DisplayName("',' 또는 ':'을 구분자로 가지는 문자열을 변환할 때, 숫자 리스트를 반환해야 한다.")
    @ParameterizedTest
    @ValueSource(strings = {
            "0,1,2",
            "0,1:2",
            "0:1:2"
    })
    void convert_withDefaultDelimiter(String input) {
        List<PositiveInteger> actual = converter.convert(input);
        assertThat(actual).containsExactly(
                new PositiveInteger(0),
                new PositiveInteger(1),
                new PositiveInteger(2)
        );
    }

    @DisplayName("null 또는 빈문자열을 전달하는 경우, 빈 리스트을 반환해야 한다.")
    @ParameterizedTest
    @NullAndEmptySource
    void convent_givenNullOrEmpty(String input) {
        List<PositiveInteger> actual = converter.convert(input);
        assertThat(actual).isEmpty();
    }

    @DisplayName("',' 또는 ':'이 아닌 구분자를 포함하는 문자열을 변환할 때, 예외가 발생해야 한다.")
    @Test
    void convert_givenNotContainingDefaultDelimiter() {
        String input = "1,2;3";
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> converter.convert(input));
    }

    @DisplayName("커스텀 구분자를 포함하는 문자열을 변환하는 경우, 숫자 리스트를 반환해야 한다.")
    @Test
    void convert_givenCustomDelimiter() {
        String input = "//;\n1;2;3";
        List<PositiveInteger> actual = converter.convert(input);
        assertThat(actual).containsExactly(
                new PositiveInteger(1),
                new PositiveInteger(2),
                new PositiveInteger(3)
        );
    }

    @DisplayName("음수를 포함하는 경우, 예외가 발생해야 한다.")
    @Test
    void convert_containingNegativeInt() {
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> converter.convert("1,-2:3"))
                .withMessage("음수를 전달할 수 없습니다.");
    }

}
