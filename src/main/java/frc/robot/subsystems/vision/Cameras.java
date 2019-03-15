/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems.vision;

import io.pseudoresonance.pixy2api.links.SPILink;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.commands.CameraSwitchView;
import frc.robot.controls.DriveController;
/**
 * Add your docs here.
 */
public class Cameras {

   private static PixyCamera pixy = null;
   public static UsbCamera frontCam;
   public static UsbCamera backCam;
   public static UsbCamera driveCam;
 
  public static void setup() {
    //pixy = new PixyCamera(new SPILink());
    pixy = new PixyCamera(new SPILink(),0);
    // // frontCam = CameraServer.getInstance().startAutomaticCapture("Front Camera",0);
    // // frontCam.setResolution(320, 240);
    // // backCam = CameraServer.getInstance().startAutomaticCapture("Back Camera",1);
    // // backCam.setResolution(320, 240);
    //driveCam starts out looking at front
    driveCam = CameraServer.getInstance().startAutomaticCapture("Drive Camera",(Robot.lookForward?0:1));
    driveCam.setResolution(320, 240);
  }

  public static void light(boolean state) {
    pixy.light(state);
  }

  public static void run() {
    pixy.run();
  }

  public static void switchView(){
    Robot.lookForward = !Robot.lookForward;
    driveCam = CameraServer.getInstance().startAutomaticCapture("Drive Camera",(Robot.lookForward?0:1)); 
    driveCam.setResolution(320, 240);
  }

  public static PixyCamera getPixyCamera() {
      return pixy;
  }


}