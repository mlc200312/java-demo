package com.minlc.demo.laboratory.itext;

import com.itextpdf.awt.geom.Rectangle2D;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.ImageRenderInfo;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.RenderListener;
import com.itextpdf.text.pdf.parser.TextRenderInfo;

import java.io.IOException;

public class Demo {
    /**
     * 定义关键字
     */
    private static String KEY_WORD = "us";
    /**
     * 定义返回值
     */
    private static float[] resu = null;
    /**
     * 定义返回页码
     */
    private static int i = 0;

    public static void main(String[] args) {
        float[] point = getKeyWords("/Users/liangchaomin/Mr.Min/Develop/code/min/java-demo/laboratory-demo/src/main/resources/借款合同-中文版V1.0.pdf");
        System.out.println(point);
    }

    /**
     * 返回关键字所在的坐标和页数 float[0] >> X float[1] >> Y float[2] >> page
     *
     * @param filePath
     * @return
     */
    private static float[] getKeyWords(String filePath) {
        try {
            PdfReader pdfReader = new PdfReader(filePath);
            int pageNum = pdfReader.getNumberOfPages();
            PdfReaderContentParser pdfReaderContentParser = new PdfReaderContentParser(pdfReader);

            // 下标从1开始  
            for (i = 1; i <= pageNum; i++) {
                pdfReaderContentParser.processContent(i, new RenderListener() {

                    @Override
                    public void renderText(TextRenderInfo textRenderInfo) {
                        String text = textRenderInfo.getText();
                        if (null != text && text.contains(KEY_WORD)) {
                            Rectangle2D.Float boundingRectange = textRenderInfo.getBaseline().getBoundingRectange();
                            resu = new float[3];
                            System.out.println("=======" + text);
                            System.out.println("h:" + boundingRectange.getHeight());
                            System.out.println("w:" + boundingRectange.width);
                            System.out.println("centerX:" + boundingRectange.getCenterX());
                            System.out.println("centerY:" + boundingRectange.getCenterY());
                            System.out.println("x:" + boundingRectange.getX());
                            System.out.println("y:" + boundingRectange.getY());
                            System.out.println("maxX:" + boundingRectange.getMaxX());
                            System.out.println("maxY:" + boundingRectange.getMaxY());
                            System.out.println("minX:" + boundingRectange.getMinX());
                            System.out.println("minY:" + boundingRectange.getMinY());
                            resu[0] = boundingRectange.x;
                            resu[1] = boundingRectange.y;
                            resu[2] = i;
                        }
                    }

                    @Override
                    public void renderImage(ImageRenderInfo arg0) {
                    }

                    @Override
                    public void endTextBlock() {
                    }

                    @Override
                    public void beginTextBlock() {
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resu;
    }

} 