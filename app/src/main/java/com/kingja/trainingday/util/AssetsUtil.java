package com.kingja.trainingday.util;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Description:TODO
 * Create Time:2017/6/15 16:55
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class AssetsUtil {

    private static AssetsUtil instance;
    private static final int SUCCESS = 1;
    private static final int FAILED = 0;
    private Context context;
    private FileOperateCallback callback;
    private volatile boolean isSuccess;
    private String errorStr;

    public static AssetsUtil getInstance(Context context) {
        if (instance == null)
            instance = new AssetsUtil(context);
        return instance;
    }

    private AssetsUtil(Context context) {
        this.context = context;
    }

    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (callback != null) {
                if (msg.what == SUCCESS) {
                    callback.onSuccess();
                }
                if (msg.what == FAILED) {
                    callback.onFailed(msg.obj.toString());
                }
            }
        }
    };

    public AssetsUtil copyAssetsToSD(final String srcPath, final String sdPath) {
        new Thread(() -> {
            copyAssetsToDst(context, srcPath, sdPath);
            if (isSuccess)
                handler.obtainMessage(SUCCESS).sendToTarget();
            else
                handler.obtainMessage(FAILED, errorStr).sendToTarget();
        }).start();
        return this;
    }

    public void setFileOperateCallback(FileOperateCallback callback) {
        this.callback = callback;
    }

    private void copyAssetsToDst(Context context, String srcPath, String dstPath) {
        try {
            String fileNames[] = context.getAssets().list(srcPath);
            if (fileNames.length > 0) {
                File file = context.getExternalFilesDir(dstPath);
                if (!file.exists()) file.mkdirs();
                for (String fileName : fileNames) {
                    if (!srcPath.equals("")) { // assets 文件夹下的目录

                        copyAssetsToDst(context, srcPath + File.separator + fileName, dstPath + File.separator +
                                fileName);
                    } else { // assets 文件夹
                        copyAssetsToDst(context, fileName, dstPath + File.separator + fileName);

                    }
                }
            } else {
                Log.e("copyAssetsToDst", "copyAssetsToDst: ");
                File outFile = context.getExternalFilesDir(dstPath);
                InputStream is = context.getAssets().open(srcPath);
                FileOutputStream fos = new FileOutputStream(outFile);
                byte[] buffer = new byte[1024];
                int byteCount;
                while ((byteCount = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, byteCount);
                }
                fos.flush();
                is.close();
                fos.close();
            }
            isSuccess = true;
        } catch (Exception e) {
            e.printStackTrace();
            errorStr = e.getMessage();
            isSuccess = false;
        }
    }

    public interface FileOperateCallback {
        void onSuccess();

        void onFailed(String error);
    }

    private void copy(Context context, String fromPath, String savePath) {
        try {
            String fileNames[] = context.getAssets().list(fromPath);
            if (fileNames.length == 0) {
                return;
            }

            for (String fileName : fileNames) {

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
