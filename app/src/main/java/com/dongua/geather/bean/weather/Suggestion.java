package com.dongua.geather.bean.weather;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by dongua on 17-8-6.
 */

@Entity
public class Suggestion {
    @Id(autoincrement = true)
    private Long id;
    private Long city_id;
    private String name;
    private String brief;
    private String details;
    @Generated(hash = 971078841)
    public Suggestion(Long id, Long city_id, String name, String brief,
                      String details) {
        this.id = id;
        this.city_id = city_id;
        this.name = name;
        this.brief = brief;
        this.details = details;
    }
    @Generated(hash = 998731485)
    public Suggestion() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getCity_id() {
        return this.city_id;
    }
    public void setCity_id(Long city_id) {
        this.city_id = city_id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getBrief() {
        return this.brief;
    }
    public void setBrief(String brief) {
        this.brief = brief;
    }
    public String getDetails() {
        return this.details;
    }
    public void setDetails(String details) {
        this.details = details;
    }

    

}
