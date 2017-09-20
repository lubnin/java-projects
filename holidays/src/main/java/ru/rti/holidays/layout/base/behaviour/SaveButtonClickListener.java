package ru.rti.holidays.layout.base.behaviour;

import ru.rti.holidays.layout.base.BaseLayout;

@FunctionalInterface
public interface SaveButtonClickListener {
    void onSaveData(BaseLayout layout, Object objectForSave);
}
