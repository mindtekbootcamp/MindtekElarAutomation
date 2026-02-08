package elar.api.utils;

import java.util.concurrent.atomic.AtomicInteger;

public class CarrierDataGenerator {

    // guarantees uniqueness within the same millisecond, safe for data providers and parallel run
    private static final AtomicInteger COUNTER = new AtomicInteger(0);

    public final String mc;
    public final String dot;
    public final String abbr;
    public final String carrierName;
    public final String policyNumber;

    private CarrierDataGenerator(
            String mc,
            String dot,
            String abbr,
            String carrierName,
            String policyNumber
    ) {
        this.mc = mc;
        this.dot = dot;
        this.abbr = abbr;
        this.carrierName = carrierName;
        this.policyNumber = policyNumber;
    }

    public static CarrierDataGenerator generateCarrierData() {

        String ts = String.valueOf(System.currentTimeMillis());

        // 000–999 counter to avoid collisions
        int seq = COUNTER.getAndIncrement() % 1000;
        String seq3 = String.format("%03d", seq);

        // last 9 digits of time + counter = 12-digit unique value
        String last9 = ts.substring(ts.length() - 9);
        String numBase = last9 + seq3;

        //  mc/dot  -->  digits-only unique values
        String mc = numBase.substring(numBase.length() - 10);
        String dot = "1" + numBase.substring(numBase.length() - 9);

        // abbreviation --> 3 chars
        long uniqueNum = Long.parseLong(numBase);
        String abbr = createAbbreviation(uniqueNum);

        // unique values for carrier_name and policy_number
        String carrierName = "carrier_" + numBase;
        String policyNumber = "POL-" + numBase.substring(numBase.length() - 6);

        return new CarrierDataGenerator(mc, dot, abbr, carrierName, policyNumber);
    }

    private static String createAbbreviation(long value) {
        String s = Long.toString(Math.abs(value), 36).toUpperCase(); // base36: 0-9A-Z
        s = "000" + s;   // to make sure I don't get IndexOutOfBoundException
        return s.substring(s.length() - 3);
    }

    public static String uniqueDigits(int length) {
        if (length < 1 || length > 12) {
            throw new IllegalArgumentException("length must be between 1 and 12");
        }

        String ts = String.valueOf(System.currentTimeMillis());
        String last9 = ts.substring(ts.length() - 9);

        int seq = COUNTER.getAndIncrement() % 1000;   // 0-999
        String seq3 = String.format("%03d", seq);     // "000"-"999"

        String base12 = last9 + seq3;

        if (length <= 3) {
            // Take the last 'length' digits from last9 (time), not from seq3
            return last9.substring(last9.length() - length);
        }

        return base12.substring(base12.length() - length);
    }

    /** Letters + digits */
    public static String lettersAndDigits(int length) {
        if (length < 2) throw new IllegalArgumentException("length must be >= 2");

        String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String digits  = "0123456789";

        StringBuilder sb = new StringBuilder(length);

        // // Make sure that both types are present
        sb.append(letters.charAt(0));
        sb.append(digits.charAt(0));

        int i = 2;
        while (i < length) {
            if (i % 2 == 0) {
                // even positions --> letters
                sb.append(letters.charAt((i / 2) % letters.length()));
            } else {
                // odd positions --> digits
                sb.append(digits.charAt((i / 2) % digits.length()));
            }
            i++;
        }
        return sb.toString();
    }

    /** Special characters */
    public static String withSpecialChars(int length) {
        if (length < 2) throw new IllegalArgumentException("length must be >= 2");

        String digits = "0123456789";
        String specialChars = "!@#$%^&*";

        StringBuilder sb = new StringBuilder(length);

        // Make sure that both types are present
        sb.append(digits.charAt(0));
        sb.append(specialChars.charAt(0));

        int i = 2;
        while (i < length) {
            sb.append(digits.charAt(i % digits.length()));
            i++;
        }
        return sb.toString();
    }

}
