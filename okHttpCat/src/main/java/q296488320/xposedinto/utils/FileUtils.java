package q296488320.xposedinto.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

import static android.content.ContentValues.TAG;

/**
 * Created by Lyh on
 * 2019/6/19
 */
public class FileUtils {

    private static final File sdCardDir = Environment.getExternalStorageDirectory();//获取SDCard目录,2.2的时候为:/mnt/sdcart

    /***
     * 调用方式
     *
     * String path = Environment.getExternalStorageDirectory().toString() + "/" + "Tianchaoxiong/useso";
     String modelFilePath = "Model/seeta_fa_v1.1.bin";
     Assets2Sd(this, modelFilePath, path + "/" + modelFilePath);
     *
     * @param context
     * @param fileAssetPath assets中的目录
     * @param fileSdPath 要复制到sd卡中的目录
     */
    public static void Assets2Sd(Context context, String fileAssetPath, String fileSdPath) {
        //测试把文件直接复制到sd卡中 fileSdPath完整路径
        File file = new File(fileSdPath);
        //存在则删掉 重新导入防止0KB
        if (file.exists()) {
            file.delete();
        }
        Log.d(TAG, "************文件不存在,文件创建");
        try {
            copyBigDataToSD(context, fileAssetPath, fileSdPath);
            Log.d(TAG, "************拷贝成功");
        } catch (IOException e) {
            Log.d(TAG, "************拷贝失败");
            e.printStackTrace();
        }


    }

    public static void copyBigDataToSD(Context context, String fileAssetPath, String strOutFileName) throws IOException {
        InputStream myInput;
        OutputStream myOutput = new FileOutputStream(strOutFileName);
        myInput = context.getAssets().open(fileAssetPath);
        byte[] buffer = new byte[1024];
        int length = myInput.read(buffer);
        while (length > 0) {
            myOutput.write(buffer, 0, length);
            length = myInput.read(buffer);
        }

        myOutput.flush();
        myInput.close();
        myOutput.close();
    }

    public static void SaveString(String str, String packageName) {
        FileWriter fw = null;
        try {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                File sdCardDir = Environment.getExternalStorageDirectory();//获取SDCard目录,2.2的时候为:/mnt/sdcart
                // 2.1的时候为：/sdcard，所以使用静态方法得到路径会好一点。
                //String s=Environment.getExternalStorageDirectory();
                File filedir = new File(sdCardDir + File.separator + "OkHttpCat/Http/");  // 这里的AA为创建的AA文件夹，在根目录下
                if (!filedir.exists()) {
                    filedir.mkdirs();
                }


                File saveFile = new File(filedir, packageName + ".txt");
                fw = new FileWriter(saveFile, true);
                PrintWriter pw = new PrintWriter(fw);
                pw.println(str);
                pw.flush();
                try {
                    fw.flush();
                    pw.close();
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {

            e.printStackTrace();

        }
    }


//    public static void SaveLoadPackageFlag(String string,String FileName) {
//
//        FileWriter fw = null;
//        try {
//            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
//
//                // 2.1的时候为：/sdcard，所以使用静态方法得到路径会好一点。
//                //String s=Environment.getExternalStorageDirectory();
//                File filedir = new File(sdCardDir + File.separator + "OkHttpCat/Config/");  // 这里的AA为创建的AA文件夹，在根目录下
//                if (!filedir.exists()) {
//                    filedir.mkdirs();
//                }
//
//
//                File saveFile = new File(filedir, FileName + ".txt");
//                //第二个 参数 是否追加
//                fw = new FileWriter(saveFile, false);
//                PrintWriter pw = new PrintWriter(fw);
//                pw.println(string);
//                pw.flush();
//                try {
//                    fw.flush();
//                    pw.close();
//                    fw.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//        } catch (IOException e) {
//
//            e.printStackTrace();
//
//        }
//    }
//
//
//    public static String readTxtFile(String fileName) {
//        File filedir = new File(sdCardDir + File.separator + "OkHttpCat/Config/");  // 这里的AA为创建的AA文件夹，在根目录下
//        if (!filedir.exists()) {
//            filedir.mkdirs();
//        }
//        File saveFile = new File(filedir, fileName + ".txt");
//
//        StringBuilder sb = new StringBuilder();
//        InputStreamReader read = null;
//        BufferedReader bufferedReader = null;
//        InputStream fi = null;
//        try {
//            String encoding = "UTF-8";
//            fi = new FileInputStream(saveFile);
//
//            read = new InputStreamReader(fi, encoding);// 考虑到编码格式
//            bufferedReader = new BufferedReader(read);
//            String lineTxt;
//            while ((lineTxt = bufferedReader.readLine()) != null) {
//                sb.append(lineTxt);
//            }
//        } catch (Exception e) {
//            CLogUtils.e("读取文件出错  "+e.getMessage());
//            e.printStackTrace();
//        } finally {
//            closeStream(read);
//            closeStream(bufferedReader);
//        }
//        return sb.toString();
//    }


    /**
     * 关闭Stream对象
     */
    public static void closeStream(Closeable stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
                // nothing
            }
        }
    }
}
