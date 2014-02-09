package ocr.account;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class AccountNumberSearcherTest {
    @Test
    public void searchForValid_singleValidPossibility() throws Exception {
        // given
        final List<String> lines = new ArrayList<>();
        final List<String> accountNumbers = new ArrayList<>();
        lines.add("                           ");
        lines.add("  |  |  |  |  |  |  |  |  |");
        lines.add("  |  |  |  |  |  |  |  |  |");
        lines.add("                           ");
        accountNumbers.add("711111111");
        lines.add(" _  _  _  _  _  _  _  _  _ ");
        lines.add("  |  |  |  |  |  |  |  |  |");
        lines.add("  |  |  |  |  |  |  |  |  |");
        lines.add("                           ");
        accountNumbers.add("777777177");
        lines.add(" _  _  _  _  _  _  _  _  _ ");
        lines.add(" _|| || || || || || || || |");
        lines.add("|_ |_||_||_||_||_||_||_||_|");
        lines.add("                           ");
        accountNumbers.add("200800000");
        lines.add(" _  _  _  _  _  _  _  _  _ ");
        lines.add(" _| _| _| _| _| _| _| _| _|");
        lines.add(" _| _| _| _| _| _| _| _| _|");
        lines.add("                           ");
        accountNumbers.add("333393333");
        lines.add("    _  _     _  _  _  _  _ ");
        lines.add(" _| _| _||_||_ |_   ||_||_|");
        lines.add("  ||_  _|  | _||_|  ||_| _|");
        lines.add("                           ");
        accountNumbers.add("123456789");
        lines.add(" _     _  _  _  _  _  _    ");
        lines.add("| || || || || || || ||_   |");
        lines.add("|_||_||_||_||_||_||_| _|  |");
        lines.add("                           ");
        accountNumbers.add("000000051");
        lines.add("    _  _  _  _  _  _     _ ");
        lines.add("|_||_|| ||_||_   |  |  | _ ");
        lines.add("  | _||_||_||_|  |  |  | _|");
        lines.add("                           ");
        accountNumbers.add("490867715");

        for (int i = 0; i < accountNumbers.size(); i++) {
            // when
            final AccountNumber invalidNumber = AccountNumberParser.parse(lines.subList(i * 4, (i + 1) * 4));
            final AccountNumberSearchResult validNumber = AccountNumberSearcher.searchForValid(invalidNumber);

            // then
            assertEquals(AccountNumberStatus.OK, validNumber.getStatus());
            assertEquals(accountNumbers.get(i), validNumber.toString());
        }
    }

    @Test
    public void searchForValid_ambivalentAccountNumbers() throws Exception {
        // given
        final List<String> lines = new ArrayList<>();
        final List<String> accountNumbers = new ArrayList<>();
        final List<Set<String>> possibleAccountNumbers = new ArrayList<>();
        lines.add(" _  _  _  _  _  _  _  _  _ ");
        lines.add("|_||_||_||_||_||_||_||_||_|");
        lines.add("|_||_||_||_||_||_||_||_||_|");
        lines.add("                           ");
        accountNumbers.add("888888888");
        possibleAccountNumbers.add(createSet("888886888", "888888988", "888888880"));
        lines.add(" _  _  _  _  _  _  _  _  _ ");
        lines.add("|_ |_ |_ |_ |_ |_ |_ |_ |_ ");
        lines.add(" _| _| _| _| _| _| _| _| _|");
        lines.add("                           ");
        accountNumbers.add("555555555");
        possibleAccountNumbers.add(createSet("559555555", "555655555"));
        lines.add(" _  _  _  _  _  _  _  _  _ ");
        lines.add("|_ |_ |_ |_ |_ |_ |_ |_ |_ ");
        lines.add("|_||_||_||_||_||_||_||_||_|");
        lines.add("                           ");
        accountNumbers.add("666666666");
        possibleAccountNumbers.add(createSet("686666666", "666566666"));
        lines.add(" _  _  _  _  _  _  _  _  _ ");
        lines.add("|_||_||_||_||_||_||_||_||_|");
        lines.add(" _| _| _| _| _| _| _| _| _|");
        lines.add("                           ");
        accountNumbers.add("999999999");
        possibleAccountNumbers.add(createSet("899999999", "993999999", "999959999"));
        lines.add("    _  _  _  _  _  _     _ ");
        lines.add("|_||_|| || ||_   |  |  ||_ ");
        lines.add("  | _||_||_||_|  |  |  | _|");
        lines.add("                           ");
        accountNumbers.add("490067715");
        possibleAccountNumbers.add(createSet("490867715", "490067115", "490067719"));

        for (int i = 0; i < accountNumbers.size(); i++) {
            // when
            final AccountNumber invalidNumber = AccountNumberParser.parse(lines.subList(i * 4, (i + 1) * 4));
            final AccountNumberSearchResult ambivalentNumber = AccountNumberSearcher.searchForValid(invalidNumber);

            // then
            assertEquals(AccountNumberStatus.AMBIVALENT, ambivalentNumber.getStatus());
            assertEquals(possibleAccountNumbers.get(i), toNumberStrings(ambivalentNumber.getPossibleAccountNumbers()));
        }
    }

    @SafeVarargs
    private final <T> Set<T> createSet(T... numbers) {
        return new HashSet<>(Arrays.asList(numbers));
    }

    private Set<String> toNumberStrings(Iterable<AccountNumber> accountNumbers) {
        final Set<String> numberStrings = new HashSet<>();
        for (AccountNumber accountNumber : accountNumbers) {
            numberStrings.add(accountNumber.getNumberString());
        }
        return numberStrings;
    }
}
