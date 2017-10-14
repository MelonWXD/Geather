package com.dongua.geather.bean.weather;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class HourlyWeather {

    /**
     * text : 多云
     * code : 4
     * temperature : 28
     * time : 2017-08-30T02:00:00+08:00
     */

    @Id(autoincrement = true)
    private Long id;
    private String city_id;

    private String text;
    private String code;
    private String temperature;
    private String time;

    public HourlyWeather(String text, String code, String temperature, String time) {
        this.text = text;
        this.code = code;
        this.temperature = temperature;
        this.time = time;
    }

    @Generated(hash = 317857911)
    public HourlyWeather(Long id, String city_id, String text, String code, String temperature,
            String time) {
        this.id = id;
        this.city_id = city_id;
        this.text = text;
        this.code = code;
        this.temperature = temperature;
        this.time = time;
    }

    @Generated(hash = 1011422428)
    public HourlyWeather() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCode() {
        return code;
    }

    public int getIntCode() {
        return Integer.parseInt(code);
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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
        return "HourlyWeather{" +
                "city_id='" + city_id + '\'' +
                ", text='" + text + '\'' +
                ", code='" + code + '\'' +
                ", temperature='" + temperature + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
