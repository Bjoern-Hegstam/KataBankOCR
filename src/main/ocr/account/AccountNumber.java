package ocr.account;

import ocr.digit.Digit;

import java.util.Collections;
import java.util.List;

public class AccountNumber {
    private final AccountNumberStatus status;
    private final List<Digit> digits;

    public AccountNumber(List<Digit> digits) {
        this.digits = digits;

        if (!isLegible()) {
            status = AccountNumberStatus.ILLEGIBLE;
        } else if (!hasValidChecksum()) {
            status = AccountNumberStatus.ERROR;
        } else {
            status = AccountNumberStatus.OK;
        }
    }

    public AccountNumberStatus getStatus() {
        return status;
    }

    public List<Digit> getDigits() {
        return Collections.unmodifiableList(digits);
    }

    private boolean isLegible() {
        for (Digit digit : digits) {
            if (!digit.isValid()) {
                return false;
            }
        }
        return true;
    }

    private boolean hasValidChecksum() {
        return checksum() % 11 == 0;
    }

    private int checksum() {
        int sum = 0;
        for (int i = 0; i < digits.size(); i++) {
            sum += (digits.size() - i) * digits.get(i).toInt();
        }
        return sum;
    }

    public String getNumberString() {
        return buildNumberString();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(buildNumberString());

        if (status != AccountNumberStatus.OK) {
            sb.append(" ").append(status);
        }

        return sb.toString();
    }

    private String buildNumberString() {
        final StringBuilder sb = new StringBuilder();
        for (Digit digit : digits) {
            if (digit.isValid()) {
                sb.append(digit.toInt());
            } else {
                sb.append("?");
            }
        }

        return sb.toString();
    }
}
