package ru.rti.holidays.navigation;

import com.vaadin.navigator.NavigationStateManager;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.Page;
import com.vaadin.shared.Registration;

public class CustomNavigationStateManager implements NavigationStateManager {
    private final Page page;
    private Navigator navigator;
    private Registration uriFragmentRegistration;

    public CustomNavigationStateManager(Page page) {
        this.page = page;
    }

    @Override
    public String getState() {
        String fragment = this.getFragment();
        return fragment != null && fragment.startsWith("!") ? fragment.substring(1) : "";
    }

    @Override
    public void setState(String state) {
        this.setFragment("!" + state);
    }

    @Override
    public void setNavigator(Navigator navigator) {
        if (this.navigator == null && navigator != null) {
            this.uriFragmentRegistration = this.page.addUriFragmentChangedListener((event) -> {
                navigator.navigateTo(this.getState());
            });
        } else if (this.navigator != null && navigator == null) {
            this.uriFragmentRegistration.remove();
        }

        this.navigator = navigator;
    }

    protected String getFragment() {
        return this.page.getUriFragment();
    }

    protected void setFragment(String fragment) {
        this.page.setUriFragment(fragment, false);
    }
}
