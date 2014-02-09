package ocr.digit;

import java.util.LinkedList;
import java.util.List;

public class DigitParser {
    public static List<Digit> parse(List<String> lines) {
        if (lines.size() < 3) {
            throw new IllegalArgumentException("Expected 3 or more lines, but was given " + lines.size());
        }

        final int lineLength = lines.get(0).length();
        if (lines.get(1).length() != lineLength || lines.get(2).length() != lineLength) {
            throw new IllegalArgumentException("All lines must be of same length");
        }

        final List<Digit> digits = new LinkedList<>();
        for (int startPos = 0; startPos < lineLength; startPos += 3) {
            final Digit.Builder builder = new Digit.Builder();

            for (DigitPart position : DigitPart.values()) {
                final char character = lines.get(position.getRow()).charAt(startPos + position.getColumn());
                if (character == position.getCharacter()) {
                    builder.add(position);
                }
            }

            digits.add(builder.toDigit());
        }

        return digits;
    }
}
