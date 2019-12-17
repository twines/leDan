package com.twins.lee.utilites;

import cn.hutool.extra.qrcode.BufferedImageLuminanceSource;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.common.HybridBinarizer;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLDecoder;
import java.util.*;

import static org.apache.catalina.manager.Constants.CHARSET;

public class Utility {

    public static Map decodeLeDanInfo(Map<String, Object> userInfo) throws UnsupportedEncodingException {
        for (String key : userInfo.keySet()) {
            String userInfoValue = (String) userInfo.get(key);
            userInfoValue = URLDecoder.decode(userInfoValue, "utf-8");
            userInfo.put(key, userInfoValue);
        }
        return userInfo;
    }

    public static Long userId() {
        String strId = (String) userInfo().get("id");
        return Long.parseLong(strId);
    }

    public static Map userInfo() {
        Subject subject = SecurityUtils.getSubject();
        // 第一个放的是id
        //第二放的是用户json
        List infos = subject.getPrincipals().asList();
        if (infos.size() == 1) {
            return null;
        }

        return (Map) infos.get(1);
    }

    public static List<String> pdfToImagePath(String filePath) {
        List<String> list = new ArrayList<>();
        String fileDirectory = filePath.substring(0, filePath.lastIndexOf("."));//获取去除后缀的文件路径

        String imagePath;
        File file = new File(filePath);
        try {
            File f = new File(fileDirectory);
            if (!f.exists()) {
                f.mkdir();
            }
            PDDocument doc = PDDocument.load(file);
            PDFRenderer renderer = new PDFRenderer(doc);
            int pageCount = doc.getNumberOfPages();
            for (int i = 0; i < pageCount; i++) {
// 方式1,第二个参数是设置缩放比(即像素)
                BufferedImage image = renderer.renderImageWithDPI(i, 296/2);
// 方式2,第二个参数是设置缩放比(即像素)
//                BufferedImage image = renderer.renderImage(i, 5f); //第二个参数越大生成图片分辨率越高，转换时间也就越长
                imagePath = fileDirectory + "/" + i + ".jpg";
                ImageIO.write(image, "PNG", new File(imagePath));
                list.add(imagePath);
            }
            doc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static boolean isWindows() {
        String plantform = System.getProperty("os.name");
        if (plantform.toLowerCase().contains("windows")) {
            // 是windows 不走ocr 直接返回个结果数据
            return true;
        } else {
            // 不是windows 走ocr返回个结果数据
            return false;
        }
    }

    public static List<String> executeLinuxCmd(String cmd) {
        Runtime run = Runtime.getRuntime();
        try {
//            Process process = run.exec(cmd);
            Process process = run.exec(new String[]{"/bin/sh", "-c", cmd});
            InputStream in = process.getInputStream();
            BufferedReader bs = new BufferedReader(new InputStreamReader(in));
            List<String> list = new ArrayList<String>();
            String result = null;
            while ((result = bs.readLine()) != null) {
                list.add(result);
            }
            in.close();
            process.waitFor();
            process.destroy();
            return list;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String factory(String pngPath, String resultPath) {
        String commad = "tesseract " + pngPath + " " + resultPath + " -l chi_sim";
        List<String> result = executeLinuxCmd(commad);
        StringBuffer stringBuffer = new StringBuffer();
        /* 读取数据 */
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(resultPath + ".txt")),
                    "UTF-8"));
            String lineTxt = null;
            while ((lineTxt = br.readLine()) != null) {//数据以逗号分隔
                stringBuffer.append(lineTxt);

            }
            br.close();
        } catch (Exception e) {
            System.err.println("read errors :" + e);
        }
        return stringBuffer.toString();
    }


    /**
     * OCR 通过文件绝对地址和OCR库在Resources资源中的绝对地址识别出问题
     *
     * @param destPath 图片本地地址
     * @param tranPath OCR训练资源库地址
     * @return 返回OCR识别的文本
     */
    public String ocrReco(String destPath, String tranPath) {

        File imageFile = new File(destPath);
        ITesseract instance = new Tesseract();
        instance.setDatapath(tranPath);
        instance.setLanguage("chi_sim");
//        instance.setLanguage("eng");

        try {
            String result = instance.doOCR(imageFile).replace(" ", "");

            return result;
        } catch (TesseractException e) {
            return null;
        }
    }

    public static void deleteAllFilesOfDir(File path) {
        if (null != path) {
            if (!path.exists())
                return;
            if (path.isFile()) {
                boolean result = path.delete();
                int tryCount = 0;
                while (!result && tryCount++ < 10) {
                    System.gc(); // 回收资源
                    result = path.delete();
                }
            }
            File[] files = path.listFiles();
            if (null != files) {
                for (int i = 0; i < files.length; i++) {
                    deleteAllFilesOfDir(files[i]);
                }
            }
            path.delete();
        }
    }

    public static boolean deleteFile(String pathname) {
        boolean result = false;
        File file = new File(pathname);
        if (file.exists()) {
            file.delete();
            result = true;
            System.out.println("文件已经被成功删除");
        }
        return result;
    }

    public static String qrReco(String filePath) throws IOException, NotFoundException {
        String destPath = filePath;
        String[] component = destPath.split("\\.");
        int suffixIndex = component.length - 1;
        String extension = component[suffixIndex];

        String result = null;
        if (extension.toLowerCase().equals("pdf")) {
            List<String> paths = pdfToImagePath(filePath);
            for (String path : paths
            ) {
                if (result == null) {
                    String tmpResult = qrResut(path);
                    if (tmpResult != null) {
                        result = tmpResult;
                    }
                }
                deleteFile(path);
                
            }

        } else {
            result = qrResut(destPath);
        }


        return result;
    }

    private static String qrResut(String destPath) throws IOException, NotFoundException {
        BufferedImage image;
        image = ImageIO.read(new File(destPath));
        if (image == null) {
            return null;
        }
        BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        com.google.zxing.Result result;
        Hashtable<DecodeHintType, Object> hints = new Hashtable<DecodeHintType, Object>();
        hints.put(DecodeHintType.CHARACTER_SET, CHARSET);
        result = new MultiFormatReader().decode(bitmap, hints);

        String resultStr = result.getText();
        return resultStr;
    }

    public static boolean isPdf(String filePath) {
        String destPath = filePath;
        String[] component = destPath.split("\\.");
        int suffixIndex = component.length - 1;
        String extension = component[suffixIndex];

        return extension.toLowerCase().equals("pdf");
    }

}
