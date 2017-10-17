package ocr.account;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class AccountNumberParserTest {
    @Test
    public void parse_digitParsing_shouldBeLegible() throws Exception {
        // given
        final List<String> lines = new ArrayList<>();
        final List<String> accountNumbers = new ArrayList<>();
        lines.add(" _  _  _  _  _  _  _  _  _ ");
        lines.add("| || || || || || || || || |");
        lines.add("|_||_||_||_||_||_||_||_||_|");
        lines.add("                           ");
        accountNumbers.add("000000000");
        lines.add("                           ");
        lines.add("  |  |  |  |  |  |  |  |  |");
        lines.add("  |  |  |  |  |  |  |  |  |");
        lines.add("                           ");
        accountNumbers.add("111111111");
        lines.add(" _  _  _  _  _  _  _  _  _ ");
        lines.add(" _| _| _| _| _| _| _| _| _|");
        lines.add("|_ |_ |_ |_ |_ |_ |_ |_ |_ ");
        lines.add("                           ");
        accountNumbers.add("222222222");
        lines.add(" _  _  _  _  _  _  _  _  _ ");
        lines.add(" _| _| _| _| _| _| _| _| _|");
        lines.add(" _| _| _| _| _| _| _| _| _|");
        lines.add("                           ");
        accountNumbers.add("333333333");
        lines.add("                           ");
        lines.add("|_||_||_||_||_||_||_||_||_|");
        lines.add("  |  |  |  |  |  |  |  |  |");
        lines.add("                           ");
        accountNumbers.add("444444444");
        lines.add(" _  _  _  _  _  _  _  _  _ ");
        lines.add("|_ |_ |_ |_ |_ |_ |_ |_ |_ ");
        lines.add(" _| _| _| _| _| _| _| _| _|");
        lines.add("                           ");
        accountNumbers.add("555555555");
        lines.add(" _  _  _  _  _  _  _  _  _ ");
        lines.add("|_ |_ |_ |_ |_ |_ |_ |_ |_ ");
        lines.add("|_||_||_||_||_||_||_||_||_|");
        lines.add("                           ");
        accountNumbers.add("666666666");
        lines.add(" _  _  _  _  _  _  _  _  _ ");
        lines.add("  |  |  |  |  |  |  |  |  |");
        lines.add("  |  |  |  |  |  |  |  |  |");
        lines.add("                           ");
        accountNumbers.add("777777777");
        lines.add(" _  _  _  _  _  _  _  _  _ ");
        lines.add("|_||_||_||_||_||_||_||_||_|");
        lines.add("|_||_||_||_||_||_||_||_||_|");
        lines.add("                           ");
        accountNumbers.add("888888888");
        lines.add(" _  _  _  _  _  _  _  _  _ ");
        lines.add("|_||_||_||_||_||_||_||_||_|");
        lines.add(" _| _| _| _| _| _| _| _| _|");
        lines.add("                           ");
        accountNumbers.add("999999999");
        lines.add("    _  _     _  _  _  _  _ ");
        lines.add("  | _| _||_||_ |_   ||_||_|");
        lines.add("  ||_  _|  | _||_|  ||_| _|");
        lines.add("                           ");
        accountNumbers.add("123456789");

        for (int i = 0; i < accountNumbers.size(); i++) {
            // when
            final AccountNumber number = AccountNumberParser.parse(lines.subList(i * 4, (i + 1) * 4));

            // then
            assertEquals(accountNumbers.get(i), number.getNumberString());
        }
    }

    @Test
    public void parser_legibleWithValidChecksum() throws Exception {
        // given
        final List<String> lines = new ArrayList<>();
        lines.add(" _  _  _  _  _  _  _  _    ");
        lines.add("| || || || || || || ||_   |");
        lines.add("|_||_||_||_||_||_||_| _|  |");
        lines.add("                           ");
        final String accountNumber = "000000051";

        // when
        final AccountNumber number = AccountNumberParser.parse(lines);

        // then
        assertEquals(AccountNumberStatus.OK, number.getStatus());
        assertEquals(accountNumber, number.getNumberString());
    }

    @Test
    public void parser_legibleWithInvalidChecksum() throws Exception {
        // given
        final List<String> lines = new ArrayList<>();
        lines.add(" _  _  _  _  _  _  _  _  _ ");
        lines.add("| || || || || || || ||_   |");
        lines.add("|_||_||_||_||_||_||_| _|  |");
        lines.add("                           ");
        final String accountNumber = "000000057";

        // when
        final AccountNumber number = AccountNumberParser.parse(lines);

        // then
        assertEquals(AccountNumberStatus.ERROR, number.getStatus());
        assertEquals(accountNumber, number.getNumberString());
    }

    @Test
    public void parse_numberWithOneIllegibleDigit() throws Exception {
        // given
        final List<String> lines = new ArrayList<>();
        lines.add("    _  _  _  _  _  _     _ ");
        lines.add("|_||_|| || ||_   |  |  | _ ");
        lines.add("  | _||_||_||_|  |  |  | _|");
        lines.add("                           ");
        final String accountNumber = "49006771?";

        // when
        final AccountNumber number = AccountNumberParser.parse(lines);

        // then
        assertEquals(AccountNumberStatus.ILLEGIBLE, number.getStatus());
        assertEquals(accountNumber, number.getNumberString());
    }

    @Test
    public void parse_numberWithTwoIllegibleDigits() throws Exception {
        // given
        final List<String> lines = new ArrayList<>();
        lines.add("    _  _     _  _  _  _  _ ");
        lines.add("  | _| _||_| _ |_   ||_||_|");
        lines.add("  ||_  _|  | _||_|  ||_| _ ");
        lines.add("                           ");
        final String accountNumber = "1234?678?";

        // when
        final AccountNumber number = AccountNumberParser.parse(lines);

        // then
        assertEquals(AccountNumberStatus.ILLEGIBLE, number.getStatus());
        assertEquals(accountNumber, number.getNumberString());
    }
}
