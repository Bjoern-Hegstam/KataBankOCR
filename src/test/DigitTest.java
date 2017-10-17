import ocr.digit.Digit;
import ocr.digit.DigitConversionException;
import org.junit.Test;

import static ocr.digit.DigitPart.*;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class DigitTest {
    @Test
    public void add() throws Exception {
        // given
        final Digit zero = Digit.fromInt(0);

        // when
        final Digit eight = zero.add(MIDDLE);

        // then
        assertEquals(Digit.fromInt(8), eight);
    }

    @Test
    public void remove() throws Exception {
        // given
        final Digit eight = Digit.fromInt(8);

        // when
        final Digit zero = eight.remove(MIDDLE);

        // then
        assertEquals(Digit.fromInt(0), zero);
    }

    @Test
    public void isValid_shouldBeTrue_forAllDefinedValidDigits() throws Exception {
        for (int number = 0; number < 10; number++) {
            assertTrue(Digit.fromInt(number).isValid());
        }
    }

    @Test
    public void toInt() throws Exception {
        for (int number = 0; number < 10; number++) {
            assertEquals(number, Digit.fromInt(number).toInt());
        }
    }

    @Test(expected = DigitConversionException.class)
    public void toInt_InvalidDigit() throws Exception {
        // given
        final Digit digit = new Digit(TOP);

        // when
        digit.toInt();
    }
}
