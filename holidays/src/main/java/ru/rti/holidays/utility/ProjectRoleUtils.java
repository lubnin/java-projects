package ru.rti.holidays.utility;

import ru.rti.holidays.entity.ProjectRole;

public class ProjectRoleUtils {
    public static ProjectRole.ProjectRoleSpecialType getProjectRoleSpecialTypeFromRole(ProjectRole projectRole) {
        if (projectRole == null) {
            return ProjectRole.ProjectRoleSpecialType.PROJECT_ROLE_SPECIAL_TYPE_REGULAR;
        }
        return projectRole.getProjectRoleSpecialType();
    }

    public static boolean isRegularProjectRole(ProjectRole projectRole) {
        ProjectRole.ProjectRoleSpecialType roleSpecialType = getProjectRoleSpecialTypeFromRole(projectRole);
        return roleSpecialType == ProjectRole.ProjectRoleSpecialType.PROJECT_ROLE_SPECIAL_TYPE_REGULAR;
    }
}
