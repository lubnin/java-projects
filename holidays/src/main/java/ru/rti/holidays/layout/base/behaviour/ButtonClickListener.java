package ru.rti.holidays.layout.base.behaviour;

import ru.rti.holidays.layout.base.BaseLayout;

@FunctionalInterface
public interface ButtonClickListener {
    ButtonClickResult onClick(BaseLayout layout, Object ... params);
}
