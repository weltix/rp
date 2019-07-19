/*
 * Copyright (c) RESONANCE JSC, 18.07.2019
 */

package logic.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Класс, описывающий чек
 *
 * @author Dmitriy Bludov
 */

public class Receipt {
    private List<ReceiptItem> receiptItems = Collections.synchronizedList(new ArrayList<>());

    void addItem(ReceiptItem item) {
        if (!receiptItems.contains(item))
            receiptItems.add(item);
        else {
            ReceiptItem existingItem = receiptItems.get(receiptItems.indexOf(item));
            existingItem.addQuantity(item.getQuantity());
        }
    }

    void removeItem(int itemIndex) {
        receiptItems.remove(itemIndex);
    }

    void setItemQuantity(int itemIndex, float newQuantity){
        receiptItems.get(itemIndex).setQuantity(newQuantity);
    }
}