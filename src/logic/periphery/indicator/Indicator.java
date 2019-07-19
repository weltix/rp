/*
 * Copyright (c) RESONANCE JSC, 17.07.2019
 */

package logic.periphery.indicator;

import logic.periphery.Device;

public interface Indicator extends Device {
    int LINE_LENGTH_DEFAULT = 20;

    void setLineLength(int length);

    default int getLineLength() {
        return LINE_LENGTH_DEFAULT;
    }
}