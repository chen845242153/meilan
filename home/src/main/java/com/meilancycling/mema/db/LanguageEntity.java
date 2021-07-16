package com.meilancycling.mema.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * 语言
 *
 * @author lion 571135591
 */
@Entity
public class LanguageEntity {
    @Id(autoincrement = true)
    private Long id;
    /**
     * 设备语言数据
     */
    private String languageName;
    /**
     * 语言编号
     */
    private Integer num;
    /**
     * 设备语言数据
     */
    private String remark;
    @Generated(hash = 1052455160)
    public LanguageEntity(Long id, String languageName, Integer num,
            String remark) {
        this.id = id;
        this.languageName = languageName;
        this.num = num;
        this.remark = remark;
    }
    @Generated(hash = 1756281788)
    public LanguageEntity() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getLanguageName() {
        return this.languageName;
    }
    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }
    public Integer getNum() {
        return this.num;
    }
    public void setNum(Integer num) {
        this.num = num;
    }
    public String getRemark() {
        return this.remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }


}
