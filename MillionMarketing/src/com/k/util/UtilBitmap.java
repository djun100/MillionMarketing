package com.k.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;

/**
 * @author Administrator
 *保存Bitmap到本地
 */
public class UtilBitmap {
	 public static boolean  saveBitmap2file(Bitmap bmp,String pathFileName){
         CompressFormat format= Bitmap.CompressFormat.JPEG;
        int quality = 100;
        OutputStream stream = null;
        try {//"/sdcard/" + filename
                stream = new FileOutputStream(pathFileName);
        } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
        }

        return bmp.compress(format, quality, stream);
        }

}
