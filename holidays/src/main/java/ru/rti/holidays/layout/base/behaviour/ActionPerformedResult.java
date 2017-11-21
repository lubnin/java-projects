package ru.rti.holidays.layout.base.behaviour;

import java.util.Collection;

public class ActionPerformedResult<T> {
    private boolean result;
    private Collection<T> resultItems;

    public ActionPerformedResult(boolean result) {
        this.result = result;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public Collection<T> getResultItems() {
        return resultItems;
    }

    public void setResultItems(Collection<T> resultItems) {
        this.resultItems = resultItems;
    }
}
