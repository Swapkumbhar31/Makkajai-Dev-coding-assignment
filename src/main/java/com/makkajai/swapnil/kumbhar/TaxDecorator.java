package com.makkajai.swapnil.kumbhar;

public abstract class TaxDecorator implements CartItem {

    protected CartItem item;

    protected double rate;

    abstract double getRate();

    public TaxDecorator(CartItem item) {
        this.item = item;
    }

    public double getPrice() {
        double salesTax = Helper.nearest5Percent(this.item.getInitPrice() * this.getRate());
        return Helper.roundPrice(this.item.getPrice() + salesTax);
    }

}
