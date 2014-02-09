package ocr.account;

import ocr.digit.Digit;
import ocr.digit.DigitPart;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AccountNumberSearcher {
    /**
     * Searches for all valid AccountNumbers that can be obtained by adding or removing a single
     * {@link DigitPart} from the digits that make up the account number.
     */
    public static AccountNumberSearchResult searchForValid(AccountNumber start) {
        final Set<AccountNumber> possibleAccountNumbers = new HashSet<>();

        final List<Digit> initialDigits = start.getDigits();
        for (int i = 0; i < initialDigits.size(); i++) {
            final List<Digit> validDigits = getValidDigits(initialDigits.get(i));
            for (Digit validDigit : validDigits) {
                final List<Digit> digits = replace(initialDigits, i, validDigit);
                final AccountNumber accountNumber = new AccountNumber(digits);
                if (accountNumber.getStatus() == AccountNumberStatus.OK) {
                    possibleAccountNumbers.add(accountNumber);
                }
            }
        }

        return new AccountNumberSearchResult(start, possibleAccountNumbers);
    }

    private static List<Digit> getValidDigits(Digit digit) {
        final List<Digit> validDigits = new ArrayList<>();

        for (DigitPart part : DigitPart.values()) {
            final Digit newDigit;
            if (digit.contains(part)) {
                newDigit = digit.remove(part);
            } else {
                newDigit = digit.add(part);
            }

            if (newDigit.isValid()) {
                validDigits.add(newDigit);
            }
        }

        if (digit.isValid()) {
            validDigits.add(digit);
        }

        return validDigits;
    }

    private static List<Digit> replace(List<Digit> initialDigits, int index, Digit validDigit) {
        final List<Digit> newDigits = new ArrayList<>();
        newDigits.addAll(initialDigits.subList(0, index));
        newDigits.add(validDigit);
        newDigits.addAll(initialDigits.subList(index + 1, initialDigits.size()));
        return newDigits;
    }
}
