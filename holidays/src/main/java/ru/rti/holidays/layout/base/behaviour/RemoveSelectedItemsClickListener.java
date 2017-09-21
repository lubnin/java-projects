package ru.rti.holidays.layout.base.behaviour;

import ru.rti.holidays.entity.DBEntity;
import ru.rti.holidays.layout.base.BaseLayout;
import java.util.Set;

@FunctionalInterface
public interface RemoveSelectedItemsClickListener {
    void onRemoveSelectedItems(BaseLayout layout, Set<? extends DBEntity> entities);
}
