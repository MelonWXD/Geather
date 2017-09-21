package com.dongua.geather.bean.state;

import com.dongua.geather.db.CityDao;
import com.dongua.geather.db.DaoSession;
import com.dongua.geather.db.StateDao;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;
import com.dongua.geather.db.DaoSession;
import com.dongua.geather.db.CityDao;
import com.dongua.geather.db.StateDao;


/**
 * Created by dongua on 17-8-19.
 */
@Entity
public class State  {

    @Id(autoincrement = true)
    private Long id;
    private String name;



    @ToMany(referencedJoinProperty = "stateId")
    List<City> cityList;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 111756178)
    private transient StateDao myDao;

    @Generated(hash = 382098141)
    public State(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Generated(hash = 1543113081)
    public State() {
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

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 142915320)
    public List<City> getCityList() {
        if (cityList == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            CityDao targetDao = daoSession.getCityDao();
            List<City> cityListNew = targetDao._queryState_CityList(id);
            synchronized (this) {
                if (cityList == null) {
                    cityList = cityListNew;
                }
            }
        }
        return cityList;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 879914949)
    public synchronized void resetCityList() {
        cityList = null;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    @Override
    public String toString() {
        return name ;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 134737586)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getStateDao() : null;
    }



}
