package com.brickman.app.common.http;

import android.os.Handler;

import com.brickman.app.MApplication;
import com.brickman.app.common.http.param.RequestParam;
import com.brickman.app.common.utils.AssetUtil;
import com.brickman.app.common.utils.LogUtil;
import com.yolanda.nohttp.BasicBinary;
import com.yolanda.nohttp.Binary;
import com.yolanda.nohttp.InputStreamBinary;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.OnUploadListener;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.download.DownloadListener;
import com.yolanda.nohttp.download.DownloadRequest;
import com.yolanda.nohttp.rest.CacheMode;
import com.yolanda.nohttp.rest.Request;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by mayu on 16/6/21,上午11:27.
 */
public class RequestHelper {
    static HttpUtil mHttpUtil;

    static {
        mHttpUtil = HttpUtil.getRequestInstance();
    }

    public static void sendGETRequest(boolean isCache, final String url, RequestParam params, final HttpListener listener) {
        if (url.contains("DEMO")) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    JSONObject json = AssetUtil.readJSONAssets(url.split("/")[3]);
                    listener.onSucceed(json);
                }
            }, 800);
        } else {
            Request<JSONObject> request = NoHttp.createJsonObjectRequest(url, RequestMethod.GET);
            request.setCacheKey(url + params.toString());
            request.setCacheMode(isCache ? CacheMode.REQUEST_NETWORK_FAILED_READ_CACHE : CacheMode.ONLY_REQUEST_NETWORK);
            request.setHeader("platform", "Android");
            if (MApplication.getInstance().mUser != null) {
                request.setHeader("token", MApplication.getInstance().mUser.token);
            }
            if (params != null) {
                params.append("cVal", params.encrypt());
                HashMap<String, String> paramMap = params.toHashMap();
                for (String key : paramMap.keySet()) {
                    request.add(key, paramMap.get(key));
                }
            }
            request.setCancelSign(url);
            mHttpUtil.add(0, request, listener, true);
        }
    }

    public static void sendPOSTRequest(boolean isCache, final String url, RequestParam params, final HttpListener listener) {
        if (url.contains("DEMO")) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    JSONObject json = AssetUtil.readJSONAssets(url.split("/")[3]);
                    listener.onSucceed(json);
                }
            }, 800);
        } else {
            Request<JSONObject> request = NoHttp.createJsonObjectRequest(url, RequestMethod.POST);
            request.setCacheKey(url + params.toString());
            request.setCacheMode(isCache ? CacheMode.REQUEST_NETWORK_FAILED_READ_CACHE : CacheMode.ONLY_REQUEST_NETWORK);
            request.setHeader("platform", "Android");
            if (MApplication.mAppContext.mUser != null) {
                request.setHeader("token", MApplication.getInstance().mUser.token);
            }
            if (params != null) {
                params.append("cVal", params.encrypt());
                HashMap<String, String> paramMap = params.toHashMap();
                for (String key : paramMap.keySet()) {
                    request.add(key, paramMap.get(key));
                }
            }
            request.setCancelSign(url);
            mHttpUtil.add(0, request, listener, true);
        }
    }

    public static void uploadFile(String url, RequestParam params, List<String> fileList, OnUploadListener onUploadListener, HttpListener<JSONObject> httpListener) {
        LogUtil.info(url);
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(url, RequestMethod.POST);
        request.setHeader("platform", "Android");
        if (MApplication.getInstance().mUser != null) {
            LogUtil.info("token = " + MApplication.mAppContext.mUser.token);
            request.setHeader("token", MApplication.getInstance().mUser.token);
        }
        List<Binary> binaries = new ArrayList<>();
        for (int i = 0; i < fileList.size(); i++) {
            File dir = new File(fileList.get(i));
            if (!dir.exists()) {
                dir.mkdir();
            }
            File file = new File(fileList.get(i));
            BasicBinary binary = null;
            try {
                binary = new InputStreamBinary(new FileInputStream(file), file.getName());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            binary.setUploadListener(i, onUploadListener);
            binaries.add(binary);
        }
        if (params != null) {
            params.append("cVal", params.encrypt());
            HashMap<String, String> paramMap = params.toHashMap();
            for (String key : paramMap.keySet()) {
                request.add(key, paramMap.get(key));
            }
            LogUtil.info(params.toString());
        }
        // 添加FileList到请求
        request.add("files", binaries);
        request.setCancelSign(url);
        mHttpUtil.add(0, request, httpListener, true);
    }

    /**
     * 下载文件
     * @param url
     */
    public static void downloadFile(String url, DownloadListener downloadListener) {
        LogUtil.info("downloadUrl = "+url);
        DownloadRequest downloadRequest = NoHttp.createDownloadRequest(url, RequestMethod.GET, MApplication.SAVE_PIC_PATH, url.substring(url.lastIndexOf("/") + 1), true, true);
        mHttpUtil.downloadQueue.add(0, downloadRequest, downloadListener);
        downloadRequest.start();
    }

    public static void cancelAll(){
        if (mHttpUtil!=null){
            mHttpUtil.cancelAll();
        }
    }
    public static void cancelBySign(Object sign){
        if (mHttpUtil!=null){
            mHttpUtil.cancelBySign(sign);
        }
    }
}
