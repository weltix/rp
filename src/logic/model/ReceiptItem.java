/*
 * Copyright (c) RESONANCE JSC, 23.08.2019
 */

package logic.model;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * Describes one item of receipt.
 */

public class ReceiptItem {
    private String name;
    private String barcode;
    private int code;
    private int vendorCode;

    private boolean isDiscountAllowed;
    private boolean isExcisable;
    private boolean isDividable;
    private boolean isFreePrice;
    private boolean isFiscal;
    private char tax1;
    private char tax2;
    private float price;

    private BigDecimal totalPrice;
    private float quantity = 1.0f;

    void setPrice(float newPrice) {
        this.price = newPrice;
    }

    void addQuantity(float quantity) {
        this.quantity += quantity;
    }

    void setQuantity(float newQuantity) {
        this.quantity = newQuantity;
    }

    float getQuantity() {
        return quantity;
    }

    void setTotalPrice(float price, float quantity) {
        BigDecimal tempPrice = new BigDecimal(String.valueOf(price));
        BigDecimal tempQuantity = new BigDecimal(String.valueOf(quantity));
        totalPrice = tempPrice.multiply(tempQuantity, new MathContext(2, RoundingMode.HALF_UP));
    }

    // TODO: 24.04.2019 Update equals and hashCode methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReceiptItem that = (ReceiptItem) o;
        return code == that.code &&
                vendorCode == that.vendorCode &&
                isDiscountAllowed == that.isDiscountAllowed &&
                isExcisable == that.isExcisable &&
                isDividable == that.isDividable &&
                isFreePrice == that.isFreePrice &&
                isFiscal == that.isFiscal &&
                tax1 == that.tax1 &&
                tax2 == that.tax2 &&
                Objects.equals(name, that.name) &&
                barcode.equals(that.barcode) &&
                Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, barcode, code, vendorCode, isDiscountAllowed, isExcisable, isDividable, isFreePrice, isFiscal, tax1, tax2, price);
    }
}