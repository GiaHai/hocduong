package com.company.truonghoc.entity;

import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.*;
import java.util.Date;

@Table(name = "TRUONGHOC_DIEMDANH")
@Entity(name = "truonghoc_Diemdanh")
public class Diemdanh extends StandardEntity {
    private static final long serialVersionUID = 1305572135769904742L;

    @Column(name = "DONVITAO_DIEMDANH")
    private String donvitao_diemdanh;

    @Column(name = "NGUOITAODD")
    private String nguoitaodd;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "HOTENHS_ID")
    private Hocsinh hotenhs;

    @Column(name = "NGAYNGHI")
    @Temporal(TemporalType.DATE)
    private Date ngaynghi;

    public void setNguoitaodd(String nguoitaodd) {
        this.nguoitaodd = nguoitaodd;
    }

    public String getNguoitaodd() {
        return nguoitaodd;
    }

    public String getDonvitao_diemdanh() {
        return donvitao_diemdanh;
    }

    public void setDonvitao_diemdanh(String donvitao_diemdanh) {
        this.donvitao_diemdanh = donvitao_diemdanh;
    }

    public void setNgaynghi(Date ngaynghi) {
        this.ngaynghi = ngaynghi;
    }

    public Date getNgaynghi() {
        return ngaynghi;
    }

    public Hocsinh getHotenhs() {
        return hotenhs;
    }

    public void setHotenhs(Hocsinh hotenhs) {
        this.hotenhs = hotenhs;
    }
}