package ru.rti.holidays.layout.base.behaviour;

import ru.rti.holidays.entity.DBEntity;
import ru.rti.holidays.layout.base.BaseLayout;

@FunctionalInterface
public interface LoginButtonClickListener {
    void onLogin(BaseLayout layout, DBEntity employee);
}
