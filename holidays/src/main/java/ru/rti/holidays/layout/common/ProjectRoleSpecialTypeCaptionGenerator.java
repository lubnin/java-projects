package ru.rti.holidays.layout.common;

import com.vaadin.ui.ItemCaptionGenerator;
import ru.rti.holidays.entity.ProjectRole;

public class ProjectRoleSpecialTypeCaptionGenerator implements ItemCaptionGenerator<ProjectRole.ProjectRoleSpecialType> {
    @Override
    public String apply(ProjectRole.ProjectRoleSpecialType projectRoleSpecialType) {
        switch (projectRoleSpecialType) {
            case PROJECT_ROLE_SPECIAL_TYPE_TEAM_LEAD:
                return "Тимлид команды";
            case PROJECT_ROLE_SPECIAL_TYPE_REGULAR:
                return "Обычная";
            case PROJECT_ROLE_SPECIAL_TYPE_PROJECT_MANAGER:
                return "Руководитель проекта";
            case PROJECT_ROLE_SPECIAL_TYPE_LINE_MANAGER:
                return "Линейный руководитель";
            case PROJECT_ROLE_SPECIAL_TYPE_SUPERVISOR:
                return "Супервизор";
            default:
                return "Неизвестная роль";
        }
    }
}
