package ourproject.nildlumbini;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StaticClassUtils {

    public StaticClassUtils()
    {

    }


    public static boolean isEmailFormatValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@gmail.com";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

}
