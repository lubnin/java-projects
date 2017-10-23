package ru.rti.holidays.layout.base.behaviour;

public class ButtonClickResult {
    private boolean result;

    public ButtonClickResult(boolean result) {
        this.result = result;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
