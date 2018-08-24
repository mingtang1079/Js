package com.example.administrator.js.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import com.appbaselib.utils.BitmapUtil;
import com.example.administrator.js.R;
import com.example.administrator.js.constant.Constans;
import com.example.administrator.js.qrcode.utils.Constants;
import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.api.BaseMediaObject;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.share.WbShareHandler;
import com.sina.weibo.sdk.utils.Utility;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class ShareUtils {
    public static final String WX_APP_ID = Constans.weixin;//改成你在微信开放平台审核通过的appID
    public static final String QQ_APP_ID = "1107062758";//改成你在QQ开放平台审核通过的appID
    public static final String sina = "1049244472";

    private IWXAPI iwxapi;
    private Tencent tencent;

    public ShareUtils() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * 要分享必须先注册(微信)
     */
    public void regToWX(Context context) {

        iwxapi = WXAPIFactory.createWXAPI(context, WX_APP_ID, true);
        iwxapi.registerApp(WX_APP_ID);

    }

    /**
     * 要分享必须先注册(QQ)
     */
    public void regToQQ(Context context) {
        tencent = Tencent.createInstance(QQ_APP_ID, context);
    }


    public IWXAPI getIwxapi() {
        return iwxapi;
    }

    public void setIwxapi(IWXAPI iwxapi) {
        this.iwxapi = iwxapi;
    }

    public Tencent getTencent() {
        return tencent;
    }

    public void setTencent(Tencent tencent) {
        this.tencent = tencent;
    }

    public String getWxAppId() {
        return WX_APP_ID;
    }

    public String getQqAppId() {
        return QQ_APP_ID;
    }


    /**
     * 分享到短信
     */
    public Intent sendSMS(String description) {

        Uri smsToUri = Uri.parse("smsto:");
        Intent sendIntent = new Intent(Intent.ACTION_VIEW, smsToUri);
        //sendIntent.putExtra("address", "123456"); // 电话号码，这行去掉的话，默认就没有电话
        sendIntent.putExtra("sms_body", description);
        sendIntent.setType("vnd.android-dir/mms-sms");

        return sendIntent;

    }


    /**
     * 分享到微信好友
     */
    public void shareToWXSceneSession(String url, String shareTitle, String description) {

        WXWebpageObject webpageObject = new WXWebpageObject();
        webpageObject.webpageUrl = url;
        WXMediaMessage mWxMediaMessage = new WXMediaMessage(webpageObject);
        mWxMediaMessage.title = shareTitle;
        mWxMediaMessage.description = description;
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());//transaction字段用于唯一标识一个请求
        req.message = mWxMediaMessage;
        req.scene = SendMessageToWX.Req.WXSceneSession;
        iwxapi.sendReq(req);

    }


    /**
     * 分享到微信收藏
     */
    public void shareToWXSceneFavorite(String url, String shareTitle, String description) {

        WXWebpageObject webpageObject = new WXWebpageObject();
        webpageObject.webpageUrl = url;
        WXMediaMessage wxMediaMessage = new WXMediaMessage(webpageObject);
        wxMediaMessage.title = shareTitle;
        wxMediaMessage.description = description;
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = wxMediaMessage;
        req.scene = SendMessageToWX.Req.WXSceneFavorite;
        iwxapi.sendReq(req);
    }


    /**
     * 分享到微信朋友圈
     */
    public void shareToWXSceneTimeline(String url, String shareTitle, String description, Drawable drawable) {

        WXWebpageObject webpageObject = new WXWebpageObject();
        webpageObject.webpageUrl = url;
        WXMediaMessage mediaMessage = new WXMediaMessage(webpageObject);
        mediaMessage.title = shareTitle;
        mediaMessage.description = description;
        if (drawable != null) {
            BitmapDrawable bd = (BitmapDrawable) drawable;
            Bitmap thumBmp = bd.getBitmap();
            thumBmp = Bitmap.createScaledBitmap(thumBmp, 150, 150, true);//图片大小有限制，太大分享不了
            mediaMessage.thumbData =bmpToByteArray(thumBmp,true);
        }
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = mediaMessage;
        req.scene = SendMessageToWX.Req.WXSceneTimeline;
        iwxapi.sendReq(req);

    }


    //    /**分享到QQ好友*/
    public void shareToQQ(Activity activity, String url, String shareTitle, String description, IUiListener uiListener) {

        Bundle qqParams = new Bundle();
        qqParams.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        qqParams.putString(QQShare.SHARE_TO_QQ_TITLE, shareTitle);
        qqParams.putString(QQShare.SHARE_TO_QQ_SUMMARY, description);
        qqParams.putString(QQShare.SHARE_TO_QQ_TARGET_URL, url);
        //qqParams.putString(QQShare.SHARE_TO_QQ_APP_NAME,  "APP名称");
        tencent.shareToQQ(activity, qqParams, uiListener);

    }


    /**
     * 分享到QQ空间
     */
    public void shareToQzone(Activity activity, String url, String imageUrl, String shareTitle, String description, IUiListener uiListener) {

        Bundle qzoneParams = new Bundle();
        qzoneParams.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
        qzoneParams.putString(QzoneShare.SHARE_TO_QQ_TITLE, shareTitle);//必填
        qzoneParams.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, description);
        qzoneParams.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, url);//必填
        ArrayList<String> imageUrlList = new ArrayList<String>();
        imageUrlList.add(imageUrl);
        qzoneParams.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imageUrlList);
        tencent.shareToQzone(activity, qzoneParams, uiListener);

    }

    //微博


    /**
     * 发送多种消息到微博
     */
    public void sendMultiMessageToWeibo(Context mContext, String text, WbShareHandler mWbShareHandler) {

        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();//初始化微博的分享消息
        //文字
        weiboMessage.textObject = getTextObj(text);
        mWbShareHandler.shareMessage(weiboMessage, false);
    }

    /**
     * 获取文本信息对象
     *
     * @return TextObject
     */
    private TextObject getTextObj(String text) {
        TextObject textObject = new TextObject();
        textObject.text = text;//这里输入文本
        return textObject;
    }

    /**
     * 创建图片消息对象。
     *
     * @return 图片消息对象。
     */
    private ImageObject getImageObj(Bitmap bitmap) {
        ImageObject imageObject = new ImageObject();
        imageObject.setImageObject(bitmap);
        return imageObject;
    }

//    /**
//     * 创建网页对象
//     *
//     * @return
//     */
//    private BaseMediaObject getWebpageObj() {//这个方法里面一个参数都不能少，开始以为描述和设置图片参数是没用的，其实它是当你没网时或失败时用的
//        WebpageObject mediaObject = new WebpageObject();
//        mediaObject.identify = Utility.generateGUID();
//        mediaObject.title = product.getSpuDetail().getName() + product.getSpuDetail().getBriefDescription();
//        mediaObject.description = "萌趣用着真的是好啊！";
//        mediaObject.setThumbImage(BitmapFactory.decodeResource(getResources(), R.mipmap.icon));
//        mediaObject.actionUrl = cons.USUAL_ISSUE_URL;
//        return mediaObject;
//    }

    public  byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {

        int i;
        int j;
        if (bmp.getHeight() > bmp.getWidth()) {
            i = bmp.getWidth();
            j = bmp.getWidth();
        } else {
            i = bmp.getHeight();
            j = bmp.getHeight();
        }

        Bitmap localBitmap = Bitmap.createBitmap(i, j, Bitmap.Config.RGB_565);
        Canvas localCanvas = new Canvas(localBitmap);

        while (true) {
            localCanvas.drawBitmap(bmp, new Rect(0, 0, i, j), new Rect(0, 0,i, j), null);
            if (needRecycle)
                bmp.recycle();
            ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
            localBitmap.compress(Bitmap.CompressFormat.JPEG, 100,
                    localByteArrayOutputStream);
            localBitmap.recycle();
            byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
            try {
                localByteArrayOutputStream.close();
                return arrayOfByte;
            } catch (Exception e) {
                //F.out(e);
            }
            i = bmp.getHeight();
            j = bmp.getHeight();
        }

    }

}
