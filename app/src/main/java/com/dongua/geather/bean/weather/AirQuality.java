package com.dongua.geather.bean.weather;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by duoyi on 17-10-18.
 */
@Entity
public class AirQuality {

    /**
     * aqi : 31
     * pm25 : 16
     * pm10 : 31
     * so2 : 7
     * no2 : 33
     * co : 0.736
     * o3 : 50
     * last_update : 2017-10-18T18:00:00+08:00
     * quality : 优
     */
    @Id(autoincrement = true)
    private Long id;
    private String city_id;
    private String aqi;
    private String pm25;
    private String pm10;
    private String so2;
    private String no2;
    private String co;
    private String o3;
    private String last_update;
    private String quality;

    @Generated(hash = 1448920002)
    public AirQuality(Long id, String city_id, String aqi, String pm25, String pm10,
            String so2, String no2, String co, String o3, String last_update,
            String quality) {
        this.id = id;
        this.city_id = city_id;
        this.aqi = aqi;
        this.pm25 = pm25;
        this.pm10 = pm10;
        this.so2 = so2;
        this.no2 = no2;
        this.co = co;
        this.o3 = o3;
        this.last_update = last_update;
        this.quality = quality;
    }

    @Generated(hash = 887331335)
    public AirQuality() {
    }

    public String getAqi() {
        return aqi;
    }

    public void setAqi(String aqi) {
        this.aqi = aqi;
    }

    public String getPm25() {
        return pm25;
    }

    public void setPm25(String pm25) {
        this.pm25 = pm25;
    }

    public String getPm10() {
        return pm10;
    }

    public void setPm10(String pm10) {
        this.pm10 = pm10;
    }

    public String getSo2() {
        return so2;
    }

    public void setSo2(String so2) {
        this.so2 = so2;
    }

    public String getNo2() {
        return no2;
    }

    public void setNo2(String no2) {
        this.no2 = no2;
    }

    public String getCo() {
        return co;
    }

    public void setCo(String co) {
        this.co = co;
    }

    public String getO3() {
        return o3;
    }

    public void setO3(String o3) {
        this.o3 = o3;
    }

    public String getLast_update() {
        return last_update;
    }

    public void setLast_update(String last_update) {
        this.last_update = last_update;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
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

    @Override
    public String toString() {
        return "AirQuality{" +
                "id=" + id +
                ", city_id='" + city_id + '\'' +
                ", aqi='" + aqi + '\'' +
                ", pm25='" + pm25 + '\'' +
                ", pm10='" + pm10 + '\'' +
                ", so2='" + so2 + '\'' +
                ", no2='" + no2 + '\'' +
                ", co='" + co + '\'' +
                ", o3='" + o3 + '\'' +
                ", last_update='" + last_update + '\'' +
                ", quality='" + quality + '\'' +
                '}';
    }
}
