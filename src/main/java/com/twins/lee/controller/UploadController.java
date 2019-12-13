package com.twins.lee.controller;

import com.twins.lee.common.CompanyTool;
import com.twins.lee.entity.Resource;
import com.twins.lee.mapper.ResourceMapper;
import com.twins.lee.response.Response;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


//@SessionAttributes(value={"CURR_USER"},types={User.class})
@Controller
@RequestMapping("/file")
public class UploadController {
    @Autowired
    ResourceMapper resourceMapper;

    @Value("${twins.uploadFolder}")
    private String docLocation;


    @RequestMapping("/upload")
    @ResponseBody
    public Map upload(@RequestParam("file") MultipartFile file, HttpSession session,
                      @RequestParam(value = "needOCR", required = false, defaultValue = "false") boolean needOcr,
                      @RequestParam(value = "type", required = false, defaultValue = "0") int type) {

        String plantform = System.getProperty("os.name");
        if (plantform.toLowerCase().contains("windows")) {
            // 是windows 不走ocr 直接返回个结果数据
        } else {
            // 不是windows 走ocr返回个结果数据

        }


        String sessionId = session.getId();

        // 获取文件名
        String fileName = file.getOriginalFilename();

        // 获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));


        try {

            // 文件上传路径
            String filePath = null;
            filePath = fileDestPath();
            if (docLocation != null) {
                filePath = docLocation;
            }
            //用户目录
            String userDir = UUID.randomUUID().toString() + "/" + sessionId;

            // 解决中文问题，liunx下中文路径，图片显示问题
            fileName = UUID.randomUUID() + suffixName;
            String fileUrl = "/" + userDir + "/" + fileName;
            filePath = filePath + fileUrl;
            File dest = new File(filePath);

            // 检测是否存在目录
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }

            file.transferTo(dest);

            Resource resource = new Resource();
            Map result = null;
            Map extract = null;
            if (needOcr && type > 0) {
                extract = new HashMap();
                String ocrResult = ocrReco(fileUrl);
                resource.setOcr(ocrResult);
                extract.put("type", type);
                extract.put("ocrResult", regexOcrResult(type, ocrResult));

            }
            Map value = new HashMap();
            resource.setUrl(fileUrl);

            resourceMapper.insert(resource);

            value.put("value", resource.getResourceUri());

            if (extract != null) {
                value.put("extract", extract);
            }
            return result = Response.success(value);
        } catch (Exception e) {
            return Response.error("系统错误:" + e.getLocalizedMessage());
        }
    }

    //    protected String qrReco(String imgPath) throws IOException, NotFoundException {
//        String destPath = docLocation + imgPath;
//
//        BufferedImage image;
//        image = ImageIO.read(new File(destPath));
//        if (image == null) {
//            return null;
//        }
//        BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(image);
//        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
//        com.google.zxing.Result result;
//        Hashtable<DecodeHintType, Object> hints = new Hashtable<DecodeHintType, Object>();
//        hints.put(DecodeHintType.CHARACTER_SET, CHARSET);
//        result = new MultiFormatReader().decode(bitmap, hints);
//
//        String resultStr = result.getText();
//
//        return resultStr;
//    }
//
    private Object regexOcrResult(int type, String ocrSource) {
        Map result = new HashMap();
        result.put("ocrValue", ocrSource);

        switch (type) {
            case CompanyTool.OcrTypeOfCardA: {
                String nameValue = null;
                String idValue = null;
                Pattern namePattern = Pattern.compile("名[^\\\\x00-\\\\xff]{2,3}");
                Matcher nameMatcher = namePattern.matcher(ocrSource);
                if (nameMatcher.find()) {
                    nameValue = nameMatcher.group();
                    nameValue = nameValue.replace("名", "");
                }
                Pattern idPattern = Pattern.compile("\\d{17}[\\d|x]|\\d{15}");
                Matcher idMatcher = idPattern.matcher(ocrSource);
                if (idMatcher.find()) {
                    idValue = idMatcher.group();
                }

                result.put("name", nameValue);
                result.put("id", idValue);
                return result;
            }
            case CompanyTool.OcrTypeOfLicense: {
                //社会统一信用代码
                String license = null;
                //法人代表
                String representativeName = null;
                Pattern licensePattern = Pattern.compile("统一社会信用代码+[0-9a-zA-Z]+");
                Matcher licenseMatcher = licensePattern.matcher(ocrSource);

                Pattern representativePattern = Pattern.compile("法定代表人[^\\x00-\\xff]{2,4}");
                Matcher representativeMatcher = representativePattern.matcher(ocrSource);

                if (licenseMatcher.find()) {
                    license = licenseMatcher.group();
                    license = license.replace("统一社会信用代码", "");
                }
                if (representativeMatcher.find()) {
                    representativeName = representativeMatcher.group();
                    representativeName = representativeName.replace("法定代表人", "");
                }
                result.put("license", license);
                result.put("representativeName", representativeName);

                return result;
            }
        }
        return null;
    }

    private String ocrReco(String imgPath) {
        String sysPath = fileDestPath();
        String tranPath = sysPath;
        String destPath = sysPath + "/static" + imgPath;
        if (docLocation != null) {
            destPath = docLocation + imgPath;
        }
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

    private String fileDestPath() {
        String filePath = null;
        try {
            filePath = ResourceUtils.getFile("classpath:").getAbsolutePath();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        return filePath;
    }


}
