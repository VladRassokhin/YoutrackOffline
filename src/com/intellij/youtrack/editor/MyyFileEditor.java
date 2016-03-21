package com.intellij.youtrack.editor;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.project.Project;

/**
 * @author Konstantin Bulenkov
 */
public class MyyFileEditor {

  public MyyFileEditor() {
  }

  public static boolean isLogged(Project project) {
    return PropertiesComponent.getInstance(project).isTrueValue("MYY_LOGGED");
  }

  public static void setLogged(Project project, boolean logged) {
    PropertiesComponent.getInstance(project).setValue("MYY_LOGGED", String.valueOf(logged));
  }
}
