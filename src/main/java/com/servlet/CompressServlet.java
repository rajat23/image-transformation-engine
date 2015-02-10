package com.servlet;

import com.helper.FileUrl;
import com.utility.ImageCompresser;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class CompressServlet extends HttpServlet {


    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException {

        String url = request.getParameter("url");
        float quality = Float.parseFloat(request.getParameter("quality"));
        BufferedImage image = null;
        ImageCompresser imageCompresser = new ImageCompresser();


        FileUrl fileUrl=new FileUrl();
        String extension=fileUrl.getFileExtension(url);
        try {
            image = ImageCompresser.getCompressImage(url,quality,extension);
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.setContentType("image/"+extension);
        ImageIO.write(image, extension, response.getOutputStream());
    }

    public void destroy() {
    }
}