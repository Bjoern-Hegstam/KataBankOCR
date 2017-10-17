package ocr.account;

public enum AccountNumberStatus {
    OK("OK"),
    ILLEGIBLE("ILL"),
    ERROR("ERR"),
    AMBIVALENT("AMB");

    private final String value;

    AccountNumberStatus(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
