package com.twins.lee.controller;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import org.apache.pdfbox.io.RandomAccessBuffer;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;


/**
 * @Authoe hanyun
 * @Email 1355081829@qq.com
 * @Date 2019/12/19
 **/
class PdfControllerTest {

    @Test
    void index() throws IOException {
        File pdfFile = new File("F:\\java\\leDan\\src\\test\\java\\com\\twins\\lee\\controller\\测试.pdf");
        PDDocument document = null;

        try {
            // 方式一：
            /**
             InputStream input = null;
             input = new FileInputStream( pdfFile );
             //加载 pdf 文档
             PDFParser parser = new PDFParser(new RandomAccessBuffer(input));
             parser.parse();
             document = parser.getPDDocument();
             **/

            // 方式二：
            document = PDDocument.load(pdfFile);

            // 获取页码
            int pages = document.getNumberOfPages();
            System.out.println(pages);

            // 读文本内容
            PDFTextStripper stripper = new PDFTextStripper();
            // 设置按顺序输出
            stripper.setSortByPosition(true);
            stripper.setStartPage(1);
            stripper.setEndPage(1);
            String content = stripper.getText(document);
            //System.out.println(content);

            BufferedReader bre = null;
            String str = "";
            int state = 1;
            bre = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8));
            System.out.println(bre);

            while ((str = bre.readLine()) != null) {
                System.out.println(str);
//                if (str.contains("细胞")) {
//                    state = 1;
//                    continue;
//                }
                if (state == 1) {
                    String[] array = str.split(" ");

                    List list = new ArrayList();
                    for (int i = 0; i < array.length; i++) {
                        list.add(array[i]);
                    }
                    for (int i = 0; i < list.size(); i++) {
                        String one = (String) list.get(i);

                        if (one.equals("--")) {
                            String one_b = (String) list.get(i - 1);
                            String one_a = (String) list.get(i + 1);

                            String new_one = one_b + one + one_a;
                            list.set(i, new_one);
                            list.remove(i - 1);
                            list.remove(i);

                            System.out.println(list.toString());
                        }
                    }
                    System.out.println(str + ",size-" + array.length);
                }

            }
        } catch (Exception e) {
            System.out.println(e);
        }
        document.close();
    }
}