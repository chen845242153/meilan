package com.meilancycling.mema.ui.setting;

/**
 * @Description: 作用描述
 * @Author: sore_lion
 * @CreateDate: 2020/10/12 5:11 PM
 */
public class KomootBean {

    /**
     * access_token : eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2MDI0OTU0OTAsInVzZXJfbmFtZSI6IjE2MzE3MDMzMTA2NzAiLCJqdGkiOiJjYzk0MWEyOC04NTk0LTQ5NGItOTJlZS1hOGM2MmMxMDc4YTYiLCJjbGllbnRfaWQiOiJtZWlsYW4tazl2OGpzIiwic2NvcGUiOlsicHJvZmlsZSJdLCJ1c2VybmFtZSI6IjE2MzE3MDMzMTA2NzAifQ.S_8zZ5lVPncnhQlNsdx-gczh4mBYkdqwM1deiFYPXNNkoCwqfoj2XvKWpkwo6qgOrED6h9DflhcMHYWjUfkBWdz3a2jBjJF9jsgYC1u50iUq6u-gUX-S1oa71wgiyZOATfGOhNWmQut5T7Q5Ky5dXTHOe-Q4Tah_c3Ad__4TAophT964o4zY6sB8sTPvQrhpyM7zDT_rBZoYudpUn390sN1EKjFZa746NBhVfxE5D1puk60aMrJ-IMvAY7M3Dh91zODGgKo1bJvY_9i6cHn8bquqH7Bit7t-kn-w3DBMkSqX5VQANVMM9P9QUkFAmw42S7ezxilsZjXj2E90T1ngCw
     * token_type : bearer
     * refresh_token : /1631703310670/meilan-k9v8js/8600e126-83e7-45ce-b559-d748b6f8bc53
     * expires_in : 1799
     * scope : profile
     * username : 1631703310670
     * jti : cc941a28-8594-494b-92ee-a8c62c1078a6
     */

    private String access_token;
    private String token_type;
    private String refresh_token;
    private int expires_in;
    private String scope;
    private String username;
    private String jti;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getJti() {
        return jti;
    }

    public void setJti(String jti) {
        this.jti = jti;
    }
}
