package com.meilancycling.mema.network.bean.response;

/**
 * @author lion
 * GET /security/getkey
 * 获取RSA公钥+私钥缓存KEY
 */
public class GetKeyBean {

    /**
     * "data": {
     * "publicKey": "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCu8zcycV4w4GebqhRRApwTwfEgFGgXXqLhPBGY1dsAIhhp6TBYwv0T6tdbgirF5WT+YQcmwCFHB6RasN21k2sFM6xzwgD0QBjKhEdqsfmucYGwGgA0DZb4Lz/vVyfV6jNZwNSt/6KHldWBvJevPPqfeoc4sjphBh0pCEW3aHqDVwIDAQAB",
     * "cacheKey": "5d0bdd9e-9de3-4308-9f21-e0656ab8877d"
     * }
     */
    private String publicKey;
    private String cacheKey;

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getCacheKey() {
        return cacheKey;
    }

    public void setCacheKey(String cacheKey) {
        this.cacheKey = cacheKey;
    }

}
