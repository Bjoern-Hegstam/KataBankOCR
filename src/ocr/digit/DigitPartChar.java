package ocr.digit;

public enum DigitPartChar {
    VERTICAL('|'),
    HORIZONTAL('_');

    private final char character;

    DigitPartChar(char character) {
        this.character = character;
    }

    public char getCharacter() {
        return character;
    }

    @Override
    public String toString() {
        return Character.toString(character);
    }
}
