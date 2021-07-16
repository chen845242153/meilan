package com.meilancycling.mema.network.bean.request;

public class Encryption {

    /**
     * password : 123456
     * publicKey : MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCD8SoIC43LPEu/I4OBz1VIQVb3CdE/8UctEy6LqZzj3tfcPbbEOD+nizU67lkQyY71gUMNILrA4Bo5tT1+bC1Idj8Q6EgTVmXot9tCGkCgEi+B+2G5czPs5iauszd3jAz9Nd4hJBrmPRMFNzz3eHty5dR3/X8pTyd2yTRIqlAjqQIDAQAB
     */

    private String password;
    private String publicKey;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }
}
