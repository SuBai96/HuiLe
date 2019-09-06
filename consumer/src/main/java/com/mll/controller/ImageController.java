package com.mll.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

@Controller
public class ImageController {
    @RequestMapping("/Image")
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BufferedImage bfi=new BufferedImage(80, 25, BufferedImage.TYPE_INT_RGB);
        Graphics graphics=bfi.getGraphics();
        graphics.fillRect(0, 0, 80, 25);
        char[] ch="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
        Random random=new Random();
        int index;
        StringBuffer str=new StringBuffer();
        for (int i = 0; i < 4; i++) {
            index=random.nextInt(ch.length);
            graphics.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
            Font font=new Font("宋体", 30, 20);
            graphics.setFont(font);
            graphics.drawString(ch[index]+"", (i*20)+2, 23);
            str.append(ch[index]);
        }
        int area=(int)(0.02*80*25);
        for (int i = 0; i < area; i++) {
            int x=(int) (Math.random()*80);
            int y=(int) (Math.random()*25);
            bfi.setRGB(	x, y, (int)(Math.random()*255));
        }
        for (int i = 0; i < 6; i++) {
            int xstart=(int)(Math.random()*80);
            int ystart=(int) (Math.random()*25);
            int xend=(int)(Math.random()*80);
            int yend=(int)(Math.random()*25);
            graphics.setColor(interLine(1,255));
            graphics.drawLine(xstart, ystart, xend, yend);
        }
        HttpSession session=request.getSession();
        session.setAttribute("verificationCode", str.toString());
        ImageIO.write(bfi, "JPG", response.getOutputStream());
        System.out.println(str.toString());
    }
    private static Color interLine(int low,int high) {
        if(low>255) {
            low=255;
        }
        if(high>255) {
            high=255;
        }
        if(low<0) {
            low=0;
        }
        if(high<0) {
            high=0;
        }
        int interval=high-low;
        int r=low+(int)(Math.random()*interval);
        int g=low+(int)(Math.random()*interval);
        int b=low+(int)(Math.random()*interval);
        return new Color(r, g, b);
    }
}
