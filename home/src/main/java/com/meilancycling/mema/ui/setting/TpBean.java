package com.meilancycling.mema.ui.setting;

/**
 * @Description: 作用描述
 * @Author: sore_lion
 * @CreateDate: 3/21/21 4:42 PM
 */
public class TpBean {

    /**
     * access_token : gAAAAKU1x4BLGSwPLBbr_my7nUZPRZ2nbElhCjFtuPPr69hK7n3JLLisFYBvcGw4eDJKHQOwbm5X6EJxaog6e_c1U3-9AclhVcsVPPvca5v6bISeF5_zv2HpDlAjTBeuhs0DLLcH_ao2R1wH5Ib1SoiMaHxKyjT4UpG6XVI8GlxISCzKJAEAAIAAAAChrYLYwyBRSNimQNXCH2vefVOJ5aQlTPP3LiIs0_CdTmfmsnnQXc7ToKEPgpbTt8kKIukMRRcNTJkRSAORQxuw0HlRmgGPYtNnj4rCJ6NSR7Sod0T-NNa54Q4NcuyUOQ8IgiGy-EfXXq-Gr0gPjQZ2NHT9BvlF6n_2i8tuLeJ4dtdiT2wR9eRAgx9DZFtNM-I1azzxzJKwr2vgHtLbi66sJnpBrHOP8y119h30r7UtgiAbt3MxvYL_2L2LSRx3tvS78UQtDVv81hHCSP0k7I7A4t96Nyyvt0oEZ2S4mjvfX6WvpHeoaAl5H_O5gCjvWPOEjVFOg40k5Q_5Wuqzc2ZXhT2SyN_L1q2_r83KD8Mce0wAAkEMTnPC8jHFMqaCAoI
     * token_type : bearer
     * expires_in : 3600
     * refresh_token : r1BC!IAAAALFJ-vMkDzvYzskt5gM6RUcP7nFj_IGfWj2cuPbR7y2nsQAAAAH2VgDZfMVcZlJ0lBghli-dKFMwooP_1_d9H-TBqddhTET6cF3cSiWtbFW0VaJFuo772Km7FjrXj_j2G2OSQofClQA1LXWwucDmOwvbphE4dc5HjBVDyq8jh3KOdcWbrJ1fkP_EoP1mRIqxvVAfrDFvjZy8bkGkHrY8OHKDAzV_yVeYVj52-z8uzdJmDIKLvutvLUtBnG8y_HWPiYJini7dsgXVVI4l5N1YXjvnK3pOwA
     * scope : file:write
     */

    private String access_token;
    private String token_type;
    private int expires_in;
    private String refresh_token;
    private String scope;

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

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
