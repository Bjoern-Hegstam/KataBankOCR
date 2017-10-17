package ocr.account;

import ocr.digit.Digit;
import ocr.digit.DigitParser;

import java.util.List;

public class AccountNumberParser {
    public static AccountNumber parse(List<String> lines) {
        final List<Digit> digits = DigitParser.parse(lines);
        return new AccountNumber(digits);
    }
}
