package ocr.digit;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

public class DigitParser {
    public static List<Digit> parse(List<String> lines) {
        if (lines.size() < 3) {
            throw new IllegalArgumentException("Expected 3 or more lines, but was given " + lines.size());
        }

        final Set<Integer> lineLengths = lines.stream()
                                              .limit(3)
                                              .map(String::length)
                                              .collect(toSet());

        if (lineLengths.size() != 1) {
            throw new IllegalArgumentException("All lines must be of same length");
        }


        final int lineLength = lineLengths.iterator().next();
        final List<Digit> digits = new LinkedList<>();

        for (int columnOffset = 0; columnOffset < lineLength; columnOffset += 3) {
            final Digit.Builder builder = new Digit.Builder();

            for (DigitPart digitPart : DigitPart.values()) {
                final char character = lines.get(digitPart.getRow()).charAt(columnOffset + digitPart.getColumn());
                if (character == digitPart.getCharacter()) {
                    builder.add(digitPart);
                }
            }

            digits.add(builder.toDigit());
        }

        return digits;
    }
}
