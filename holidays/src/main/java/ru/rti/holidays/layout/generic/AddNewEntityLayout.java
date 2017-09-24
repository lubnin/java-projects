package ru.rti.holidays.layout.generic;

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.ValueProvider;
import com.vaadin.server.Setter;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import ru.rti.holidays.entity.DBEntity;
import ru.rti.holidays.layout.base.BaseVerticalLayout;

import java.util.ArrayList;
import java.util.List;

public class AddNewEntityLayout<T extends DBEntity> extends BaseVerticalLayout {
    protected Binder<T> entityBinder = new Binder<T>();
    protected final Class<T> entityClass;
    protected T newEntity;
    //protected AddNewEntityLayoutBuilder<T> entityLayoutBuilder = new AddNewEntityLayoutBuilder<T>();

    private List<NewEntityTextControlBinding> textControlBindings;

    public class NewEntityTextControlBinding {
        private T caption;
        private ValueProvider vp;
        private Setter st;

        NewEntityTextControlBinding(T caption, ValueProvider vp, Setter st) {
            this.caption = caption;
            this.vp = vp;
            this.st = st;
        }

    }

    public List<NewEntityTextControlBinding> getTextControlBindings() {
        if (textControlBindings == null) {
            textControlBindings = new ArrayList<>();
        }
        return textControlBindings;
    }
    //public AddNewEntityLayoutBuilder<T> getLayoutBuilder() {
    //    return entityLayoutBuilder;
    //}
/*
    public class AddNewEntityLayoutBuilder<T> {
        List<TextField> textFields;
        AddNewEntityLayoutBuilder<T> instance;

        public AddNewEntityLayoutBuilder() {
        }

        public AddNewEntityLayoutBuilder<T> appendTextField(String caption) {
            if (textFields == null) {
                textFields = new ArrayList<TextField>();
            }
            textFields.add(new TextField(caption));
            return this;
        }

        public List<TextField> getTextFields() {
            return textFields;
        }
    }
*/
    public AddNewEntityLayout(final Class<T> entityClass) {
        this.entityClass = entityClass;
        try {
            create();
        } catch (IllegalAccessException e) {
            handleException(e, e.getMessage());
        } catch (InstantiationException e) {
            handleException(e, e.getMessage());
        }
    }

    public T create() throws IllegalAccessException, InstantiationException {
        newEntity = entityClass.newInstance();
        return newEntity;
    }

    public Binder<T> getEntityBinder() {
        return entityBinder;
    }

    //public Binder.BindingBuilder addTextFieldAndBind(String caption) {
//        TextField txtField = new TextField(caption);
  //      return entityBinder.forField(txtField);
    //}

    @Override
    public void constructLayout() {
        Button btnSaveEntity = new Button("Сохранить", event -> {
            try {
                entityBinder.writeBean(newEntity);

                if (saveButtonClickListener != null) {
                    saveButtonClickListener.onSaveData(this, newEntity);
                }

                newEntity = entityClass.newInstance();
                entityBinder.readBean(newEntity);

                if (getParentLayout() != null) {
                    getParentLayout().refreshDataGrid();
                }
            } catch (ValidationException e) {
                handleException(e, "Невозможно сохранить команду. Пожалуйста, проверьте корректность заполнения полей.");
            }catch (InstantiationException e) {
                handleException(e, "Невозможно создать объект.");
            } catch (IllegalAccessException e){
                handleException(e, "Неверный доступ.");
            }
        });

        /*
        TextField txtTeamName = new TextField("Название команды:");
        teamBinder.forField(txtTeamName)
                .asRequired("Необходимо ввести название команды")
                .bind(Team::getTeamName, Team::setTeamName);

        Button btnSaveTeam = new Button("Сохранить", event -> {
            try {
                teamBinder.writeBean(newTeam);
                if (saveButtonClickListener != null) {
                    saveButtonClickListener.onSaveData(this, newTeam);
                }

                newTeam = new Team();
                teamBinder.readBean(newTeam);

                if (getParentLayout() != null) {
                    getParentLayout().refreshDataGrid();
                }
            } catch (ValidationException e) {
                handleException(e, "Невозможно сохранить команду. Пожалуйста, проверьте корректность заполнения полей.");
            }
        });*/

        addComponent(new Label("Generic layout"));
        addComponent(btnSaveEntity);

    }

    @Override
    public void postConstructLayout() {
        super.postConstructLayout();
    }
}
