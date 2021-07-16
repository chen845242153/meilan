package com.meilancycling.mema.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 文件上传
 *
 * @author lion
 */
@Entity
public class FileUploadEntity {
    @Id(autoincrement = true)
    private Long id;
    private long userId;
    /**
     * 运动类型
     *
     * 1骑车，2骑行台，3骑行竞赛，7其它
     */
    private int sportsType;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 产品编号
     */
    private String productNo;
    /**
     * 时区 对应的bat文件才上传
     */
    private int timeZone;

    private String filePath;

    @Generated(hash = 664742228)
    public FileUploadEntity(Long id, long userId, int sportsType, String fileName,
            String productNo, int timeZone, String filePath) {
        this.id = id;
        this.userId = userId;
        this.sportsType = sportsType;
        this.fileName = fileName;
        this.productNo = productNo;
        this.timeZone = timeZone;
        this.filePath = filePath;
    }

    @Generated(hash = 1354560716)
    public FileUploadEntity() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getUserId() {
        return this.userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getSportsType() {
        return this.sportsType;
    }

    public void setSportsType(int sportsType) {
        this.sportsType = sportsType;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getProductNo() {
        return this.productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public int getTimeZone() {
        return this.timeZone;
    }

    public void setTimeZone(int timeZone) {
        this.timeZone = timeZone;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    

}
