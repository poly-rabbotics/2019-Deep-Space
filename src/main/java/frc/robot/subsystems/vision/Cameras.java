/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems.vision;


import io.pseudoresonance.pixy2api.links.SPILink;
/**
 * Add your docs here.
 */
public class Cameras {

   private static PixyCamera pixy = null;

  public static void setup() {
      //pixy = new PixyCamera(new SPILink());
      pixy = new PixyCamera(new SPILink(),0);

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