package ru.spacecorp.mobdekbkp.web.parameter;

import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.gui.data.impl.CustomGroupDatasource;
import ru.spacecorp.mobdekbkp.entity.Parameter;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Stepanov_ME on 31.07.2018.
 */
public class ParameterDatasource extends CustomGroupDatasource<Parameter, UUID> {

    private List<Parameter> parameterList = null;

    public void initParams(List<Parameter> parameters) {
        parameterList = parameters;
    }

    @Override
    protected Collection<Parameter> getEntities(Map params) {
        if (parameterList != null) {
            return parameterList;
        } else {
            LoadContext<Parameter> loadContext = getCompiledLoadContext();
            return dataSupplier.loadList(loadContext);
        }
    }
}
