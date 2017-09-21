package com.dongua.geather.bean.state;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by dongua on 17-8-19.
 */
@Entity
public class Region   {
    @Id(autoincrement = true)
    private Long id;
    private String name;

    private Long cityId;

    @Generated(hash = 1831661221)
    public Region(Long id, String name, Long cityId) {
        this.id = id;
        this.name = name;
        this.cityId = cityId;
    }

    @Generated(hash = 600106640)
    public Region() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCityId() {
        return this.cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    @Override
    public String toString() {
        return name ;
    }



}
