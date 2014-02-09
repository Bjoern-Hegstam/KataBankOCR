import ocr.digit.Digit;
import ocr.digit.DigitParser;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static junit.framework.Assert.assertEquals;

public class DigitParserTest {
    @Test
    public void parseSingleDigit() throws Exception {
        for (int number = 0; number < 10; number++) {
            Digit digit = Digit.fromInt(number);
            String[] lines = digit.toString().split("\n");

            List<Digit> parsedDigits = DigitParser.parse(Arrays.asList(lines));

            assertEquals(1, parsedDigits.size());
            assertEquals(digit, parsedDigits.get(0));
        }
    }

    @Test
    public void parseMultipleDigits() throws Exception {
        // given
        int[] numbers = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        List<String> lines = convertToLines(numbers);

        // when
        List<Digit> digits = DigitParser.parse(lines);

        // then
        for (int i = 0; i < digits.size(); i++) {
            assertEquals(Digit.fromInt(numbers[i]), digits.get(i));
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void parseNoLines() throws Exception {
        DigitParser.parse(Collections.<String>emptyList());
    }

    @Test(expected = IllegalArgumentException.class)
    public void parseLinesOfUnequalLength() throws Exception {
        List<String> lines = convertToLines(0, 1, 2);
        lines.set(0, lines.get(0).substring(0, 3));

        DigitParser.parse(lines);
    }

    private List<String> convertToLines(int... numbers) {
        String[] lines = new String[] {"", "", ""};
        for (int number : numbers) {
            Digit digit = Digit.fromInt(number);
            String[] digitParts = digit.toString().split("\n");
            for (int i = 0; i < digitParts.length; i++) {
                lines[i] += digitParts[i];
            }
        }
        return Arrays.asList(lines);
    }
}
