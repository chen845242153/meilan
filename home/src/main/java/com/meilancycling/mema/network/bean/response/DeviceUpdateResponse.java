package com.meilancycling.mema.network.bean.response;

/**
 * @Description: 作用描述
 * @Author: sore_lion
 * @CreateDate: 2021/1/12 9:37 AM
 */
public class DeviceUpdateResponse {

    /**
     * sdkTV : 0
     * softTV : 2
     * bootTV : 2
     * externalTV :
     * deviceSoftwareTV : 0
     * softwareTV : 4.8
     * hardwareTV : 3.1
     * twoUrl : http://cnweb.meilancycling.com/hardware/version/product10101/file2/OTA-FINDER-V4.0-europe-20201124.zip?Expires=2237508260&OSSAccessKeyId=LTAI1XlOSlLCBlbb&Signature=6ha5h%2BSJUVus7aoblcu1YDLIBNs%3D
     * twoSize : 297765
     * twoMes : 修复以下问题：
     * .设备上传记录，部分手机未能成功接收
     * twoMesEn : Fix the following issues:
     * .Device upload records, some mobile phones failed to receive
     * versionNo : 49
     * plib : 2
     * flib : 2
     * attachPara : 1
     */

    private String sdkTV;
    private String softTV;
    private String bootTV;
    private String externalTV;
    private String deviceSoftwareTV;
    private String softwareTV;
    private String hardwareTV;
    private String twoUrl;
    private String twoSize;
    private String twoMes;
    private String twoMesEn;
    private String versionNo;
    private String plib;
    private String flib;
    private String attachPara;

    public String getSdkTV() {
        return sdkTV;
    }

    public void setSdkTV(String sdkTV) {
        this.sdkTV = sdkTV;
    }

    public String getSoftTV() {
        return softTV;
    }

    public void setSoftTV(String softTV) {
        this.softTV = softTV;
    }

    public String getBootTV() {
        return bootTV;
    }

    public void setBootTV(String bootTV) {
        this.bootTV = bootTV;
    }

    public String getExternalTV() {
        return externalTV;
    }

    public void setExternalTV(String externalTV) {
        this.externalTV = externalTV;
    }

    public String getDeviceSoftwareTV() {
        return deviceSoftwareTV;
    }

    public void setDeviceSoftwareTV(String deviceSoftwareTV) {
        this.deviceSoftwareTV = deviceSoftwareTV;
    }

    public String getSoftwareTV() {
        return softwareTV;
    }

    public void setSoftwareTV(String softwareTV) {
        this.softwareTV = softwareTV;
    }

    public String getHardwareTV() {
        return hardwareTV;
    }

    public void setHardwareTV(String hardwareTV) {
        this.hardwareTV = hardwareTV;
    }

    public String getTwoUrl() {
        return twoUrl;
    }

    public void setTwoUrl(String twoUrl) {
        this.twoUrl = twoUrl;
    }

    public String getTwoSize() {
        return twoSize;
    }

    public void setTwoSize(String twoSize) {
        this.twoSize = twoSize;
    }

    public String getTwoMes() {
        return twoMes;
    }

    public void setTwoMes(String twoMes) {
        this.twoMes = twoMes;
    }

    public String getTwoMesEn() {
        return twoMesEn;
    }

    public void setTwoMesEn(String twoMesEn) {
        this.twoMesEn = twoMesEn;
    }

    public String getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(String versionNo) {
        this.versionNo = versionNo;
    }

    public String getPlib() {
        return plib;
    }

    public void setPlib(String plib) {
        this.plib = plib;
    }

    public String getFlib() {
        return flib;
    }

    public void setFlib(String flib) {
        this.flib = flib;
    }

    public String getAttachPara() {
        return attachPara;
    }

    public void setAttachPara(String attachPara) {
        this.attachPara = attachPara;
    }
}
