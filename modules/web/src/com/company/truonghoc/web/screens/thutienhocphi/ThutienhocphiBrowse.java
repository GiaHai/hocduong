package com.company.truonghoc.web.screens.thutienhocphi;

import com.company.truonghoc.entity.Donvi;
import com.company.truonghoc.entity.Thutienhocphi;
import com.company.truonghoc.service.DulieuUserService;
import com.company.truonghoc.service.ServerConfigService;
import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.components.Component;
import com.haulmont.cuba.gui.components.HtmlBoxLayout;
import com.haulmont.cuba.gui.components.LookupField;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.security.global.UserSession;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@UiController("truonghoc_Thutienhocphi.browse")
@UiDescriptor("thutienhocphi-browse.xml")
@LookupComponent("thutienhocphisTable")
@LoadDataBeforeShow
public class ThutienhocphiBrowse extends StandardLookup<Thutienhocphi> {
    @Inject
    protected UiComponents uiComponents;
    @Inject
    protected UserSession userSession;
    @Inject
    protected DulieuUserService dulieuUserService;
    @Inject
    protected CollectionContainer<Donvi> donvisDc;
    @Inject
    protected CollectionLoader<Donvi> donvisDl;
    @Inject
    protected LookupField donvitao_thutienhocphiField;
    @Inject
    protected LookupField trangthaiField;
    @Inject
    protected ServerConfigService serverConfigService;

    @Subscribe
    protected void onInit(InitEvent event) {
        List<String> list = Arrays.asList("Đã thanh toán", "Chưa thanh toán");
        trangthaiField.setOptionsList(list);
    }

    @Subscribe
    protected void onBeforeShow(BeforeShowEvent event) {
        if (dulieuUserService.timbrowerdonvi(userSession.getUser().getLogin()).size() == 0) {
            donvitao_thutienhocphiField.setEditable(false);
            donvitao_thutienhocphiField.setValue(dulieuUserService.timEditdonvi(userSession.getUser().getLogin()).getTendonvi());
        } else {
            donvitao_thutienhocphiField.setEditable(true);

            donvisDl.load();
            List<String> sessionTypeNames = donvisDc.getMutableItems().stream()
                    .map(Donvi::getTendonvi)
                    .collect(Collectors.toList());
            donvitao_thutienhocphiField.setOptionsList(sessionTypeNames);

        }
    }


    public Component checkhan(Thutienhocphi entity) {
        HtmlBoxLayout htmlBoxLayout = uiComponents.create(HtmlBoxLayout.class);
        htmlBoxLayout.setHtmlSanitizerEnabled(true);

        if (entity.getHinhthucthanhtoan() != null) {
            String body = "<a style=\"background-color: #00FFFF; width: 100%;\">ĐÃ THANH TOÁN</a>\n";
            htmlBoxLayout.setTemplateContents(body);
        } else {
            HtmlBoxLayout htmlBoxLayout1 = uiComponents.create(HtmlBoxLayout.class);
            htmlBoxLayout.setHtmlSanitizerEnabled(true);
            Date now = new Date();
            Date han = entity.getDenngay();
            if (now.compareTo(han) >= 0) {
                String body = "<a style=\"background-color: black; color: red ; width: 100%;\">QUÁ HẠN</a>\n";
                htmlBoxLayout1.setTemplateContents(body);
            } else {
                String body = "<a style=\"color: red; width: 100%;\">ĐẾN HẠN</a>\n";
                htmlBoxLayout1.setTemplateContents(body);
            }
            return htmlBoxLayout1;
        }
        return htmlBoxLayout;
    }

//    private String pathPdf;
//    @Inject
//    private JavaScriptComponent printerPdf;
//    private String webBaseFolder;
//
//    public Component test(Thutienhocphi entity) {
//        HtmlBoxLayout htmlBoxLayout = uiComponents.create(HtmlBoxLayout.class);
//        htmlBoxLayout.setHtmlSanitizerEnabled(false);
//        if (entity.getHinhthucthanhtoan() != null){
//            Map<String, Object> parameters = new HashMap<>();
//            parameters.put("nguoitao", entity.getUsertao_thutienhocphi());
//
//            String path = AppContext.getProperty("knkx.template");
//
//            String fileTemplate = WebFunctionHelper.modifiedTemplate(path + "/test.docx", serverConfigService, parameters);
//            String fileName = WebFunctionHelper.convertDocToPdf(fileTemplate, pathPdf, true);
//            if (!StringUtils.isEmpty(fileName)) {
//                List<String> filesPrint = new ArrayList<>();
//                filesPrint.add(fileName);
//                WebFunctionHelper.printFiles(printerPdf, filesPrint,  callbackEvent -> {
//                    if (callbackEvent.getArguments() != null) {
//                        String urlFile = callbackEvent.getArguments().getString(0);
//                        if (!org.apache.commons.lang3.StringUtils.isBlank(urlFile) && !StringUtils.isEmpty(webBaseFolder)) {
//                            GlobalFunctionHelper.deleteFile(webBaseFolder + "/" + urlFile);
//                        }
//                    }
//                });
//            }
//
//            String body = "<button type=\"button\">In phiếu</button>\n";
//            htmlBoxLayout.setTemplateContents(body);
//        }else {
//            String body = "<a></a>";
//            htmlBoxLayout.setTemplateContents(body);
//        }
//        return htmlBoxLayout;
//    }
}