package com.company.truonghoc.web.screens.thutienhocphi;

import com.company.truonghoc.entity.Chitietthu;
import com.company.truonghoc.entity.Hocsinh;
import com.company.truonghoc.entity.Thutienhocphi;
import com.company.truonghoc.service.DulieuUserService;
import com.company.truonghoc.service.ServerConfigService;
import com.company.truonghoc.utils.GlobalFunctionHelper;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.sys.AppContext;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.InstanceContainer;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.security.global.UserSession;
import com.haulmont.cuba.web.gui.components.JavaScriptComponent;
import org.springframework.util.StringUtils;

import javax.inject.Inject;
import java.util.Calendar;
import java.util.*;

@UiController("truonghoc_Thutienhocphi.edit")
@UiDescriptor("thutienhocphi-edit.xml")
@EditedEntityContainer("thutienhocphiDc")
@LoadDataBeforeShow
public class ThutienhocphiEdit extends StandardEditor<Thutienhocphi> {
    @Inject
    protected DateField<Date> denngayField;
    @Inject
    protected DateField<Date> tungayField;
    @Inject
    protected TextField<Long> thanhtienField;
    @Inject
    protected TextField<String> usertaoField;
    @Inject
    protected TextField<String> dovitao_thutienhocphiField;
    @Inject
    protected UserSession userSession;
    @Inject
    protected DulieuUserService dulieuUserService;
    @Inject
    protected LookupField<String> hinhthucthanhtoanField;
    @Inject
    protected TextField<String> tinhtrangthanhtoanField;
    @Inject
    protected LookupField<Hocsinh> tenhocsinhField;
    @Inject
    protected DataManager dataManager;
    @Inject
    protected ServerConfigService serverConfigService;
    @Inject
    protected GroupBoxLayout lkchitietthuBox;
    @Inject
    protected Table<Chitietthu> chitietthusTable;
    @Inject
    protected Button InphieuBtn;

    @Subscribe
    protected void onInit(InitEvent event) {
        List<String> list = Arrays.asList("Ti???n m???t", "Chuy???n kho???n");
        hinhthucthanhtoanField.setOptionsList(list);

        dovitao_thutienhocphiField.setEditable(false);
        usertaoField.setEditable(false);
        thanhtienField.setEditable(false);
        tinhtrangthanhtoanField.setVisible(false);
        if (hinhthucthanhtoanField.getValue() == null)
        {
            InphieuBtn.setEnabled(false);
        }else {
            InphieuBtn.setEnabled(true);
        }
    }

    @Subscribe
    protected void onBeforeShow(BeforeShowEvent event) {
        usertaoField.setValue(userSession.getUser().getLogin());
        dovitao_thutienhocphiField.setValue(dulieuUserService.timEditdonvi(userSession.getUser().getLogin()).getTendonvi());

        Calendar calendar = Calendar.getInstance();
        tungayField.setValue(calendar.getTime());
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        denngayField.setValue(calendar.getTime());

        tinhtrangthanhtoanField.setValue("Ch??a thanh to??n");
    }

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        tenhocsinhField.setOptionsList(hocsinhList(getEditedEntity().getDonvitao_thutienhocphi()));
    }

    private List<Hocsinh> hocsinhList(String dovitao_hocphi) {
        return
                dataManager.load(Hocsinh.class)
                        .query("select e from truonghoc_Hocsinh e where e.donvitao_hocsinh = :donvitao_hocphiField")
                        .parameter("donvitao_hocphiField", dovitao_hocphi)
                        .list();
    }



    @Subscribe(id = "lkchitietthu", target = Target.DATA_CONTAINER)
    protected void onLkchitietthuItemChange(InstanceContainer.ItemChangeEvent<Chitietthu> event) {
        Map<Object, Object> resluts = chitietthusTable.getAggregationResults();

        Object priceId = chitietthusTable.getColumn("tonggia").getId();
        thanhtienField.setValue((Long) resluts.get(priceId));
    }

    @Subscribe("hinhthucthanhtoanField")
    protected void onHinhthucthanhtoanFieldValueChange(HasValue.ValueChangeEvent<String> event) {
        if (hinhthucthanhtoanField.getValue() ==null){
            tinhtrangthanhtoanField.setValue("Ch??a thanh to??n");
        }else {
            tinhtrangthanhtoanField.setValue("???? thanh to??n");
        }
    }

    @Subscribe("hinhthucthanhtoanField")
    protected void onHinhthucthanhtoanFieldValueChange1(HasValue.ValueChangeEvent<String> event) {
        InphieuBtn.setEnabled(true);
    }


//    -----------------In phi???u-----------------
    private String pathPdf;

    @Subscribe("InphieuBtn")
    protected void onInphieuBtnClick(Button.ClickEvent event) {
        Thutienhocphi thutienhocphi = getEditedEntity();
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("nguoitao", thutienhocphi.getUsertao_thutienhocphi());
        parameters.put("donvithanhtoan", thutienhocphi.getDonvitao_thutienhocphi());
        parameters.put("tenkhachhang", thutienhocphi.getTenkhachhang());
        parameters.put("diachi", thutienhocphi.getDiachi());
        parameters.put("tenhocsinh", thutienhocphi.getTenhocsinh().getTenhocsinh());
        parameters.put("thanhtien", thutienhocphi.getThanhtien());
        parameters.put("hinhthucthanhtoan", thutienhocphi.getHinhthucthanhtoan());

        String path = AppContext.getProperty("knkx.template");

        String fileTemplate = WebFunctionHelper.modifiedTemplate(path + "/test.docx", serverConfigService, parameters);
        String fileName = WebFunctionHelper.convertDocToPdf(fileTemplate, pathPdf, true);
        if (!StringUtils.isEmpty(fileName)) {
            List<String> filesPrint = new ArrayList<>();
            filesPrint.add(fileName);
            WebFunctionHelper.printFiles(printerPdf, filesPrint,  callbackEvent -> {
                if (callbackEvent.getArguments() != null) {
                    String urlFile = callbackEvent.getArguments().getString(0);
                    if (!org.apache.commons.lang3.StringUtils.isBlank(urlFile) && !StringUtils.isEmpty(webBaseFolder)) {
                        GlobalFunctionHelper.deleteFile(webBaseFolder + "/" + urlFile);
                    }
                }
            });
        }

    }
    private String webBaseFolder;
    @Inject
    private JavaScriptComponent printerPdf;

    @Subscribe
    protected void onAfterShow1(AfterShowEvent event) {
        pathPdf = AppContext.getProperty("knkx.temp.printer");
        webBaseFolder = AppContext.getProperty("knkx.printer.web.base.folder");

    }

    @Inject
    protected InstanceContainer<Thutienhocphi> thutienhocphiDc;


}