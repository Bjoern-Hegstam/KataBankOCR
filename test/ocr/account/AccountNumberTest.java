package ocr.account;

import ocr.digit.Digit;
import ocr.digit.DigitPart;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;

public class AccountNumberTest {
    @Test
    public void getStatus_legibleWithInvalidChecksum() throws Exception {
        // given
        final List<Digit> digits = createDigitList(0, 1, 2);

        // when
        final AccountNumber number = new AccountNumber(digits);

        // then
        assertEquals(AccountNumberStatus.ERROR, number.getStatus());
    }

    @Test
    public void getStatus_withInvalidDigit() throws Exception {
        // given
        final List<Digit> digits = createDigitList(0, 1, 2);
        digits.add(new Digit(DigitPart.TOP));

        // when
        final AccountNumber number = new AccountNumber(digits);

        // then
        assertEquals(AccountNumberStatus.ILLEGIBLE, number.getStatus());
    }

    @Test
    public void getStatus_validAccountNumber() throws Exception {
        // given
        final List<Digit> digits = createDigitList(3, 4, 5, 8, 8, 2, 8, 6, 5);

        // when
        final AccountNumber number = new AccountNumber(digits);

        // then
        assertEquals(AccountNumberStatus.OK, number.getStatus());
    }

    @Test
    public void getStatus_invalidAccountNumber() throws Exception {
        // given
        final List<Digit> digits = createDigitList(3, 4, 5, 8, 8, 2, 8, 6, 4);

        // when
        final AccountNumber number = new AccountNumber(digits);

        // then
        assertEquals(AccountNumberStatus.ERROR, number.getStatus());
    }

    private List<Digit> createDigitList(int... numbers) {
        final List<Digit> digits = new ArrayList<>(numbers.length);
        for (int number : numbers) {
            digits.add(Digit.fromInt(number));
        }
        return digits;
    }
}
