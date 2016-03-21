package com.intellij.youtrack.util;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.project.Project;

/**
 * @author Konstantin Bulenkov
 */
public class MyyConnectionUtil {

  public MyyConnectionUtil() {
  }

  public static boolean isLogged(Project project) {
    return PropertiesComponent.getInstance(project).isTrueValue("MYY_LOGGED");
  }

  public static void setLogged(Project project, boolean logged) {
    PropertiesComponent.getInstance(project).setValue("MYY_LOGGED", String.valueOf(logged));
  }
}
