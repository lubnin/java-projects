package ru.rti.holidays.layout.base;

import ru.rti.holidays.view.base.AbstractBaseView;

public class StandardBaseLayoutDrawer implements BaseLayoutDrawer {
    private BaseLayout layout;
    private AbstractBaseView viewContainer;
    private BaseLayout parentLayout;

    public StandardBaseLayoutDrawer(BaseLayout parentLayout, BaseLayout layout) {
        this.parentLayout = parentLayout;
        this.layout = layout;
    }

    public StandardBaseLayoutDrawer(AbstractBaseView viewContainer, BaseLayout layout) {
        this.viewContainer = viewContainer;
        this.layout = layout;
    }

    //public StandardBaseLayoutDrawer(BaseLayout layout) {
    //    this.layout = layout;
    //}

    //public StandardBaseLayoutDrawer() {

    //}


    /**
     * Draws the layout defined in a constructor of StandardBaseLayoutDrawer class with automatically adding it to a containing view.
     * Also, performs all exception handling for a layout while constructing and post-constructing the layout
     */
    @Override
    public void drawLayout() {
        if (layout == null) {
            throw new IllegalArgumentException("`null` value passed for layout. You should pass non-null value in the method");
        }

        try {
            layout.constructLayout();
        } catch (Exception e) {
            layout.handleException(e, e.getMessage());
        }

        try {
            layout.postConstructLayout();
        } catch (Exception e) {
            layout.handleException(e, e.getMessage());
        }

        if (viewContainer != null) {
            viewContainer.addComponent(layout);
        } else if (parentLayout != null) {
            parentLayout.addComponent(layout);
        }

    }

    public AbstractBaseView getViewContainer() {
        return viewContainer;
    }

    public void setViewContainer(AbstractBaseView viewContainer) {
        this.viewContainer = viewContainer;
    }

    public BaseLayout getLayout() {
        return layout;
    }

    public void setLayout(BaseLayout layout) {
        this.layout = layout;
    }
}
