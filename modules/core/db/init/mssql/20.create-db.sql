-- begin TRUONGHOC_THUTIENHOCPHI
alter table TRUONGHOC_THUTIENHOCPHI add constraint FK_TRUONGHOC_THUTIENHOCPHI_ON_TENHOCSINH foreign key (TENHOCSINH_ID) references TRUONGHOC_HOCSINH(ID)^
create index IDX_TRUONGHOC_THUTIENHOCPHI_ON_TENHOCSINH on TRUONGHOC_THUTIENHOCPHI (TENHOCSINH_ID)^
-- end TRUONGHOC_THUTIENHOCPHI
-- begin TRUONGHOC_LUONGTHANG
alter table TRUONGHOC_LUONGTHANG add constraint FK_TRUONGHOC_LUONGTHANG_ON_HOVATEN foreign key (HOVATEN_ID) references TRUONGHOC_GIAOVIEN(ID)^
create index IDX_TRUONGHOC_LUONGTHANG_ON_HOVATEN on TRUONGHOC_LUONGTHANG (HOVATEN_ID)^
-- end TRUONGHOC_LUONGTHANG
-- begin TRUONGHOC_DIEMDANH
alter table TRUONGHOC_DIEMDANH add constraint FK_TRUONGHOC_DIEMDANH_ON_HOTENHS foreign key (HOTENHS_ID) references TRUONGHOC_HOCSINH(ID)^
create index IDX_TRUONGHOC_DIEMDANH_ON_HOTENHS on TRUONGHOC_DIEMDANH (HOTENHS_ID)^
-- end TRUONGHOC_DIEMDANH
-- begin TRUONGHOC_HOCPHI
alter table TRUONGHOC_HOCPHI add constraint FK_TRUONGHOC_HOCPHI_ON_HOVATEN foreign key (HOVATEN_ID) references TRUONGHOC_HOCSINH(ID)^
create index IDX_TRUONGHOC_HOCPHI_ON_HOVATEN on TRUONGHOC_HOCPHI (HOVATEN_ID)^
-- end TRUONGHOC_HOCPHI
-- begin TRUONGHOC_CHAMCONG_GV
alter table TRUONGHOC_CHAMCONG_GV add constraint FK_TRUONGHOC_CHAMCONG_GV_ON_HOTEN_GV foreign key (HOTEN_GV_ID) references TRUONGHOC_GIAOVIEN(ID)^
create index IDX_TRUONGHOC_CHAMCONG_GV_ON_HOTEN_GV on TRUONGHOC_CHAMCONG_GV (HOTEN_GV_ID)^
-- end TRUONGHOC_CHAMCONG_GV
-- begin TRUONGHOC_CHITIETTHU
alter table TRUONGHOC_CHITIETTHU add constraint FK_TRUONGHOC_CHITIETTHU_ON_TEN_THUTIENHOCPHI foreign key (TEN_THUTIENHOCPHI_ID) references TRUONGHOC_THUTIENHOCPHI(ID)^
create index IDX_TRUONGHOC_CHITIETTHU_ON_TEN_THUTIENHOCPHI on TRUONGHOC_CHITIETTHU (TEN_THUTIENHOCPHI_ID)^
-- end TRUONGHOC_CHITIETTHU
-- begin SEC_USER
alter table SEC_USER add constraint FK_SEC_USER_ON_LOOCKUP_DONVI foreign key (LOOCKUP_DONVI_ID) references TRUONGHOC_DONVI(ID)^
create index IDX_SEC_USER_ON_LOOCKUP_DONVI on SEC_USER (LOOCKUP_DONVI_ID)^
alter table SEC_USER add constraint FK_SEC_USER_ON_GIAOVIEN foreign key (GIAOVIEN_ID) references TRUONGHOC_GIAOVIEN(ID)^
create index IDX_SEC_USER_ON_GIAOVIEN on SEC_USER (GIAOVIEN_ID)^
-- end SEC_USER
