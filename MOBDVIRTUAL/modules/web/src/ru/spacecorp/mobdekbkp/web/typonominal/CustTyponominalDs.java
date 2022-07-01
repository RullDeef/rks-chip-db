package ru.spacecorp.mobdekbkp.web.typonominal;

import com.haulmont.cuba.gui.data.impl.CustomCollectionDatasource;
import ru.spacecorp.mobdekbkp.entity.DeviceFilterConditions;
import ru.spacecorp.mobdekbkp.entity.Typonominal;

import java.util.*;

/**
 * Created by Stepanov_ME on 22.08.2018.
 */
public class CustTyponominalDs extends CustomCollectionDatasource<Typonominal, UUID> {

    private ArrayList<DeviceFilterConditions> filterConditions;
    private HashMap<UUID, Integer> tnIdNotAllowedMap;

    @Override
    protected Collection<Typonominal> getEntities(Map<String, Object> params) {
        ArrayList<Typonominal> typonominals = new ArrayList<>(dataSupplier.loadList(getCompiledLoadContext().setView("tn-names")));
        if (filterConditions != null) {
            CustomFilter customFilter = new CustomFilter();
            tnIdNotAllowedMap = customFilter.filterData(filterConditions);
            typonominals.removeIf(x -> tnIdNotAllowedMap.get(x.getId()) != null);
        }
        return typonominals;
    }

    public void init(String queryString, ArrayList<DeviceFilterConditions> deviceFilterConditions) {
        this.filterConditions = deviceFilterConditions;
        super.query = queryString;
    }
}
