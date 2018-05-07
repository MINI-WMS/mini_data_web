package com.ltsznh.common.utils.ebdata;

import com.ltsznh.common.utils.R;
import com.ltsznh.common.utils.syncData;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.HashMap;

public class GetDataMeituan {
    private Logger logger = LogManager.getLogger(GetDataMeituan.class);

    String meituanLoginURL = "https://waimaieapp.meituan.com/bizdata/businessStatisticsV3/chain/hisOverview?beginTime=20180412&endTime=20180412";

    String CookieString;

    public String getCookieString() {
        return CookieString;
    }

    public void setCookieString(String cookieString) {
        CookieString = cookieString;
    }

    public void getShopList(){

    }

    public R getSaleData() {
        HashMap<String, Object> params = new HashMap();
        params.put("Cookie", CookieString);
        try {
            R result = syncData.sendGetRequest(meituanLoginURL, params);
            return result;
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e.getCause());
        }
        return R.error(-1, "系统异常，请稍后重试");
    }
}
