package com.liushuxue.corelibrary.event;

public class ForeOrBackEvent {
    private boolean isFore = false;

    public ForeOrBackEvent(boolean isFore) {
        this.isFore = isFore;
    }

    public boolean isFore() {
        return isFore;
    }
}
