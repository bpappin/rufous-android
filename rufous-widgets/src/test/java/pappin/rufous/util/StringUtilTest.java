package pappin.rufous.util;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class StringUtilTest {

    private String[] values;
    private String joinedValues;
    private String separator = "|";

    @Before
    public void init() {
        values = new String[0];
        joinedValues = "";
    }

    @Test
    public void join_whenValuesAreEmptyOrNull_shouldReturnEmpty() {
        String result = StringUtil.join(values, separator);
        assertEquals(result, "");

        values = null;
        result = StringUtil.join(values, separator);
        assertEquals(result, "");
    }

    @Test
    public void join_whenAValueIsEmpty_shouldReturnOnlyFormattedValues() {
        values = new String[] {"xxxx", "", "zzzz"};
        joinedValues = "xxxx|zzzz";
        String result = StringUtil.join(values, separator);
        assertEquals(result, joinedValues);
    }

    @Test
    public void join_whenAValueIsNull_shouldReturnOnlyFormattedValues() {
        values = new String[] {null, "xyxy", "oooo"};
        joinedValues = "xyxy|oooo";
        String result = StringUtil.join(values, separator);
        assertEquals(joinedValues, result);
    }

    @Test
    public void join_whenAValueIsOnlyASeparator_shouldReturnOnlyFormattedValues() {
        values = new String[] {"xxxx", "|", "zzzz"};
        joinedValues = "xxxx|zzzz";
        String result = StringUtil.join(values, separator);
        assertEquals(joinedValues, result);
    }


    @Test
    public void join_whenValuesAlreadyContainSeparator_shouldReturnSameValues() {
        values = new String[] {"xxxxx|", "vvvvv|", "oooooo"};
        joinedValues = "xxxxx|vvvvv|oooooo";

        String result = StringUtil.join(values, separator);
        assertEquals(joinedValues, result);
    }

    @Test
    public void join_whenValuesAreRaw_shouldReturnFormattedValues() {
        values = new String[] {"xxxx", "99999", "xyxyx"};
        joinedValues = "xxxx|99999|xyxyx";

        String result = StringUtil.join(values, separator);
        assertEquals(joinedValues, result);
    }

    @Test
    public void join_whenValuesAreMixed_shouldFormatOnlyRawValues() {
        values = new String[] {"xxxx|", "99999", "xyxyx"};
        joinedValues = "xxxx|99999|xyxyx";

        String result = StringUtil.join(values, separator);
        assertEquals(joinedValues, result);
    }
}
