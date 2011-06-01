package vision;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;

public class ComponentLocation {

    public static void setLocationCenter(float x, float y, Component c) {
        Dimension comp = c.getSize();
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int xLocation = (int) (screen.width * x - comp.width / 2);
        int yLocation = (int) (screen.height * y - comp.height / 2);
        c.setLocation(xLocation, yLocation);
    }

    public static void setLocationTopLeftCorner(float x, float y, Component c) {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int xLocation = (int) (screen.width * x);
        int yLocation = (int) (screen.height * y);
        c.setLocation(xLocation, yLocation);
    }
}
