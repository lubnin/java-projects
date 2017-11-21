package ru.rti.holidays.layout.base.behaviour;

import ru.rti.holidays.layout.base.BaseLayout;

@FunctionalInterface
public interface ActionPerformedListener<T> {
    ActionPerformedResult<T> onActionPerformed(BaseLayout layout, Object ... params);
}
