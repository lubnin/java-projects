package ru.rti.holidays.view.old;


import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.ExternalResource;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import ru.rti.holidays.view.old.BaseView;

public class ErrorView extends BaseView {
    @Override
    public Component getViewComponent() {
        VerticalLayout layout = new VerticalLayout();
        Label errorTitle = new Label("Кажется, что-то пошло не так... :-(");
        errorTitle.addStyleName(ValoTheme.LABEL_H1);
        //errorTitle.addStyleName(ValoTheme.LABEL_FAILURE);
        //errorTitle.setIcon(VaadinIcons.MEH_O);
        //errorTitle.setIcon(VaadinIcons.STOP);

        Label errorText = new Label("<b>Вы попали на эту страницу, поскольку произошло одно из следующих событий:</b><br/><br/>" +
                "1) Произошла системная ошибка при попытке обработать Ваш запрос;<br/>" +
                "2) Мы не смогли найти запрашиваемую Вами страницу в Системе. Вероятно, что её не существует;<br/>" +
                "Пожалуйста, попробуйте снова перейти на Главную Страницу.", ContentMode.HTML
        );


        errorText.addStyleName(ValoTheme.LABEL_H3);

        Link mainPageLink = new Link("Перейти на Главную", new ExternalResource("/"));
        mainPageLink.addStyleName(ValoTheme.LINK_LARGE);
        mainPageLink.setIcon(VaadinIcons.REFRESH);

        layout.setSizeFull();
        layout.setSpacing(true);
        layout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        layout.addComponents(errorTitle, errorText, mainPageLink);
        return layout;

    }

    public ErrorView(Navigator navigator) {
        super(navigator);
    }
}
