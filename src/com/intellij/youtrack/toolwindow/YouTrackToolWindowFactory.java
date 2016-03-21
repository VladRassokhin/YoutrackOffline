package com.intellij.youtrack.toolwindow;

import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Computable;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.intellij.ui.content.ContentManager;
import com.intellij.youtrack.MyyLoginPanel;
import com.intellij.youtrack.editor.MyyFileEditor;
import com.intellij.youtrack.editor.MyyIssueViewer;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.text.JTextComponent;

public class YouTrackToolWindowFactory implements ToolWindowFactory, DumbAware {

    @Override
    public void createToolWindowContent(@NotNull final Project project, @NotNull ToolWindow toolWindow) {
        final ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        final ContentManager contentManager = toolWindow.getContentManager();
        final Content content;
        if (!MyyFileEditor.isLogged(project)) {
            content = createLoginContent(project, contentFactory, contentManager);
        } else {
            content = createIssuesContent(project, contentFactory, contentManager);
        }
        content.setCloseable(false);
        contentManager.addContent(content);
    }

    @NotNull
    private Content createLoginContent(@NotNull final Project project, final ContentFactory contentFactory, final ContentManager contentManager) {
        final MyyLoginPanel myLogin = new MyyLoginPanel(project);
        final Content content = contentFactory.createContent(myLogin.getRoot(), "Login", false);
        content.setPreferredFocusedComponent(new Computable<JComponent>() {
            @Override
            public JComponent compute() {
                for (JTextComponent textComponent : new JTextComponent[]{myLogin.getHost(), myLogin.getLogin(), myLogin.getPassword()}) {
                    if (StringUtil.isEmpty(textComponent.getText())) {
                        return textComponent;
                    }
                }
                return null;
            }
        });
        myLogin.onSuccess(new Runnable() {
            @Override
            public void run() {
                if (project.isDisposed() || contentManager.isDisposed()) return;
                content.setCloseable(true);
                contentManager.removeContent(content, true);
                contentManager.addContent(createIssuesContent(project, contentFactory, contentManager));
            }
        });
        return content;
    }

    @NotNull
    private Content createIssuesContent(@NotNull Project project, ContentFactory contentFactory, ContentManager contentManager) {
        return contentFactory.createContent(new MyyIssueViewer(project, contentManager), "Issues", false);
    }
}
