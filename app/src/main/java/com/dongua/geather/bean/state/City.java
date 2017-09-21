package com.dongua.geather.bean.state;

import com.dongua.geather.db.CityDao;
import com.dongua.geather.db.DaoSession;
import com.dongua.geather.db.RegionDao;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;
import com.dongua.geather.db.DaoSession;
import com.dongua.geather.db.RegionDao;
import com.dongua.geather.db.CityDao;


/**
 * Created by dongua on 17-8-19.
 */
@Entity
public class City  {
    @Id(autoincrement = true)
    private Long id;
    private String name;
    private Long stateId;

    @ToMany(referencedJoinProperty = "cityId")
    List<Region> regionList;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 448079911)
    private transient CityDao myDao;

    @Generated(hash = 1248596315)
    public City(Long id, String name, Long stateId) {
        this.id = id;
        this.name = name;
        this.stateId = stateId;
    }

    @Generated(hash = 750791287)
    public City() {
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

    public Long getStateId() {
        return this.stateId;
    }

    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 2041284143)
    public List<Region> getRegionList() {
        if (regionList == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            RegionDao targetDao = daoSession.getRegionDao();
            List<Region> regionListNew = targetDao._queryCity_RegionList(id);
            synchronized (this) {
                if (regionList == null) {
                    regionList = regionListNew;
                }
            }
        }
        return regionList;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1796823724)
    public synchronized void resetRegionList() {
        regionList = null;
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
    @Generated(hash = 293508440)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getCityDao() : null;
    }


}
