package com.dongua.geather.bean.weather;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by dongua on 17-8-11.
 */
@Entity
public class Future {
    @Id(autoincrement = true)
    private Long id;
    private String city_id;

    //future
    private String date;
    private String day;
    private String high;
    private String low;
    private String text;
    private String wind;
    @Generated(hash = 702584549)
    public Future(Long id, String city_id, String date, String day, String high,
            String low, String text, String wind) {
        this.id = id;
        this.city_id = city_id;
        this.date = date;
        this.day = day;
        this.high = high;
        this.low = low;
        this.text = text;
        this.wind = wind;
    }
    @Generated(hash = 1483558105)
    public Future() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getCity_id() {
        return this.city_id;
    }
    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }
    public String getDate() {
        return this.date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getDay() {
        return this.day;
    }
    public void setDay(String day) {
        this.day = day;
    }
    public String getHigh() {
        return this.high;
    }
    public void setHigh(String high) {
        this.high = high;
    }
    public String getLow() {
        return this.low;
    }
    public void setLow(String low) {
        this.low = low;
    }
    public String getText() {
        return this.text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public String getWind() {
        return this.wind;
    }
    public void setWind(String wind) {
        this.wind = wind;
    }


}
