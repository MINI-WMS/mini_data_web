package com.ltsznh.common.utils;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.ltsznh.common.model.ResultData;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.*;

/**
 * Created by 涛 on 2015/6/28.
 */
public class Convert {
    private static Logger logger = LogManager.getLogger(Convert.class);


    public static ResultData inputStreamToResultData(InputStream in) {
        return inputStreamToResultData(in, "utf-8");
    }

    public static ResultData inputStreamToResultData(InputStream in,
                                                     String encoding) {

        return new Gson().fromJson(inputStreamToString(in, encoding),
                ResultData.class);
    }

    public static R inputStreamToR(InputStream in) throws Exception {
        return inputStreamToR(in, "utf-8");
    }

    public static R inputStreamToR(InputStream in,
                                   String encoding) throws Exception {
//		String resultString = inputStreamToString(in, encoding);
//		R result = JSON.parseObject(resultString, R.class);
//		return result;
        try {
            return JSON.parseObject(inputStreamToString(in, encoding), R.class);
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e.getCause());
            logger.error(inputStreamToString(in, encoding));
            throw new Exception("系统返回结果错误！");
        }


//		return new Gson().fromJson(inputStreamToString(in, encoding),
//				R.class);
    }


    public static String inputStreamToString(InputStream in) {

        return inputStreamToString(in, "utf-8");
    }

    public static String inputStreamToString(InputStream in, String encoding) {
        String result = "";
        BufferedReader reader = null;
        String lines = "";
        try {
            reader = new BufferedReader(new InputStreamReader(in, encoding));// 设置编码
            while ((lines = reader.readLine()) != null) {
                result = result + lines;
            }
            return result;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String inputStreamToString(InputStreamReader in) {
        // TODO Auto-generated method stub
        return inputStreamToString(in, "utf-8");
    }

    private static String inputStreamToString(InputStreamReader in,
                                              String encoding) {
        // TODO Auto-generated method stub
        String result = "";
        BufferedReader reader = null;
        String lines = "";
        try {
            reader = new BufferedReader(in);// 设置编码
            while ((lines = reader.readLine()) != null) {
                result = result + lines;
            }
            return result;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
