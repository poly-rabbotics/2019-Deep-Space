package frc.robot.subsystems.vision;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import io.github.pseudoresonance.pixy2api.links.SPILink;

public class Cameras {

  private static PixyCamera pixy = null;

  public static void setup() {
    pixy = new PixyCamera(new SPILink(), 0);

  }

  public static void light(boolean state) {
    pixy.light(state);
  }

  public static void run() {
    pixy.run();
  }

  public static PixyCamera getPixyCamera() {
    return pixy;
  }

}