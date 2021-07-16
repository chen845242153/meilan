package com.meilancycling.mema.network.bean.response;

/**
 * 注册返回参数。
 */
public class LoginBean {
    /**
     * session : session_pw:5571&ae673ebfb2554454a899657d217472fa
     * id : 5571
     * userCode : 10005558
     * loginName : 13986737306
     * regType : 1
     * nickName : 10005558
     * headerUrl : http://cnweb.meilancycling.com/images/picture/20201014/63831a4fa80b49fbb56a71aac4218bbb.png?Expires=2233374879&OSSAccessKeyId=LTAI1XlOSlLCBlbb&Signature=GxgjE7MWPFXYVFLKBGxc6SUM%2Bd0%3D
     * language : 1
     * sex : 1
     * height : 17400
     * weight : 7400
     * birthday : 2012-03-04
     * phone : 13986737306
     * unit : 1
     * bkUnit : 1
     * targetWeight : 8000
     * newUser : 1
     */

    private String session;
    private int id;
    private int userCode;
    private String loginName;
    private String regType;
    private String nickName;
    private String headerUrl;
    private String language;
    private String sex;
    private String height;
    private String weight;
    private String birthday;
    private String country;
    private String phone;
    private String mail;
    private int targetWeight;
    private int newUser;

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserCode() {
        return userCode;
    }

    public void setUserCode(int userCode) {
        this.userCode = userCode;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getRegType() {
        return regType;
    }

    public void setRegType(String regType) {
        this.regType = regType;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeaderUrl() {
        return headerUrl;
    }

    public void setHeaderUrl(String headerUrl) {
        this.headerUrl = headerUrl;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }



    public int getTargetWeight() {
        return targetWeight;
    }

    public void setTargetWeight(int targetWeight) {
        this.targetWeight = targetWeight;
    }

    public int getNewUser() {
        return newUser;
    }

    public void setNewUser(int newUser) {
        this.newUser = newUser;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }


    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}


