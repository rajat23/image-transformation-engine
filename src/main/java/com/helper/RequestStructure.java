package com.helper;


import com.helper.MyColor;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.image.BufferedImage;
import java.rmi.MarshalledObject;
import java.util.Enumeration;
import java.util.Map;

public class RequestStructure {
    private BufferedImage image;
    private int height;
    private int width;
    private Color color;
    private String mode;
    private String orientation;
    private Coordinates coordinates;
    private boolean isCoordinatesSet;
    private int radius;
    private String effect;
    private String angle;
    private String format;
    private float quality;

    public RequestStructure() {
        height = 0;
        width = 0;
        color = Color.WHITE;
        mode = "pad";
        orientation = "center";
        coordinates=new Coordinates(0,0);
        isCoordinatesSet=false;
        radius=0;
        effect="NoEffect";
        angle="0";
        format="";
        quality=-1.0f;
    }

    public BufferedImage getImage() {
        return image;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Color getColor() {
        return color;
    }

    public String getMode() {
        return mode;
    }

    public String getOrientation() {
        return orientation;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public float getAspectRatio() {
        return (float) image.getWidth() / (float) image.getHeight();
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public boolean isCoordinatesSet() {
        return isCoordinatesSet;
    }
    public String getEffect() { return effect;}


    public String getAngle() {
        return angle;
    }

    public String getFormat() {
        return format;
    }

    public void setParameters(Map map) {

        if (map.containsKey("width"))
            width = getWidth(((String[]) map.get("width"))[0],image.getWidth());
        if (map.containsKey("height"))
            height = getHeight(((String[]) map.get("height"))[0],image.getHeight());

        if (height == 0)
            height = getProportionalHeight(width, getAspectRatio());
        if (width == 0)
            width = getProportionalWidth(height, getAspectRatio());
        if (map.containsKey("color"))
            color = MyColor.getColor(((String[]) map.get("color"))[0]);
        if (map.containsKey("mode"))
            mode = ((String[]) map.get("mode"))[0];
        if (map.containsKey("orientation"))
            orientation = ((String[]) map.get("orientation"))[0];
        int x=0,y=0;
        if (map.containsKey("x")) {
            x = Integer.parseInt(((String[]) map.get("x"))[0]);
            isCoordinatesSet=true;
        }
        if (map.containsKey("y")) {
            y = Integer.parseInt(((String[]) map.get("y"))[0]);
            isCoordinatesSet=true;

        }
        if(map.containsKey("effect")) {
            effect=((String[]) map.get("effect"))[0];
        }

        coordinates=new Coordinates(x,y);

        if(map.containsKey("radius"))
            radius=Integer.parseInt(((String[]) map.get("radius"))[0]);

        if(map.containsKey("angle"))
            angle=((String[]) map.get("angle"))[0];
        if(map.containsKey("format"))
            format=((String[]) map.get("format"))[0];
        else
        {
            format=new FileUrl().getFileExtension(((String[]) map.get("path"))[0]);
        }
        if(map.containsKey("quality"))
            quality= Float.parseFloat(((String[]) map.get("quality"))[0]);


    }

    public int getHeight(String height,int imageHeight) {
        if(height.contains(".")) {
            Double percentage = Double.parseDouble(height);
            return (int) (percentage*(double)imageHeight);
        }
        return Integer.parseInt(height);
    }

    public float getQuality() {
        return quality;
    }

    public int getWidth(String width,int imageWidth) {
        if(width.contains(".")) {
            Double percentage = Double.parseDouble(width);
            return (int) (percentage*(double)imageWidth);
        }
        return Integer.parseInt(width);
    }


    public int getProportionalHeight(int width, float aspectRatio) {
        return (int) (width / aspectRatio);

    }

    public int getProportionalWidth(int height, float aspectRatio) {
        return (int) (height * aspectRatio);
    }

    public int getRadius() {
        return radius;
    }

    public Dimension getDimension() {
        return new Dimension(width, height);
    }


}
