package ru.spacecorp.mobdekbkp.web.type;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.core.global.View;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.components.actions.AddAction;
import com.haulmont.cuba.gui.components.actions.CreateAction;
import com.haulmont.cuba.gui.components.actions.RemoveAction;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.data.GroupDatasource;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;
import com.spacecorp.rulesmodule.web.functionLib.FunctionLib;
import ru.spacecorp.mobdekbkp.entity.*;
import ru.spacecorp.mobdekbkp.service.AddEntriesService;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

public class TypeEdit extends AbstractEditor<Type> {
    @Named("paramValueTable.create")
    private CreateAction paramValueCreateBtn;
    @Inject
    private Metadata metadata;
    @Inject
    private AddEntriesService addEntriesService;
    @Named("manufacturersTable.add")
    private AddAction addActionManufacturers;
    @Named("calicoholdersTable.add")
    private AddAction addActioncalicoholders;
    @Named("providersTable.add")
    private AddAction addActionProviders;
    @Named("manufacturersTable.remove")
    private RemoveAction manufacturersRemoveBtn;
    @Inject
    private TabSheet tabSheetMain;
    @Inject
    private Button typonominalCreateBtn;
    @Inject
    private SplitPanel mainSplit;

    @Inject
    private Table<ParameterValue> paramValueTable;
    @Inject
    private ComponentsFactory componentsFactory;

    @Inject
    private GroupDatasource<Typonominal, UUID> typonominalDs;

    @Inject
    private TextArea reliabilityArea;

    @Inject
    private PopupView popupReliabilityData;

    private FunctionLib functionLib = new FunctionLib();

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);

        popupReliabilityData.setVisible(false);
        typonominalDs.addItemChangeListener(e -> {
            if(e.getItem()!=null){
                functionLib.refreshData();
                popupReliabilityData.setVisible(true);
                String reliabilityString = functionLib.getAdditionMsg(e.getItem());
                if(reliabilityString.length() <= 120) {
                    popupReliabilityData.setMinimizedValue(reliabilityString);
                }
                else {
                    popupReliabilityData.setMinimizedValue(reliabilityString.substring(0,120));
                }
                reliabilityArea.setValue(reliabilityString);
            }
            else {
                popupReliabilityData.setVisible(false);
            }
        });

        paramValueTable.addGeneratedColumn("labelName", entity -> {
            Label labelValue = componentsFactory.createComponent(Label.class);
            labelValue.setWidth("100%");
            labelValue.setHtmlEnabled(true);
            labelValue.setValue(entity.getParameter().getValue("name"));
            return labelValue;
        });

        paramValueTable.setColumnCaption("labelName", messages.getMessage(getClass(), "paramsName"));
    }

    @Override
    protected void initNewItem(Type item) {
        item.setHasLongDeliverCycle(ExtBoolean.notSet);
        item.setPlacementCategory(TypePlacementCategory.notSet);
    }

    @Override
    protected void postInit() {
        addManufacturersEntry();
        addCalicoholdersEntry();
        addProvidersEntry();
        addParameterValue();
        mainSplit.setLocked(true);
        if (getItem().getCreatedBy() == null) {
            tabSheetMain.getTab("tabParamValue").setVisible(false);
        }
        if (getItem().getManufacturers() == null) {
            typonominalCreateBtn.setDescription("Для создания Типономинала введите Изготовителя");
            typonominalCreateBtn.setEnabled(false);
            manufacturersRemoveBtn.setEnabled(false);
        } else {
            if (getItem().getManufacturers().size() > 1) {
                manufacturersRemoveBtn.setEnabled(true);
            } else {
                manufacturersRemoveBtn.setEnabled(false);
            }
        }


    }

    @Override
    public void ready() {
        super.ready();
        manufacturersRemoveBtn.setAfterRemoveHandler(new RemoveAction.AfterRemoveHandler() {
            @Override
            public void handle(Set removedItems) {
                if (getItem().getManufacturers().size() == 0) {
                    typonominalCreateBtn.setDescription("Для создания Типономинала введите Изготовителя");
                    typonominalCreateBtn.setEnabled(false);
                }
                if (getItem().getManufacturers().size() > 1) {
                    manufacturersRemoveBtn.setEnabled(true);
                } else {
                    manufacturersRemoveBtn.setEnabled(false);
                }
            }
        });
    }

    @Override
    protected boolean preCommit() {
        Boolean commited = true;

        if (getItem().getManufacturers() == null || getItem().getManufacturers().size() <= 0) {
            showNotification(messages.getMessage("ru.spacecorp.mobdekbkp.web.type", "noCompany"), NotificationType.WARNING);
            commited = false;
        } else {
            commited = true;
        }
        return commited;
    }   //Проверка на ввод изготовителя типа ЭКБ

    void addParameterValue() {
        //Начало - добавление Значение параметра
        Map<String, Object> valuesParameterValue = new HashMap<>();
        valuesParameterValue.put("type", getItem());
        paramValueCreateBtn.setWindowParams(valuesParameterValue);
        paramValueCreateBtn.setWindowId("mobdekbkp$ParameterValue.edit");
        //Конец - добавление Значение параметра
    }//добавление Значение параметра


    void addManufacturersEntry() {
        //Начало - добавление Изготовителя
        addActionManufacturers.setWindowId("mobdekbkp$Company.browse");
        addActionManufacturers.setHandler(new Lookup.Handler() {
            @Override
            public void handleLookup(Collection items) {
                List manufacturersList = new LinkedList(items);
                if (getItem().getManufacturers() != null && getItem().getManufacturers().size() >= 1) {
                    if (((Company) manufacturersList.get(0)).getCountry().getId().equals(getItem().getManufacturers().get(0).getName().getCountry().getId())) {
                        List<Company> companyList = new LinkedList<>();
                        for (TypeManufacturerEntry manufacturerEntry : getItem().getManufacturers()) {
                            companyList.add(manufacturerEntry.getName());
                        }
                        if (companyList.indexOf((Company) manufacturersList.get(0)) < 0) {
                            testManufacturers(manufacturersList);
                        } else {
                            showNotification(messages.getMessage("ru.spacecorp.mobdekbkp.web.type", "manufacturerExist"), NotificationType.WARNING);
                        }
                    } else {
                        showNotification(messages.getMessage("ru.spacecorp.mobdekbkp.web.type", "manufacturerCountryError"), NotificationType.WARNING);
                    }
                } else {
                    testManufacturers(manufacturersList);
                }
            }
        });
        //Конец - добавление Изготовителя
    } //добавление Изготовителя

    void testManufacturers(List manufacturersList) {
        TypeManufacturerEntry manufacturerEntry;
        if (getItem().getCreatedBy() == null) {
            manufacturerEntry = metadata.create(TypeManufacturerEntry.class);
            manufacturerEntry.setName((Company) manufacturersList.get(0));
            manufacturerEntry.setTypeInverse(getItem());
        } else {
            manufacturerEntry = addEntriesService.addManufacturerEntry(getItem(), (Company) manufacturersList.get(0));
        }

        if (getItem().getManufacturers() != null) {
            getItem().getManufacturers().add(manufacturerEntry);
        } else {
            List<TypeManufacturerEntry> typeManufacturerEntryList = new LinkedList<>();
            typeManufacturerEntryList.add(manufacturerEntry);
            getItem().setManufacturers(typeManufacturerEntryList);
        }
        typonominalCreateBtn.setDescription("");
        typonominalCreateBtn.setEnabled(true);

        if (getItem().getManufacturers().size() > 1) {
            manufacturersRemoveBtn.setEnabled(true);
        } else {
            manufacturersRemoveBtn.setEnabled(false);
        }
    } // Проверки при добавлении изготовителя

    void addCalicoholdersEntry() {
        //Начало - добавление Калькодержателя
        addActioncalicoholders.setWindowId("mobdekbkp$Company.browse");
        addActioncalicoholders.setHandler(new Lookup.Handler() {
            @Override
            public void handleLookup(Collection items) {
                List calicoholderList = new LinkedList(items);
                TypeCalicoholderEntry calicoholderEntry;
                if (getItem().getCreatedBy() == null) {
                    calicoholderEntry = metadata.create(TypeCalicoholderEntry.class);
                    calicoholderEntry.setName((Company) calicoholderList.get(0));
                    calicoholderEntry.setTypeInverse(getItem());
                } else {
                    calicoholderEntry = addEntriesService.addCalicoholderEntry(getItem(), (Company) calicoholderList.get(0));
                }

                if (getItem().getCalicoholders() != null) {
                    getItem().getCalicoholders().add(calicoholderEntry);
                } else {
                    List<TypeCalicoholderEntry> typeCalicoholderEntryList = new LinkedList<>();
                    typeCalicoholderEntryList.add(calicoholderEntry);
                    getItem().setCalicoholders(typeCalicoholderEntryList);
                }
            }
        });
        //Конец - добавление Калькодержателя
    }   //добавление Калькодержателя

    void addProvidersEntry() {
        //Начало - добавление Поставщика
        addActionProviders.setWindowId("mobdekbkp$Company.browse");
        addActionProviders.setHandler(new Lookup.Handler() {
            @Override
            public void handleLookup(Collection items) {
                List providersList = new LinkedList(items);
                TypeProviderEntry providerEntry;
                if (getItem().getCreatedBy() == null) {
                    providerEntry = metadata.create(TypeProviderEntry.class);
                    providerEntry.setName((Company) providersList.get(0));
                    providerEntry.setTypeInverse(getItem());
                } else {
                    providerEntry = addEntriesService.addProviderEntry(getItem(), (Company) providersList.get(0));
                }

                if (getItem().getProviders() != null) {
                    getItem().getProviders().add(providerEntry);
                } else {
                    List<TypeProviderEntry> typeProviderEntryList = new LinkedList<>();
                    typeProviderEntryList.add(providerEntry);
                    getItem().setProviders(typeProviderEntryList);
                }
            }
        });//Конец - добавление Поставщика
    }   //добавление Поставщика

}