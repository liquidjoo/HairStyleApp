package android.app.category;

import java.io.File;
import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

public class ImageUtil {  
  
    /** 
      * ������ �н��� ������ ȭ�� ũ�⿡ �°� �о Bitmap�� ���� 
      * 
      * @param context 
      *       application context 
      * @param imgFilePath 
      *       bitmap file path 
      * @return Bitmap 
      * @throws IOException 
      */  
    public static Bitmap loadBackgroundBitmap(Context context, String imgFilePath) {  
        File file = new File(imgFilePath);  
        if (file.exists() == false) {  
            return null;  
        }  
  
        // ���� ȭ�� ����� ���Ѵ�.  
        Display display = ((WindowManager)context.getSystemService(  
                Context.WINDOW_SERVICE)).getDefaultDisplay();  
        int displayWidth = display.getWidth();  
        int displayHeight = display.getHeight();  
  
        // �о���� �̹����� ����� ���Ѵ�.  
        BitmapFactory.Options options = new BitmapFactory.Options();  
        options.inPreferredConfig = Config.RGB_565;  
        options.inJustDecodeBounds = true;  
        BitmapFactory.decodeFile(imgFilePath, options);  
  
        // ȭ�� ����� ���� �����ϴ� �̹����� ������ ���͸� ���Ѵ�.  
        // ������ ���ʹ� �̹��� �ս��� �ּ�ȭ�ϱ� ���� ¦���� �Ѵ�.  
        float widthScale = options.outWidth / displayWidth;  
        float heightScale = options.outHeight / displayHeight;  
        float scale = widthScale > heightScale ? widthScale : heightScale;  
                  
        if (scale >= 8)  
            options.inSampleSize = 8;  
        else if (scale >= 6)  
            options.inSampleSize = 6;  
        else if (scale >= 4)  
            options.inSampleSize = 4;  
        else if (scale >= 2)  
            options.inSampleSize = 2;  
        else  
            options.inSampleSize = 1;  
        options.inJustDecodeBounds = false;  
  
        return BitmapFactory.decodeFile(imgFilePath, options);  
    }         
  
    /** 
      * ������ �н��� ������ EXIF ������ �о ȸ����ų ���� ���ϱ� 
      * 
      * @param imgFilePath 
      *       bitmap file path 
      * @return degree 
      */  
    public synchronized static int GetExifOrientation(String filepath) {  
        int degree = 0;  
        ExifInterface exif = null;  
          
        try {  
            exif = new ExifInterface(filepath);  
        }   
        catch (IOException e) {  
            Log.e("TAG", "cannot read exif");  
            e.printStackTrace();  
        }  
          
        if (exif != null) {  
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);  
              
            if (orientation != -1) {  
                // We only recognize a subset of orientation tag values.  
                switch(orientation) {  
                    case ExifInterface.ORIENTATION_ROTATE_90:  
                        degree = 90;  
                        break;  
                          
                    case ExifInterface.ORIENTATION_ROTATE_180:  
                        degree = 180;  
                        break;  
                          
                    case ExifInterface.ORIENTATION_ROTATE_270:  
                        degree = 270;  
                        break;  
                }  
            }  
        }  
          
        return degree;  
    }  
  
    /** 
      * ������ �н��� ������ EXIF ������ ���� ȸ����Ű�� 
      * 
      * @param bitmap 
      *       bitmap handle 
      * @return Bitmap 
      */  
    public synchronized static Bitmap GetRotatedBitmap(Bitmap bitmap, int degrees) {  
        if (degrees != 0 && bitmap != null) {  
            Matrix m = new Matrix();  
            m.setRotate(degrees, (float) bitmap.getWidth() / 2,  
                    (float) bitmap.getHeight() / 2 );  
            try {  
                Bitmap b2 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),  
                        bitmap.getHeight(), m, true);  
                if (bitmap != b2) {  
                    bitmap.recycle();  
                    bitmap = b2;  
                }  
            }   
            catch (OutOfMemoryError ex) {  
                // We have no memory to rotate. Return the original bitmap.  
            }  
        }  
          
        return bitmap;  
    }  
}  