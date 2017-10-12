package com.dongua.geather.ui.model;

import com.dongua.geather.App;
import com.dongua.geather.AppManager;
import com.dongua.geather.bean.state.City;
import com.dongua.geather.bean.state.Region;
import com.dongua.geather.bean.state.State;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dongua on 17-8-18.
 */

public class LocationModel {


    private boolean isSucceed ;
    String path = "file:///assets/LocList.xml";
    String xmlPath = "/home/duoyi/Downloads/LocList.xml";
    Map<String, Map<String, List<String>>> locMap = new LinkedHashMap<>();
    Map<String, List<String>> cityMap = new LinkedHashMap<>();
    List<String> regionList = new ArrayList<>();


    public boolean saveLocDB() {

        try {
            InputStream is = AppManager.getInstance().getAppContext().getAssets().open("LocList.xml");

            SAXReader reader = new SAXReader();
            Document document = reader.read(is);
            Element root = document.getRootElement();
            Element countryRegion = root.element("CountryRegion");
            List<Element> states = countryRegion.elements();
            Long stateId = 0L;
            Long cityId = 0L;
            for (Element state : states) {



                State s = new State(stateId,state.attribute(0).getText());
                App.getDaoSession().getStateDao().insert(s);


                List<Element> cities = state.elements();
                for (Element city : cities) {
                    City c = new City(cityId,city.attribute(0).getText(),stateId);
                    App.getDaoSession().getCityDao().insert(c);

                    List<Element> regions = city.elements();
                    for (Element region : regions) {
                        Region r = new Region(null,region.attribute(0).getText(),cityId);
                        App.getDaoSession().getRegionDao().insert(r);

                    }
                    cityId++;

                }
                stateId++;
            }
            is.close();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }


        return true;

    }


    public Map getCityInfo() {


        try {

            InputStream is = AppManager.getInstance().getAppContext().getAssets().open("LocList.xml");

            SAXReader reader = new SAXReader();
            Document document = reader.read(is);
            Element root = document.getRootElement();
            Element countryRegion = root.element("CountryRegion");
            List<Element> states = countryRegion.elements();
            for (Element state : states) {
                String stateName = state.attribute(0).getText();
                List<Element> cities = state.elements();
                for (Element city : cities) {
                    String cityName = city.attribute(0).getText();
                    List<Element> regions = city.elements();
                    for (Element region : regions) {
                        String regionName = region.attribute(0).getText();
                        regionList.add(regionName);
                    }
                    cityMap.put(cityName, regionList);
                    regionList = new ArrayList<>();
                }
                locMap.put(stateName, cityMap);
                cityMap = new LinkedHashMap<>();
            }


            //lambda
//            countryRegion.elements().forEach(state -> {
//                String stateName = state.attribute(0).getText();
//                state.elements().forEach(city -> {
//                    String cityName = city.attribute(0).getText();
//                    city.elements().forEach(region -> regionList.add(region.attribute(0).getText()));
//                    cityMap.put(cityName, regionList);
//                    regionList = new ArrayList<>();
//                });
//                locMap.put(stateName,cityMap);
//                cityMap = new LinkedHashMap<>();
//            });

            is.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return locMap;
    }


//    public static void main(String[] args) {
//        System.out.println(        Utils.cn2Spell("北京"));
//    }

}
