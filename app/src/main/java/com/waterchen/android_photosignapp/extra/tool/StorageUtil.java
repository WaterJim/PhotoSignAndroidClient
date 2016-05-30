package com.waterchen.android_photosignapp.extra.tool;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Path;
import android.hardware.Camera;
import android.os.Environment;
import android.os.StatFs;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by seasonyuu on 16-5-9.
 */
public class StorageUtil {
    private static final String PATH = Environment.getExternalStorageDirectory() + "/CameraFir/";

    public interface OnSaveCallback {
        void onSaved(boolean isSucceed);
    }

    public static String getRootPath() {
        return PATH;
    }

    public static String getRootFilePath(int classId) {
        return PATH + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "." + classId;
    }

    public static boolean savePicture(final Context context, Camera camera, final double percent,
                                      final String id, final int num, final OnSaveCallback onSaveCallback) {
        camera.takePicture(new Camera.ShutterCallback() {
            @Override
            public void onShutter() {

            }
        }, null, new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                // 判断是否存在SD卡
                if (Environment.getExternalStorageState().equals(
                        Environment.MEDIA_MOUNTED)) {
                    try {
                        // 将拍的照片保存到sd卡中
                        File folder = new File(PATH + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "." + id);
                        if (!folder.exists()) {
                            folder.mkdirs();
                        }
                        File jpgFile = new File(folder.getPath() + "/"
                                + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "_" + num + ".jpg");
                        FileOutputStream outputStream = new FileOutputStream(
                                jpgFile);


                        outputStream.write(data);
                        outputStream.close();

                        Bitmap bitmap = BitmapFactory.decodeFile(jpgFile.getPath());
                        int resultSize = (int) Math.sqrt(bitmap.getWidth() * bitmap.getHeight() * percent);
                        int height = camera.getParameters().getPictureSize().width;
                        int width = camera.getParameters().getPictureSize().height;
                        int beginX = (width - resultSize) / 2;
                        int beginY = (height - resultSize) / 2;
                        Bitmap resultBitmap = Bitmap.createBitmap(bitmap, beginX, beginY, resultSize, resultSize);

                        outputStream = new FileOutputStream(
                                jpgFile);
                        resultBitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream);
                        outputStream.flush();
                        outputStream.close();

                        camera.startPreview();
                        if (onSaveCallback != null)
                            onSaveCallback.onSaved(true);
                    } catch (IOException e) {
                        e.printStackTrace();
                        if (onSaveCallback != null)
                            onSaveCallback.onSaved(false);
                    }
                } else {
                    Toast.makeText(context, "不存在外部存储", Toast.LENGTH_LONG).show();
                }
            }
        });
        return true;
    }

    /**
     * 获取机身内存总大小
     *
     * @return
     */
    public static long getRomTotalSize() {
        StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
        long blockSize = statFs.getBlockSize();
        long totalBlocks = statFs.getBlockCount();
        return blockSize * totalBlocks;
    }

    /**
     * 获取机身可用内存大小
     *
     * @return
     */
    public static long getRomAvailableSize() {
        StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
        long blockSize = statFs.getBlockSize();
        long availableBlocks = statFs.getAvailableBlocks();
        return blockSize * availableBlocks;
    }
}
