package com.company.truonghoc.entity;

import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.*;

@Table(name = "TRUONGHOC_CHITIETTHU")
@Entity(name = "truonghoc_Chitietthu")
public class Chitietthu extends StandardEntity {
    private static final long serialVersionUID = -591671652700518729L;

    @Column(name = "TENPHI")
    private String tenphi;

    @Column(name = "SOLUONG")
    private Long soluong;

    @Column(name = "DONGIA")
    private Long dongia;

    @Column(name = "TONGGIA")
    private Long tonggia;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TEN_THUTIENHOCPHI_ID")
    private Thutienhocphi ten_thutienhocphi;

    public void setTonggia(Long tonggia) {
        this.tonggia = tonggia;
    }

    public Long getTonggia() {
        return tonggia;
    }

    public void setSoluong(Long soluong) {
        this.soluong = soluong;
    }

    public Long getSoluong() {
        return soluong;
    }

    public void setDongia(Long dongia) {
        this.dongia = dongia;
    }

    public Long getDongia() {
        return dongia;
    }

    public Thutienhocphi getTen_thutienhocphi() {
        return ten_thutienhocphi;
    }

    public void setTen_thutienhocphi(Thutienhocphi ten_thutienhocphi) {
        this.ten_thutienhocphi = ten_thutienhocphi;
    }

    public String getTenphi() {
        return tenphi;
    }

    public void setTenphi(String tenphi) {
        this.tenphi = tenphi;
    }
}