package com.example.demo.lab.itext;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @Description: 功能描述
 * @Author: Mr.Min
 * @Date: 2020-08-13
 **/
public class ITextDemo {

    @SuppressWarnings("deprecation")
    public static final String SRC = "/Users/liangchaomin/Mr.Min/Develop/code/min/java-demo/laboratory-demo/src/main/java/com/minlc/demo/laboratory/itext/test.pdf";
    public static final String DEST = "/Users/liangchaomin/Downloads/test.pdf";

    public static void main(String[] args) throws IOException, DocumentException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new ITextDemo().manipulatePdf(SRC, DEST);
    }

    public void manipulatePdf(String src, String dest) throws IOException, DocumentException {
        PdfReader reader = new PdfReader(src);
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
        PdfContentByte canvas = stamper.getOverContent(1);
        float height = 595;
        System.out.println(canvas.getHorizontalScaling());
        float x, y;
        x = 216;
        y = height - 49.09F;
        canvas.saveState();
        canvas.setColorFill(BaseColor.WHITE);
        canvas.rectangle(x, y - 5, 43, 15);

        canvas.fill();
        canvas.restoreState();
        //开始写入文本
        canvas.beginText();
        //BaseFont bf = BaseFont.createFont(URLDecoder.decode(CutAndPaste.class.getResource("/AdobeSongStd-Light.otf").getFile()), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        BaseFont bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.EMBEDDED);
        Font font = new Font(bf, 10, Font.BOLD);
        //设置字体和大小
        canvas.setFontAndSize(font.getBaseFont(), 10);
        //设置字体的输出位置
        canvas.setTextMatrix(x, y);
        //要输出的text
        canvas.showText("多退少补");

        //设置字体的输出位置
        canvas.setFontAndSize(font.getBaseFont(), 20);
        canvas.setTextMatrix(x, y - 90);
        //要输出的text
        canvas.showText("多退少补");

        canvas.endText();
        stamper.close();
        reader.close();
        System.out.println("complete");
    }
}
