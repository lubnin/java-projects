package ru.rti.holidays.component.vaadin;

import ru.rti.holidays.component.vaadin.button.RtiButton;

public abstract class RtiGUIFactory {
    public static <T extends RtiButton> T createButton(Class<T> cls, String caption) {
        T instance = null;

        try {
            instance = cls.newInstance();
            instance.setButtonCaption(caption);
            instance.init();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return instance;
    }

    public static <T extends RtiButton> T createButton(Class<T> cls, String caption, boolean enabled, String width) {
        T instance = null;

        try {
            instance = cls.newInstance();
            instance.setButtonCaption(caption);
            instance.setButtonEnabled(enabled);
            instance.setButtonWidth(width);
            instance.init();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return instance;
    }

    public static <T extends RtiButton> T createButton(Class<T> cls, String caption, boolean enabled, String id, String width) {
        T instance = null;

        try {
            instance = cls.newInstance();
            instance.setButtonCaption(caption);
            instance.setButtonEnabled(enabled);
            instance.setButtonId(id);
            instance.setButtonWidth(width);
            instance.init();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return instance;
    }
}
