package com.dclee.recovery.util;

import android.app.Activity;
import android.util.Log;

import com.dclee.recovery.R;
import com.dclee.recovery.base.CacheUtil;
import com.dclee.recovery.base.Config;
import com.dclee.recovery.base.NewResponse;
import com.dclee.recovery.base.Response;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

public class RequestUtil {
    private Activity activity;
    public static final int POST = 0;


    public RequestUtil(Activity activity) {
        this.activity = activity;
    }

    public <T> void doPost(String url, RequestParams requestParams, final Class<T> clazz, final OnRequestFinishListener<T> listener) {
        requestParams.addHeader("Authorization", "Bearer" + " " + CacheUtil.getAccessToken());
        Log.e("zkf requestParams", "Bearer" + " " + CacheUtil.getAccessToken());
        requestParams.setUri(activity.getResources().getString(R.string.base_url) + url);
        requestParams.addHeader("content-type","application/json; charset=UTF-8");
        requestParams.setBodyContentType("application/json; charset=UTF-8");
        requestParams.setAsJsonContent(true);

        Log.e("zkf requestParams", requestParams.toString());

        x.http().post(requestParams, new Callback.CacheCallback<String>() {
            @Override
            public boolean onCache(String result) {
                return false;
            }

            @Override
            public void onSuccess(final String resultStr) {
                Log.e("requestParams", resultStr);
                final Response<T> result = FastJsonTools.getResponse(resultStr, clazz);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (result.getCode() == 200 || result.getCode() == 0) {
                            listener.onRequestSuccess(result.getData());
                        } else {
                            ToastUtil.showToast(activity, result.getMessage());
                            listener.onRequestFail(result.getCode(), result.getMessage());
                        }
                    }
                });
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("requestParams", ex.getMessage());
                ex.printStackTrace();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast(activity, "未知错误");
                        listener.onRequestFail(500, "未知错误");
                    }
                });
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    public <T> void doPostWithToken(final String url, RequestParams requestParams, final Class<T> clazz, final OnRequestFinishListener<T> listener) {
//        requestParams.addParameter("serial_no", Config.SERIALNO);
        //requestParams.setBodyContent("application/json;charset=UTF-8");
        requestParams.addHeader("Authorization", "Bearer" + " " + CacheUtil.getAccessToken());
        requestParams.addHeader("content-type","application/json; charset=UTF-8");
        requestParams.setUri(activity.getResources().getString(R.string.base_url) + url);
        requestParams.setBodyContentType("application/json; charset=UTF-8");
        Log.i("requestParams", requestParams.toString());
        requestParams.setAsJsonContent(true);
        x.http().post(requestParams, new Callback.CacheCallback<String>() {
            @Override
            public boolean onCache(String result) {
                return false;
            }

            @Override
            public void onSuccess(final String resultStr) {
                Log.i("requestParams", resultStr);
                final Response<T> result = FastJsonTools.getResponse(resultStr, clazz);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (result.getCode() == 200 || result.getCode() == 0) {
                            Log.i("requestParams", "1111");
                            if (url.contains("sendVerificationCode")) {
                                Log.i("requestParams", "2222");
                                listener.onRequestSuccess(null);
                            } else {
                                listener.onRequestSuccess(result.getData());
                            }
                        } else {
                            ToastUtil.showToast(activity, result.getMessage());
                            listener.onRequestFail(result.getCode(), result.getMessage());
                        }
                    }
                });
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("requestParams", ex.getMessage());
                ex.printStackTrace();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast(activity, "未知错误");
                        listener.onRequestFail(500, "未知错误");
                    }
                });
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    public <T> void doPostWithoutToken(final String url, RequestParams requestParams, final Class<T> clazz, final OnRequestFinishListener<T> listener) {
//        requestParams.addParameter("serial_no", Config.SERIALNO);
        //requestParams.setBodyContent("application/json;charset=UTF-8");
        requestParams.addHeader("content-type","application/json; charset=UTF-8");
        requestParams.setUri(activity.getResources().getString(R.string.base_url) + url);
        requestParams.setBodyContentType("application/json; charset=UTF-8");
        Log.i("requestParams", requestParams.toString());
        requestParams.setAsJsonContent(true);
        x.http().post(requestParams, new Callback.CacheCallback<String>() {
            @Override
            public boolean onCache(String result) {
                return false;
            }

            @Override
            public void onSuccess(final String resultStr) {
                Log.i("requestParams", resultStr);
                final Response<T> result = FastJsonTools.getResponse(resultStr, clazz);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (result.getCode() == 200 || result.getCode() == 0) {
                            Log.i("requestParams", "1111");
                            if (url.contains("sendVerificationCode")) {
                                Log.i("requestParams", "2222");
                                listener.onRequestSuccess(null);
                            } else {
                                listener.onRequestSuccess(result.getData());
                            }
                        } else {
                            ToastUtil.showToast(activity, result.getMessage());
                            listener.onRequestFail(result.getCode(), result.getMessage());
                        }
                    }
                });
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("requestParams", ex.getMessage());
                ex.printStackTrace();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast(activity, "未知错误");
                        listener.onRequestFail(500, "未知错误");
                    }
                });
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    public void doNewPost(final String url, RequestParams requestParams, final OnRequestFinishListener2 listener) {
        requestParams.addParameter("serial_no", Config.SERIALNO);
        requestParams.setUri(activity.getResources().getString(R.string.base_url) + url);
        Log.i("requestParams", requestParams.toString());

        x.http().post(requestParams, new Callback.CacheCallback<String>() {
            @Override
            public boolean onCache(String result) {
                return false;
            }

            @Override
            public void onSuccess(final String resultStr) {
                Log.i("requestParams", resultStr);
                final NewResponse result = FastJsonTools.get(resultStr, NewResponse.class);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (result.getCode() == 200 || result.getCode() == 0) {
                            Log.i("requestParams", "3333" + result.getData());
                            listener.onRequestSuccess(result.getData());
                        } else {
                            ToastUtil.showToast(activity, result.getMessage());
                            listener.onRequestFail(result.getCode(), result.getMessage());
                        }
                    }
                });
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("requestParams", ex.getMessage());
                ex.printStackTrace();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast(activity, "未知错误");
                        listener.onRequestFail(500, "未知错误");
                    }
                });
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    public void doGet(String url, RequestParams requestParams, final OnRequestFinishListener<String> listener) {
        requestParams.setUri(url);
        requestParams.addHeader("Authorization", "Bearer" + " " + CacheUtil.getAccessToken());
        requestParams.addHeader("content-type","application/json; charset=UTF-8");
        requestParams.setUri(activity.getResources().getString(R.string.base_url) + url);
        requestParams.setBodyContentType("application/json; charset=UTF-8");
        Log.i("requestParams", requestParams.toString());
        x.http().get(requestParams, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String resultStr) {
                listener.onRequestSuccess(resultStr);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
                Log.d("zkf","eeeeeee:" + ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }


    public <T> void postList(String url, RequestParams requestParams, final Class<T> clazz, final OnRequestFinishListener<List<T>> listener) {
        String token = CacheUtil.getTokenType() + " " + CacheUtil.getAccessToken();
        requestParams.addHeader("Authorization", token);
        requestParams.addParameter("serial_no", Config.SERIALNO);
        requestParams.setUri(activity.getResources().getString(R.string.base_url) + url);
        Log.e("requestParams", requestParams.toString());
        Log.e("requestParams", token);
        x.http().post(requestParams, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(final String resultStr) {
                Log.e("requestParams", resultStr);
                final Response<List<T>> result = FastJsonTools.getResponseList(resultStr, clazz);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (result.getCode() == 200 || result.getCode() == 0) {
                            listener.onRequestSuccess(result.getData());
                        } else {
                            ToastUtil.showToast(activity, result.getMessage());
                            listener.onRequestFail(result.getCode(), result.getMessage());
                        }
                    }
                });
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
                Log.e("requestParams error : ", ex.getMessage());
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast(activity, "未知错误");
                        listener.onRequestFail(500, "未知错误");
                    }
                });
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }

        });
    }

    public interface OnRequestFinishListener<T> {
        void onRequestSuccess(T result);

        void onRequestFail(int errorCode, String desc);
    }

    public interface OnRequestFinishListener2 {
        void onRequestSuccess(String result);

        void onRequestFail(int errorCode, String desc);
    }
}
