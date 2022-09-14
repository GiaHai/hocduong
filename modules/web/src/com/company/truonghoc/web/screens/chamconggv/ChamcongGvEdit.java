package com.company.truonghoc.web.screens.chamconggv;

import com.company.truonghoc.entity.ChamcongGv;
import com.company.truonghoc.entity.Giaovien;
import com.company.truonghoc.service.DulieuUserService;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.security.global.UserSession;

import javax.inject.Inject;
import java.util.*;

@UiController("truonghoc_ChamcongGv.edit")
@UiDescriptor("chamcong-gv-edit.xml")
@EditedEntityContainer("chamcongGvDc")
@LoadDataBeforeShow
public class ChamcongGvEdit extends StandardEditor<ChamcongGv> {
    @Inject
    protected DulieuUserService dulieuUserService;
    @Inject
    protected UserSession userSession;
    @Inject
    protected DataManager dataManager;
    @Inject
    protected LookupField<Giaovien> hotenGvField;
    @Inject
    protected TextField<String> donvigvField;
    @Inject
    protected LookupField<String> buoilamField;
    @Inject
    protected DateField<Date> ngaylamField;

    @Subscribe
    protected void onInit(InitEvent event) {
        donvigvField.setEditable(false);
        hotenGvField.setRequired(true);
        ngaylamField.setRequired(true);
        buoilamField.setRequired(true);
        List<String> list = Arrays.asList("Làm cả ngày", "Ca sáng", "Ca chiều");
        buoilamField.setOptionsList(list);
    }

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        hotenGvField.setOptionsList(timtengiaovien(dulieuUserService.timEditdonvi(userSession.getUser().getLogin()).getTendonvi()));
    }

    @Subscribe("hotenGvField")
    protected void onHotenGvFieldValueChange(HasValue.ValueChangeEvent<Giaovien> event) {
        donvigvField.setValue(hotenGvField.getValue().getDonvitao_giaovien());
    }

    public List<Giaovien> timtengiaovien(String donvitao){
        return dataManager.load(Giaovien.class)
                .query("select e from truonghoc_Giaovien e where e.donvitao_giaovien = :donvitao")
                .parameter("donvitao", donvitao)
                .list();
    }
}