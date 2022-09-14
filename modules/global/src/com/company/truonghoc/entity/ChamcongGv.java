package com.company.truonghoc.entity;

import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.*;
import java.util.Date;

@Table(name = "TRUONGHOC_CHAMCONG_GV")
@Entity(name = "truonghoc_ChamcongGv")
public class ChamcongGv extends StandardEntity {
    private static final long serialVersionUID = -4541555708534567398L;

    @JoinColumn(name = "HOTEN_GV_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Giaovien hotenGv;

    @Column(name = "DONVIGV")
    private String donvigv;

    @Column(name = "NGAYLAM")
    @Temporal(TemporalType.DATE)
    private Date ngaylam;

    @Column(name = "BUOILAM")
    private String buoilam;

    public void setBuoilam(String buoilam) {
        this.buoilam = buoilam;
    }

    public String getBuoilam() {
        return buoilam;
    }

    public void setNgaylam(Date ngaylam) {
        this.ngaylam = ngaylam;
    }

    public Date getNgaylam() {
        return ngaylam;
    }

    public String getDonvigv() {
        return donvigv;
    }

    public void setDonvigv(String donvigv) {
        this.donvigv = donvigv;
    }

    public void setHotenGv(Giaovien hotenGv) {
        this.hotenGv = hotenGv;
    }

    public Giaovien getHotenGv() {
        return hotenGv;
    }

}