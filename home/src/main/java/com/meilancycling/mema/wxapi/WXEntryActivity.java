package com.meilancycling.mema.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;

import com.meilancycling.mema.MyApplication;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

/**
 * 微信客户端回调activity示例
 */
public class WXEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {
    private IWXAPI api;
    public final static String WX_LOGIN_SUCCESS = "wx_login_success";
    public final static String WX_LOGIN_FAIL = "wx_login_fail";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //取消标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        api = MyApplication.api;
        try {
            boolean result = api.handleIntent(getIntent(), this);
            if (!result) {
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onReq(BaseReq req) {
    }


    @Override
    public void onResp(BaseResp baseResp) {
        if (baseResp.getType() == 1) {
            if (baseResp.errCode == BaseResp.ErrCode.ERR_OK) {
                SendAuth.Resp authResp = (SendAuth.Resp) baseResp;
                Intent intent = new Intent(WX_LOGIN_SUCCESS);
                intent.putExtra("code", authResp.code);
                sendBroadcast(intent);
            } else {
                sendBroadcast(new Intent(WX_LOGIN_FAIL));
            }
            finish();
        } else {
            finish();
        }
    }

}