package com.k.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;

public class LocalPicToDrawable {
	static int BUFFER_SIZE=1024;
	public static BitmapDrawable getImageDrawable(String path)  
	        throws IOException  
	    {  
	        //打开文件   
	        File file = new File(path);  
	        if(!file.exists())  
	        {  
	            return null;  
	        }  
	          
	        ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
	        byte[] bt = new byte[BUFFER_SIZE];  
	          
	        //得到文件的输入流   
	        InputStream in = new FileInputStream(file);  
	          
	        //将文件读出到输出流中   
	        int readLength = in.read(bt);  
	        while (readLength != -1) {  
	            outStream.write(bt, 0, readLength);  
	            readLength = in.read(bt);  
	        }  
	          
	        //转换成byte 后 再格式化成位图   
	        byte[] data = outStream.toByteArray();  
	        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);// 生成位图   
	        BitmapDrawable bd = new BitmapDrawable(bitmap);  
	          
	        return bd;  
	    }  
}
