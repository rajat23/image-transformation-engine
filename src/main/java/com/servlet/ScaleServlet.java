package com.servlet;



import com.helper.ImageReader;
import com.helper.Response;
import com.mode.Mode;
import com.mode.ModeFactory;
import com.utility.ImageScaler;
import com.utility.RequestStructure;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;


public class ScaleServlet extends HttpServlet {


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String path = request.getParameter("path");
        ImageReader imageReader = new ImageReader();
        BufferedImage image;
        try {
            image = imageReader.readImage(path);
        } catch (IOException ioException) {
            response.setContentType("text/html");
            PrintWriter printWriter = response.getWriter();
            printWriter.write("<html><body>" + ioException.getStackTrace() + "</body><html>");
            return;
        }
        RequestStructure requestStructure=new RequestStructure(image);
        requestStructure.setParameters(request);
        ModeFactory modeFactory=new ModeFactory();
        Mode mode=modeFactory.create(requestStructure.getMode());
        image=mode.getScaledImage(requestStructure);
        Response servletResponse = new Response();
        servletResponse.setResponse(response, image, path);
    }



}
