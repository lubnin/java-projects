package ru.rti.holidays.component.vaadin;

import com.vaadin.server.Resource;
import ru.rti.holidays.component.vaadin.button.RtiButton;

public abstract class RtiGUIFactory {
//    public static <T extends RtiButton> T createButton(Class<T> cls, String caption) {
//        return createButton(cls, caption, null, null, null, null);
//    }
//
//    public static <T extends RtiButton> T createButton(Class<T> cls, String caption, Boolean enabled, String width) {
//        return createButton(cls, caption, enabled, null, width, null);
//    }
//
//    public static <T extends RtiButton> T createButton(Class<T> cls, String caption, Resource icon) {
//        return createButton(cls, caption, null, null, null, null);
//    }

    public static <T extends RtiButton> T createButton(Class<T> buttonClass) {
        T instance = null;

        try {
            instance = buttonClass.newInstance();
            instance.build();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return instance;
    }

//    public static <T extends RtiButton> T createButton(Class<T> cls, String caption, Boolean enabled, String id, String width, Resource icon) {
//        T instance = null;
//
//        try {
//            instance = cls.newInstance();
//            if (caption != null) {
//                instance.setButtonCaption(caption);
//            }
//            if (enabled != null) {
//                instance.setButtonEnabled(enabled);
//            }
//            if (id != null) {
//                instance.setButtonId(id);
//            }
//            if (width != null) {
//                instance.setButtonWidth(width);
//            }
//            if (icon != null) {
//                instance.setButtonIcon(icon);
//            }
//            instance.init();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//
//        return instance;
//    }
}
