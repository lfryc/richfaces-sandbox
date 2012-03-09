package org.richfaces.component;

import org.ajax4jsf.model.DataVisitResult;
import org.ajax4jsf.model.DataVisitor;

import javax.faces.context.FacesContext;

public class AbstractTogglePanelItemVisitor implements DataVisitor {
// ------------------------------ FIELDS ------------------------------

    private TabVisitorCallback callback;

    private AbstractTogglePanel panel;

// --------------------------- CONSTRUCTORS ---------------------------

    public AbstractTogglePanelItemVisitor(AbstractTogglePanel panel, TabVisitorCallback callback)
    {
        this.panel = panel;
        this.callback = callback;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface DataVisitor ---------------------

    @Override
    public DataVisitResult process(FacesContext context, Object rowKey, Object argument)
    {
        panel.setRowKey(context, rowKey);

        if (panel.isRowAvailable()) {
            if (panel.getChildCount() > 0) {
                for (AbstractTogglePanelItemInterface item : panel.getRenderedItems()) {
                    if(DataVisitResult.STOP.equals(callback.visit(item))) {
                        return DataVisitResult.STOP;
                    }
                }
            }
        }

        return DataVisitResult.CONTINUE;
    }

// -------------------------- INNER CLASSES --------------------------

    public abstract static class TabVisitorCallback {
// -------------------------- OTHER METHODS --------------------------

        public abstract DataVisitResult visit(AbstractTogglePanelItemInterface item);
    }
}
