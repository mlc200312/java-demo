package com.minlc.demo.laboratory.itext;

import com.itextpdf.awt.geom.Rectangle2D;
import com.itextpdf.text.pdf.parser.ImageRenderInfo;
import com.itextpdf.text.pdf.parser.RenderListener;
import com.itextpdf.text.pdf.parser.TextRenderInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: pdf渲染监听, 当找到渲染的文本时，得到文本的坐标x,y,w,h
 * @Author: Mr.Min
 * @Date: 2020-08-13
 **/
public class PositionRenderListener implements RenderListener {

    private List<String> findText;
    /**
     * 出现无法取到值的情况，默认为12
     */
    private float defaultH;
    /**
     * 可能出现无法完全覆盖的情况，提供修正的参数，默认为2
     */
    private float fixHeight;

    public PositionRenderListener(List<String> findText, float defaultH, float fixHeight) {
        this.findText = findText;
        this.defaultH = defaultH;
        this.fixHeight = fixHeight;
    }

    public PositionRenderListener(List<String> findText) {
        this.findText = findText;
        this.defaultH = 12;
        this.fixHeight = 2;
    }

    @Override
    public void beginTextBlock() {
    }

    @Override
    public void endTextBlock() {
    }

    @Override
    public void renderImage(ImageRenderInfo imageInfo) {
    }

    private Map<String, ReplaceRegion> result = new HashMap<String, ReplaceRegion>();

    @Override
    public void renderText(TextRenderInfo textInfo) {
        String text = textInfo.getText();
        for (String keyWord : findText) {
            if (null != text && text.contains(keyWord)) {
                Rectangle2D.Float bound = textInfo.getBaseline().getBoundingRectange();
                ReplaceRegion region = new ReplaceRegion(keyWord);
                region.setH(bound.height == 0 ? defaultH : bound.height);
                region.setW(bound.width);
                region.setX(bound.x);
                region.setY(bound.y - this.fixHeight);
                result.put(keyWord, region);
            }
        }
    }

    public Map<String, ReplaceRegion> getResult() {
        ////补充没有找到的数据
        for (String key : findText) {
            if (this.result.get(key) == null) {
                this.result.put(key, null);
            }
        }
        return this.result;
    }
}  