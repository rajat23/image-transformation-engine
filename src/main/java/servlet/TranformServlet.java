package servlet;


import effect.Effect;
import effect.EffectFactory;
import helper.ImageReader;
import helper.Response;
import helper.Rounder;
import mode.Mode;
import mode.ModeFactory;
import helper.RequestStructure;
import rotate.Angle;
import rotate.AngleFactory;
import utility.ImageCompresser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;


public class TranformServlet extends HttpServlet {


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
        RequestStructure requestStructure = new RequestStructure();
        requestStructure.setImage(image);
        Map<String, String[]> map = request.getParameterMap();
        requestStructure.setParameters(map);

        ModeFactory modeFactory = new ModeFactory();
        Mode mode = modeFactory.create(requestStructure.getMode());

        image = mode.getScaledImage(requestStructure);
        image = Rounder.makeRoundedCorner(image, requestStructure.getRadius());

        Effect effect=new EffectFactory().create(requestStructure.getEffect());
        image=effect.getEffect(image);

        Angle angle=new AngleFactory().create(requestStructure.getAngle());
        image=angle.rotateImage(image);

        ImageCompresser imageCompresser=new ImageCompresser();
        image=imageCompresser.getCompressImage(image,requestStructure.getQuality());

        Response servletResponse = new Response();
        servletResponse.setResponse(response, image, requestStructure.getFormat());
    }


}