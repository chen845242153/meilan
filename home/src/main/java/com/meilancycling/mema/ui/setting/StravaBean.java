package com.meilancycling.mema.ui.setting;

/**
 * @Description: 作用描述
 * @Author: sore_lion
 * @CreateDate: 2020/10/12 9:15 AM
 */
public class StravaBean {

    /**
     * token_type : Bearer
     * expires_at : 1602486414
     * expires_in : 21144
     * refresh_token : 04488aa44b968e2791e211dbfdf4060b821f8000
     * access_token : f3ba9cc675d84d4258a9502efb358a0e7a8f8a46
     * athlete : {"id":69369480,"username":null,"resource_state":2,"firstname":"choco","lastname":"刘","city":null,"state":null,"country":null,"sex":"M","premium":false,"summit":false,"created_at":"2020-09-29T00:34:43Z","updated_at":"2020-09-29T09:21:14Z","badge_type_id":0,"profile_medium":"https://lh4.googleusercontent.com/--JG2Jy6kdr0/AAAAAAAAAAI/AAAAAAAAAAA/AMZuucnVbZ7OyrJySAUCFvZnQM3RDMSOgg/photo.jpg","profile":"https://lh4.googleusercontent.com/--JG2Jy6kdr0/AAAAAAAAAAI/AAAAAAAAAAA/AMZuucnVbZ7OyrJySAUCFvZnQM3RDMSOgg/photo.jpg","friend":null,"follower":null}
     */

    private String token_type;
    private int expires_at;
    private int expires_in;
    private String refresh_token;
    private String access_token;
    private AthleteBean athlete;

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public int getExpires_at() {
        return expires_at;
    }

    public void setExpires_at(int expires_at) {
        this.expires_at = expires_at;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public AthleteBean getAthlete() {
        return athlete;
    }

    public void setAthlete(AthleteBean athlete) {
        this.athlete = athlete;
    }

    public static class AthleteBean {
        /**
         * id : 69369480
         * username : null
         * resource_state : 2
         * firstname : choco
         * lastname : 刘
         * city : null
         * state : null
         * country : null
         * sex : M
         * premium : false
         * summit : false
         * created_at : 2020-09-29T00:34:43Z
         * updated_at : 2020-09-29T09:21:14Z
         * badge_type_id : 0
         * profile_medium : https://lh4.googleusercontent.com/--JG2Jy6kdr0/AAAAAAAAAAI/AAAAAAAAAAA/AMZuucnVbZ7OyrJySAUCFvZnQM3RDMSOgg/photo.jpg
         * profile : https://lh4.googleusercontent.com/--JG2Jy6kdr0/AAAAAAAAAAI/AAAAAAAAAAA/AMZuucnVbZ7OyrJySAUCFvZnQM3RDMSOgg/photo.jpg
         * friend : null
         * follower : null
         */

        private int id;
        private Object username;
        private int resource_state;
        private String firstname;
        private String lastname;
        private Object city;
        private Object state;
        private Object country;
        private String sex;
        private boolean premium;
        private boolean summit;
        private String created_at;
        private String updated_at;
        private int badge_type_id;
        private String profile_medium;
        private String profile;
        private Object friend;
        private Object follower;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Object getUsername() {
            return username;
        }

        public void setUsername(Object username) {
            this.username = username;
        }

        public int getResource_state() {
            return resource_state;
        }

        public void setResource_state(int resource_state) {
            this.resource_state = resource_state;
        }

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getLastname() {
            return lastname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

        public Object getCity() {
            return city;
        }

        public void setCity(Object city) {
            this.city = city;
        }

        public Object getState() {
            return state;
        }

        public void setState(Object state) {
            this.state = state;
        }

        public Object getCountry() {
            return country;
        }

        public void setCountry(Object country) {
            this.country = country;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public boolean isPremium() {
            return premium;
        }

        public void setPremium(boolean premium) {
            this.premium = premium;
        }

        public boolean isSummit() {
            return summit;
        }

        public void setSummit(boolean summit) {
            this.summit = summit;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public int getBadge_type_id() {
            return badge_type_id;
        }

        public void setBadge_type_id(int badge_type_id) {
            this.badge_type_id = badge_type_id;
        }

        public String getProfile_medium() {
            return profile_medium;
        }

        public void setProfile_medium(String profile_medium) {
            this.profile_medium = profile_medium;
        }

        public String getProfile() {
            return profile;
        }

        public void setProfile(String profile) {
            this.profile = profile;
        }

        public Object getFriend() {
            return friend;
        }

        public void setFriend(Object friend) {
            this.friend = friend;
        }

        public Object getFollower() {
            return follower;
        }

        public void setFollower(Object follower) {
            this.follower = follower;
        }
    }
}
