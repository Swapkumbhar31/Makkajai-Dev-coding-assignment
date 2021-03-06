package com.makkajai.swapnil.kumbhar;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.Set;

public class Helper {

    private static Set<String> exemptItems;

    static {
        exemptItems = new HashSet<>();
        exemptItems.add("book");
        exemptItems.add("headache pills");
        exemptItems.add("packet of headache pills");
        exemptItems.add("box of imported chocolates");
        exemptItems.add("imported box of chocolates");
        exemptItems.add("box of chocolates");
        exemptItems.add("chocolate");
        exemptItems.add("chocolate bar");
        exemptItems.add("pills");
    }

    static public double nearest5Percent(double amount) {

        return BigDecimal.valueOf(Math.ceil(amount * 20) / 20).setScale(2, RoundingMode.HALF_UP).doubleValue();

    }

    public static double roundPrice(double amount) {

        return new BigDecimal(amount).setScale(2, RoundingMode.HALF_UP)
                .doubleValue();

    }

    public static boolean isExempt(String name) {
        return exemptItems.contains(name);
    }

    public static void getFromFile(String fileName) {
        Main sc = new Main();
        try {
            BufferedReader in = new BufferedReader(new FileReader(fileName));
            String str;
            while ((str = in.readLine()) != null) {
                if (ProductParser.matches(str) && !str.isEmpty())
                    sc.put(ProductParser.parser(str), ProductParser.count(str));
                else if (!str.isEmpty()) System.out.println("unknown line format: " + str);
            }
            in.close();
        } catch (IOException e) {
            System.out.println("error:" + e.getMessage());
            return;
        }
        sc.printOrderInput();
        sc.printOrderResults();
    }
}
