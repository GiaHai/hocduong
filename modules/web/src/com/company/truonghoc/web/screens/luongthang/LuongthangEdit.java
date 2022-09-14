package com.company.truonghoc.web.screens.luongthang;

import com.company.truonghoc.entity.ChamcongGv;
import com.company.truonghoc.entity.Giaovien;
import com.company.truonghoc.entity.Luongthang;
import com.company.truonghoc.service.DulieuUserService;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.security.global.UserSession;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@UiController("truonghoc_Luongthang.edit")
@UiDescriptor("luongthang-edit.xml")
@EditedEntityContainer("luongthangDc")
@LoadDataBeforeShow
public class LuongthangEdit extends StandardEditor<Luongthang> {

    @Inject
    protected CollectionLoader<Giaovien> giaoviensDl;
    @Inject
    protected TextField<Long> luongcobanField;
    @Inject
    protected CollectionContainer<Giaovien> giaoviensDc;
    @Inject
    protected TextField<Long> thuclinhField;
    @Inject
    protected TextField<BigDecimal> buoilamField;
    @Inject
    protected TextField<Long> daythemField;
    @Inject
    protected TextField<Long> trocapField;
    @Inject
    protected TextField<Long> trachnhiemField;
    @Inject
    protected TextField<Long> chuyencanField;
    @Inject
    protected TextField<Long> thuongField;
    @Inject
    protected TextField<Long> tonglinhField;
    @Inject
    protected Notifications notifications;
    @Inject
    protected TextField<String> usertao_luongthangField;
    @Inject
    protected TextField<String> donvitao_luongthangField;
    @Inject
    protected DulieuUserService dulieuUserService;
    @Inject
    protected DateField<Date> ngaynhanField;
    @Inject
    protected DataManager dataManager;
    @Inject
    protected LookupField<Giaovien> hovatenField;
    @Inject
    protected DateField<Date> hannhanluongField;
    @Inject
    protected TextField<String> tinhtrangnhanluongField;
    @Inject
    protected TextField<Integer> casangField;
    @Inject
    protected TextField<Integer> cangoaiField;
    Long a = Long.valueOf(0);
    @Inject
    protected UserSession userSession;
    @Inject
    protected LookupField<String> hinhthucthanhtoanField;
    @Inject
    protected DateField<Date> denngayField;
    @Inject
    protected DateField<Date> tungayField;

    @Subscribe
    protected void onInit(InitEvent event) {
        List<String> list = Arrays.asList("Tiền mặt", "Chuyển khoản");
        hinhthucthanhtoanField.setOptionsList(list);


        thuclinhField.setEditable(false);
        tonglinhField.setEditable(false);
        luongcobanField.setEditable(false);
        usertao_luongthangField.setEditable(false);
        donvitao_luongthangField.setEditable(false);
        ngaynhanField.setValue(new Date());
        tinhtrangnhanluongField.setVisible(false);
        hinhthucthanhtoanField.setVisible(false);

    }


    @Subscribe
    protected void onBeforeShow(BeforeShowEvent event) {

        trachnhiemField.setValue(a);
        daythemField.setValue(a);
        trocapField.setValue(a);
        chuyencanField.setValue(a);
        thuongField.setValue(a);
        thuclinhField.setValue(a);
        tinhtonglinh();

        donvitao_luongthangField.setValue(dulieuUserService.timEditdonvi(userSession.getUser().getLogin()).getTendonvi());
        usertao_luongthangField.setValue(userSession.getUser().getLogin());
    }

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        hovatenField.setOptionsList(giaovienList(getEditedEntity().getDonvitao_luongthang()));
    }

    @Subscribe("hovatenField")
    protected void onHovatenFieldValueChange(HasValue.ValueChangeEvent<Giaovien> event) {
        luongcobanField.setValue(hovatenField.getValue().getLuongcoban());
        tinhthuclinh();
        tinhtonglinh();
    }

    @Subscribe("buoilamField")
    protected void onBuoilamFieldValueChange(HasValue.ValueChangeEvent<Long> event) {
        tinhthuclinh();
        tinhtonglinh();
    }
    private void tinhthuclinh(){
        if (luongcobanField.getValue() != null &&
        buoilamField.getValue()  != null){
            BigDecimal b = new BigDecimal(luongcobanField.getValue() / 26.00000000);
            BigDecimal c = b.setScale(10, BigDecimal.ROUND_HALF_EVEN);
            thuclinhField.setValue((long) (c.doubleValue() * buoilamField.getValue().doubleValue()));
        }else {
            thuclinhField.setValue(luongcobanField.getValue());
        }
    }
    private void tinhtonglinh() {
        if (thuclinhField.getValue() != null &&
                trachnhiemField.getValue() != null &&
                daythemField.getValue() != null &&
                trocapField.getValue() != null &&
                chuyencanField.getValue() != null &&
                thuongField.getValue() != null
        ) {
            tonglinhField.setValue(thuclinhField.getValue() + daythemField.getValue() + trocapField.getValue() +
                    trachnhiemField.getValue() + chuyencanField.getValue() + thuongField.getValue());
        }
        if (daythemField.getValue() == null) {
            daythemField.setValue(a);
        }
        if (trachnhiemField.getValue() == null) {
            trachnhiemField.setValue(a);
        }
        if (trocapField.getValue() == null) {
            trocapField.setValue(a);
        }
        if (chuyencanField.getValue() == null) {
            chuyencanField.setValue(a);
        }
        if (thuongField.getValue() == null) {
            thuongField.setValue(a);
        }
    }

    @Subscribe("daythemField")
    protected void onDaythemFieldValueChange(HasValue.ValueChangeEvent<Long> event) {
        tinhtonglinh();
    }

    @Subscribe("trocapField")
    protected void onTrocapFieldValueChange(HasValue.ValueChangeEvent<Long> event) {
        tinhtonglinh();

    }

    @Subscribe("trachnhiemField")
    protected void onTrachnhiemFieldValueChange(HasValue.ValueChangeEvent<Long> event) {
        tinhtonglinh();

    }

    @Subscribe("chuyencanField")
    protected void onChuyencanFieldValueChange(HasValue.ValueChangeEvent<Long> event) {
        tinhtonglinh();

    }

    @Subscribe("thuongField")
    protected void onThuongFieldValueChange(HasValue.ValueChangeEvent<Long> event) {
        tinhtonglinh();

    }


    private List<Giaovien> giaovienList(String dovitao_luongthang) {
        return
                dataManager.load(Giaovien.class)
                        .query("select e from truonghoc_Giaovien e where e.donvitao_giaovien = :donvitao_luongthangField")
                        .parameter("donvitao_luongthangField", dovitao_luongthang)
                        .list();
    }

    @Subscribe("ngaynhanField")
    protected void onNgaynhanFieldValueChange(HasValue.ValueChangeEvent<Date> event) {
        if (ngaynhanField.getValue() == null){
            hannhanluongField.setRequired(true);
            hannhanluongField.setVisible(true);
            tinhtrangnhanluongField.setValue("Chưa nhận lương");
            hinhthucthanhtoanField.setRequired(false);
            hinhthucthanhtoanField.setVisible(false);
        }else {
            hannhanluongField.setVisible(false);
            hannhanluongField.clear();
            tinhtrangnhanluongField.setValue("Đã nhận lương");
            hinhthucthanhtoanField.setRequired(true);
            hinhthucthanhtoanField.setVisible(true);
        }
    }

    @Subscribe("hannhanluongField")
    protected void onHannhanluongFieldValueChange(HasValue.ValueChangeEvent<Date> event) {
        hinhthucthanhtoanField.setVisible(false);
    }

    @Subscribe("timkiemBtn")
    protected void onTimkiemBtnClick(Button.ClickEvent event) {
        int lamcangay = timkiemlamfull(hovatenField.getValue(), tungayField.getValue(), denngayField.getValue()).size();
        int lamcasang = tkbuoisang(hovatenField.getValue(), tungayField.getValue(), denngayField.getValue()).size();
        int lamcachieu= tkbuoichieu(hovatenField.getValue(), tungayField.getValue(), denngayField.getValue()).size();

        buoilamField.setValue(BigDecimal.valueOf(lamcangay + lamcasang*0.5 + lamcachieu*0.5));
        casangField.setValue(lamcasang);
    }

    @Subscribe("cangoaiField")
    protected void onCangoaiFieldValueChange(HasValue.ValueChangeEvent<Integer> event) {
        int lamcangay = timkiemlamfull(hovatenField.getValue(), tungayField.getValue(), denngayField.getValue()).size();
        int lamcasang = tkbuoisang(hovatenField.getValue(), tungayField.getValue(), denngayField.getValue()).size();
        int lamcachieu= tkbuoichieu(hovatenField.getValue(), tungayField.getValue(), denngayField.getValue()).size();

        buoilamField.setValue(BigDecimal.valueOf(lamcangay + lamcasang*0.5 + lamcachieu*0.5 + cangoaiField.getValue()));
        casangField.setValue(lamcasang);
    }

    public List<ChamcongGv> timkiemlamfull(Giaovien hotengv, Date tungay, Date denngay){
        return dataManager.load(ChamcongGv.class)
                .query("select e from truonghoc_ChamcongGv e where e.buoilam = 'Làm cả ngày'" +
                        "and e.ngaylam >= :tungay and :denngay >= e.ngaylam and e.hotenGv = :hotengv")
                .parameter("hotengv", hotengv)
                .parameter("tungay" , tungay)
                .parameter("denngay", denngay)
                .list();
    }

    public List<ChamcongGv> tkbuoisang(Giaovien hotengv, Date tungay, Date denngay){
        return dataManager.load(ChamcongGv.class)
                .query("select e from truonghoc_ChamcongGv e where e.buoilam = 'Ca sáng'" +
                        "and e.ngaylam >= :tungay and :denngay >= e.ngaylam and e.hotenGv = :hotengv")
                .parameter("hotengv", hotengv)
                .parameter("tungay" , tungay)
                .parameter("denngay", denngay)
                .list();
    }
    public List<ChamcongGv> tkbuoichieu(Giaovien hotengv, Date tungay, Date denngay){
        return dataManager.load(ChamcongGv.class)
                .query("select e from truonghoc_ChamcongGv e where e.buoilam = 'Ca chiều'" +
                        "and e.ngaylam >= :tungay and :denngay >= e.ngaylam and e.hotenGv = :hotengv")
                .parameter("hotengv", hotengv)
                .parameter("tungay" , tungay)
                .parameter("denngay", denngay)
                .list();
    }

}