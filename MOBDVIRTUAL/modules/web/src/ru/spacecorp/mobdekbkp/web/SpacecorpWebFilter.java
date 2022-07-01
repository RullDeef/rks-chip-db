package ru.spacecorp.mobdekbkp.web;

import com.haulmont.cuba.gui.components.filter.FilterDelegate;
import com.haulmont.cuba.web.gui.components.WebFilter;

public class SpacecorpWebFilter extends WebFilter {
    public FilterDelegate getDelegate() {
        return delegate;
    }
}
