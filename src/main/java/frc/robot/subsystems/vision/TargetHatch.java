/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems.vision;
import java.util.ArrayList;
import frc.robot.subsystems.vision.Cameras;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import io.pseudoresonance.pixy2api.Pixy2CCC.Block;

/**
 * Add your docs here.
 */
public class TargetHatch {
    private static ArrayList<Block> blocks = Cameras.getPixyCamera().getPixy().getCCC().getBlocks(); //Cameras.getPixyCamera().getPixy().getCCC().getBlocks();
    private static final int blockSignature = 2;
  
    public static void run(int count) {
      if (count > 0) {
        Block largestBlock = null;
        // Checks for Biggest Block that is of the Green Color Signature
        for (Block block : blocks) {
          if (block.getSignature() == blockSignature) {
            if (largestBlock == null) {
              largestBlock = block;
            } else {
              if (block.getWidth() > largestBlock.getWidth()) {
                largestBlock = block;
              }
            }
          }
        }
        int hatchX = largestBlock.getX();
        // Assuming camera is ccentered on robot and the display is 320 pixels wide this approxiamtes the angle to the hatch
        double yaw = ((hatchX - 157.5) * 0.1904761905);
  
        // Information about the Big Orange Block is sent to NetworkTables for
        // Shuffleboard
        SmartDashboard.putNumber("Hatch Angle", yaw);
        SmartDashboard.putNumber("Hatch X", largestBlock.getX());
        SmartDashboard.putNumber("Hatch Y", largestBlock.getY());
        SmartDashboard.putNumber("Hatch Box Width", largestBlock.getWidth());
        SmartDashboard.putNumber("Hatch Box Height", largestBlock.getHeight());
      }

    }  
}
