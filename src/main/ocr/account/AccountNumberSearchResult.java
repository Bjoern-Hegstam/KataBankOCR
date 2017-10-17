package ocr.account;

import java.util.Collections;
import java.util.Set;

public class AccountNumberSearchResult {
    private final AccountNumberStatus status;
    private final AccountNumber baseAccountNumber;
    private final Set<AccountNumber> possibleAccountNumbers;

    public AccountNumberSearchResult(AccountNumber baseAccountNumber, Set<AccountNumber> accountNumbers) {
        this.baseAccountNumber = baseAccountNumber;
        this.possibleAccountNumbers = accountNumbers;

        if (accountNumbers.size() == 0) {
            status = AccountNumberStatus.ILLEGIBLE;
        } else if (accountNumbers.size() > 1) {
            status = AccountNumberStatus.AMBIVALENT;
        } else {
            status = AccountNumberStatus.OK;
        }
    }

    public AccountNumberStatus getStatus() {
        return status;
    }

    public Set<AccountNumber> getPossibleAccountNumbers() {
        return Collections.unmodifiableSet(possibleAccountNumbers);
    }

    @Override
    public String toString() {
        if (status == AccountNumberStatus.OK) {
            return possibleAccountNumbers.iterator().next().toString();
        }

        final StringBuilder sb = new StringBuilder();
        sb.append(baseAccountNumber.getNumberString());
        sb.append(" ").append(status);

        if (status == AccountNumberStatus.AMBIVALENT) {
            sb.append(" ").append(AccountNumberStatus.AMBIVALENT).append(" ");
            sb.append("[");
            for (AccountNumber accountNumber : possibleAccountNumbers) {
                sb.append(String.format("'%s', ", accountNumber));
            }
            sb.setLength(sb.length() - 2); // Remove extra comma and space
            sb.append("]");
        }

        return sb.toString();
    }
}
