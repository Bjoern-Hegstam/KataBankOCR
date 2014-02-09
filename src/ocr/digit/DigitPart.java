package ocr.digit;

public enum DigitPart {
    TOP(0, 1, DigitPartChar.HORIZONTAL),
    TOP_RIGHT(1, 2, DigitPartChar.VERTICAL),
    BOTTOM_RIGHT(2, 2, DigitPartChar.VERTICAL),
    BOTTOM(2, 1, DigitPartChar.HORIZONTAL),
    BOTTOM_LEFT(2, 0, DigitPartChar.VERTICAL),
    TOP_LEFT(1, 0, DigitPartChar.VERTICAL),
    MIDDLE(1, 1, DigitPartChar.HORIZONTAL);

    private final int row;
    private final int column;
    private final char character;

    DigitPart(int row, int column, DigitPartChar digitChar) {
        this.row = row;
        this.column = column;
        this.character = digitChar.getCharacter();
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public char getCharacter() {
        return character;
    }

    @Override
    public String toString() {
        return Character.toString(character);
    }
}
