package com.jmu.bibasedmanage.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 验证码
 */
@Controller
@RequestMapping("/code")
public class CodeController {

    private int width = 90;//定义图片的width
    private int height = 30;//定义图片的height
    private int codeCount = 4;//定义图片上显示验证码的个数
    private int xx = 15;
    private int fontHeight = 18;
    private int codeY = 16;
    char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
            'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

    @RequestMapping("/get-code")
    protected void doGetCode(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        //设置页面不缓存
        httpServletResponse.setHeader("Pragma", "No-cache");
        httpServletResponse.setHeader("Cache-Control", "no-cache");
        httpServletResponse.setDateHeader("Expires", 0);
        httpServletResponse.setContentType("image/jpeg");
        // 在内存中创建图象
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        // 获取图形上下文
        Graphics g = image.getGraphics();
        // 创建一个随机数生成器类
        Random random = new Random();
        // 将图像填充为白色
        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, width, height);
        //设定字体
        g.setFont(new Font("Times New Roman", Font.PLAIN, fontHeight));
        //画边框
        g.setColor(new Color(20,20,20));
        g.drawRect(0, 0, width - 1, height - 1);
        // 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
        g.setColor(getRandColor(160, 200));
        for (int i = 0; i < 155; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x,y,x+xl,y+yl);
        }
        // randomCode用于保存随机产生的验证码，以便用户登录后进行验证。
        StringBuffer randomCode = new StringBuffer();
        int red = 0, green = 0, blue = 0;
        // 随机产生codeCount数字的验证码。
        for (int i = 0; i < codeCount; i++) {
            // 得到随机产生的验证码数字。
            String code = String.valueOf(codeSequence[random.nextInt(36)]);

            // 产生随机的颜色分量来构造颜色值，这样输出的每位数字的颜色值都将不同。
            red = 20 + random.nextInt(110);
            green = 20 + random.nextInt(110);
            blue = 20 + random.nextInt(110);


            // 用随机产生的颜色将验证码绘制到图像中。
            g.setColor(new Color(red, green, blue));
            g.drawString(code, (i + 1) * xx, codeY);


            // 将产生的四个随机数组合在一起。
            randomCode.append(code);
        }
        // 图象生效
        g.dispose();
        HttpSession session = httpServletRequest.getSession();
        session.setAttribute("code", randomCode.toString());
        // 将图像输出到Servlet输出流中。
        ServletOutputStream sos = httpServletResponse.getOutputStream();
        ImageIO.write(image, "jpeg", sos);
        sos.close();
    }




    /**
     * 获取随机颜色
     * @param fc
     * @param bc
     * @return
     */
    private Color getRandColor(int fc,int bc) {
        Random random = new Random();
        if(fc > 255) {
            fc = 255;
        }
        if(bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }




    public int getWidth() {
        return width;
    }


    public void setWidth(int width) {
        this.width = width;
    }


    public int getHeight() {
        return height;
    }


    public void setHeight(int height) {
        this.height = height;
    }


    public int getCodeCount() {
        return codeCount;
    }


    public void setCodeCount(int codeCount) {
        this.codeCount = codeCount;
    }


    public int getXx() {
        return xx;
    }


    public void setXx(int xx) {
        this.xx = xx;
    }


    public int getFontHeight() {
        return fontHeight;
    }


    public void setFontHeight(int fontHeight) {
        this.fontHeight = fontHeight;
    }


    public int getCodeY() {
        return codeY;
    }


    public void setCodeY(int codeY) {
        this.codeY = codeY;
    }

}
