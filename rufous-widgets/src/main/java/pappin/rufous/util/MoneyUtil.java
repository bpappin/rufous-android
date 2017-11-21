package pappin.rufous.util;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Currency;

/**
 * Created by bpappin on 2017-05-05.
 */

public class MoneyUtil {
    public static final BigDecimal toDecimal(int bankersValue) {
        BigDecimal result = new BigDecimal((bankersValue / 100) + "." + (bankersValue % 100));
        return result;
    }

    public static String format(int price, String currencyCode) {
        Currency currency = Currency.getInstance(currencyCode);
        NumberFormat nformat = NumberFormat
                .getCurrencyInstance();
        nformat.setCurrency(currency);
        return nformat.format(MoneyUtil.toDecimal(price));
    }
}
