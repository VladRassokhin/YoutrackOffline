package com.intellij.youtrack.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;

/**
 * @author Konstantin Bulenkov
 */
public class OpenYouTrackAction extends AnAction implements DumbAware {
  @Override
  public void actionPerformed(AnActionEvent e) {
    Project project = e.getProject();
    open(project);
  }

  public static void open(Project project) {
    final ToolWindowManager manager = ToolWindowManager.getInstance(project);
    final ToolWindow tw = manager.getToolWindow("YouTrack");
    tw.show(null);
  }

  @Override
  public void update(AnActionEvent e) {
    e.getPresentation().setEnabledAndVisible(e.getProject() != null);
  }
}
