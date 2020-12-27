package com.makkajai.swapnil.kumbhar;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Main {

    private final Map<CartItem, Integer> itemMap = new HashMap<>();

    DecimalFormat df = new DecimalFormat("###.00");

    public void put(CartItem item, int count) {
        if (item.isImported()) item = new ImportTaxDecorator(item);
        if (!item.isExempt()) item = new SalesTaxDecorator(item);
        Integer i = this.itemMap.get(item);
        if (i != null) count += i;
        this.itemMap.put(item, count);
    }

    public void remove(CartItem item) {
        this.itemMap.remove(item);
    }

    public void clear() {
        this.itemMap.clear();
    }

    public Set<CartItem> getItems() {
        return itemMap.keySet();
    }

    public int getQuantity(CartItem item) {
        return itemMap.get(item);
    }

    public double getTaxTotal() {
        double taxTotal = 0;
        for (CartItem item : itemMap.keySet()) {
            double subTotal = item.getPrice() * getQuantity(item);
            double subInitTotal = item.getInitPrice() * getQuantity(item);
            taxTotal += subTotal - subInitTotal;
        }
        return taxTotal;
    }

    public double getTotal() {
        double total = 0;
        for (CartItem item : itemMap.keySet()) {
            double subTotal = item.getPrice() * getQuantity(item);
            total += subTotal;
        }
        return Helper.roundPrice(total);
    }

    public void printOrderInput() {
        System.out.println("Order input: ");
        for (CartItem item : itemMap.keySet()) {
            System.out.println(itemMap.get(item) + " " + item.getName() + " at " + df.format(item.getInitPrice()));
        }
        System.out.println();
    }

    public void printOrderResults() {
        double taxtotal = 0;
        double total = 0;
        System.out.println("Order results: ");
        Set<CartItem> taxedItems = itemMap.keySet();
        for (CartItem item : taxedItems) {
            double subTotal = item.getPrice() * getQuantity(item);
            double subInitTotal = item.getInitPrice() * getQuantity(item);
            taxtotal += subTotal - subInitTotal;
            total += subTotal;
            System.out.println(getQuantity(item) + " " + item.getName() + ": " + df.format(subTotal));
        }
        total = Helper.roundPrice(total);
        System.out.println("Sales Taxes: " + df.format(taxtotal));
        System.out.println("Total: " + df.format(total));
        System.out.println();
    }

    public static void main(String[] args) {
        // Check how many arguments were passed in
        String filePath = "src/main/resources/cart/input-one.txt";
        Helper.getFromFile(filePath);
    }
}

