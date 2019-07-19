/*
 * Copyright (c) RESONANCE JSC, 17.07.2019
 */

package logic.periphery;

public interface Device {

    void init();

    void finish();

    <T> void sendTo(T data);
}
