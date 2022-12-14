package com.company.truonghoc.web.screens;

import com.company.truonghoc.entity.Donvi;
import com.company.truonghoc.entity.Giaovien;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.gui.app.security.user.edit.UserEditor;
import com.haulmont.cuba.gui.components.CheckBox;
import com.haulmont.cuba.gui.components.HasValue;
import com.haulmont.cuba.gui.components.LookupField;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.screen.Subscribe;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

public class ExtUserEditor extends UserEditor {
    @Inject
    protected CollectionDatasource<Donvi, UUID> donvisDc;
    @Inject
    protected CheckBox donvitrungtam;
    @Inject
    protected LookupField<Donvi> loockuptendonvi;
    @Inject
    protected DataManager dataManager;
    @Inject
    protected TextField<String> tendonviField;
    @Inject
    protected LookupField<Giaovien> tengiaovienField;
    @Inject
    protected TextField<String> TextGiaovienField;

    @Subscribe("loockuptendonvi")
    protected void onLoockuptendonviValueChange(HasValue.ValueChangeEvent<Donvi> event) {
        tendonviField.setValue(donvisDc.getItem().getTendonvi());
        donvitrungtam.setValue(donvisDc.getItem().getDonvitrungtam());
        if (loockuptendonvi.getValue() != null){
            tengiaovienField.setOptionsList(tengiaovien(tendonviField.getValue()));
        }

    }
    @Subscribe("tengiaovienField")
    protected void onTengiaovienFieldValueChange(HasValue.ValueChangeEvent<Donvi> event) {
        TextGiaovienField.setValue(tengiaovienField.getValue().getTengiaovien());
    }
    private List<Giaovien> tengiaovien(String dvgiaovien) {
        return dataManager.load(Giaovien.class)
                .query("select e from truonghoc_Giaovien e where e.donvitao_giaovien = :dvgiaovien")
                .parameter("dvgiaovien", dvgiaovien)
                .list();
    }

    @Subscribe
    protected void onInit(InitEvent event) {
        tendonviField.setVisible(false);
        donvitrungtam.setEditable(false);
    }

}