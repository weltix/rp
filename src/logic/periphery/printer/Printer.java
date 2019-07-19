/*
 * Copyright (c) RESONANCE JSC, 17.07.2019
 */

package logic.periphery.printer;

import logic.periphery.CashDrawer;
import logic.periphery.Device;

public interface Printer extends Device, CashDrawer {
    int LINE_LENGTH_DEFAULT = 32;

    void setLineLength(int length);

    default int getLineLength() {
        return LINE_LENGTH_DEFAULT;
    }
}