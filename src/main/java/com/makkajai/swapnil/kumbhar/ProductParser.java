package com.makkajai.swapnil.kumbhar;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProductParser {
    private static final String ITEM_DESCRIPTION_REGEX = "(\\d+)\\s((\\w+\\s)+)at\\s(\\d+.\\d+)";

    public static CartItem parser(String order) {
        Matcher m = parse(order);
        String name = m.group(2).trim();
        CartProduct item = new CartProduct(name, Double.parseDouble(m.group(4)));
        if (name.contains("imported"))
            item.setImported(true);
        if (Helper.isExempt(name))
            item.setExempt(true);
        return item;
    }

    public static int count(String order) {
        return Integer.parseInt(parse(order).group(1));
    }

    public static Matcher parse(String description) {
        Pattern pattern = Pattern.compile(ITEM_DESCRIPTION_REGEX);
        return pattern.matcher(description);
    }

    public static boolean matches(String description) {
        return Pattern.matches(ITEM_DESCRIPTION_REGEX, description);
    }
}
