package ocr.digit;

import java.util.*;

import static ocr.digit.DigitPart.*;

public final class Digit {
    private static final char SPACE_CHAR = ' ';

    private static final Map<Integer, Digit> validDigits = new HashMap<>();

    static {
        validDigits.put(0, new Digit(TOP, TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT, BOTTOM));
        validDigits.put(1, new Digit(TOP_RIGHT, BOTTOM_RIGHT));
        validDigits.put(2, new Digit(TOP, TOP_RIGHT, MIDDLE, BOTTOM_LEFT, BOTTOM));
        validDigits.put(3, new Digit(TOP, TOP_RIGHT, MIDDLE, BOTTOM_RIGHT, BOTTOM));
        validDigits.put(4, new Digit(TOP_LEFT, MIDDLE, TOP_RIGHT, BOTTOM_RIGHT));
        validDigits.put(5, new Digit(TOP, TOP_LEFT, MIDDLE, BOTTOM_RIGHT, BOTTOM));
        validDigits.put(6, new Digit(TOP, TOP_LEFT, BOTTOM_LEFT, MIDDLE, BOTTOM_RIGHT, BOTTOM));
        validDigits.put(7, new Digit(TOP, TOP_RIGHT, BOTTOM_RIGHT));
        validDigits.put(8, new Digit(TOP, TOP_LEFT, TOP_RIGHT, MIDDLE, BOTTOM_LEFT, BOTTOM_RIGHT, BOTTOM));
        validDigits.put(9, new Digit(TOP_LEFT, TOP, TOP_RIGHT, MIDDLE, BOTTOM_RIGHT, BOTTOM));
    }

    private final Set<DigitPart> parts = new HashSet<>();

    public Digit(DigitPart... parts) {
        Collections.addAll(this.parts, parts);
    }

    private Digit(Set<DigitPart> parts) {
        this.parts.addAll(parts);
    }

    public static Digit fromInt(int number) {
        if (number < 0 || number > 9) {
            throw new IllegalArgumentException("Number must be in range [0-9]");
        }

        return validDigits.get(number);
    }

    public boolean contains(DigitPart part) {
        return parts.contains(part);
    }

    public Digit add(DigitPart part) {
        final Digit digit = new Digit(parts);
        digit.parts.add(part);
        return digit;
    }

    public Digit remove(DigitPart part) {
        final Digit digit = new Digit(parts);
        digit.parts.remove(part);
        return digit;
    }

    @Override
    public int hashCode() {
        return parts.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return other != null && other instanceof Digit && parts.equals(((Digit) other).parts);

    }

    public boolean isValid() {
        return validDigits.containsValue(this);
    }

    public int toInt() {
        for (Map.Entry<Integer, Digit> entry : validDigits.entrySet()) {
            if (entry.getValue().equals(this)) {
                return entry.getKey();
            }
        }

        throw new DigitConversionException("Digit does not represent an integer");
    }

    @Override
    public String toString() {
        final char[][] characters = new char[3][];
        for (int i = 0; i < characters.length; i++) {
            characters[i] = new char[]{SPACE_CHAR, SPACE_CHAR, SPACE_CHAR};
        }

        for (DigitPart part : parts) {
            characters[part.getRow()][part.getColumn()] = part.getCharacter();
        }

        final StringBuilder sb = new StringBuilder();
        for (char[] row : characters) {
            sb.append(row).append("\n");
        }
        return sb.toString();
    }

    public static class Builder {
        private final Set<DigitPart> parts = new HashSet<>();

        public Builder add(DigitPart part) {
            parts.add(part);
            return this;
        }

        public Digit toDigit() {
            return new Digit(parts);
        }
    }
}