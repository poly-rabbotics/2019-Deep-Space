/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems.vision ;
import java.util.ArrayList;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.vision.Cameras;
import io.pseudoresonance.pixy2api.Pixy2CCC.Block;


public class TargetBall {
    private static ArrayList<Block> blocks = Cameras.getPixyCamera().getPixy().getCCC().getBlocks();
    private static final int blockSignature = 1;
  
    public static void run(int count) {
      if (count > 0) {
        Block largestBlock1 = null;
        Block largestBlock2 = null;
        Block oldBlock1 = null;
        boolean newBlock1 = false;
        // Checks for Biggest Block that is of the Orange Color Signature
        for (Block block : blocks) {
            if (block.getSignature() == blockSignature) {
                if (largestBlock1 == null) {
                    largestBlock1 = block;
                } else {
                    if (block.getWidth() > largestBlock1.getWidth()) {
                        oldBlock1 = largestBlock1;
                        largestBlock1 = block;
                        newBlock1 = true;
                    }
                }
                if(newBlock1){
                    largestBlock2 = oldBlock1;
                }
                // if(largestBlock2 == null) {
                //     largestBlock2 = block;
                // } else {
                //     if(block.getWidth() < largestBlock1.getWidth() && block.getWidth() > largestBlock2.getWidth()){
                //         largestBlock2 = block;
                //     }
                // }
        }
        }
        
        int target1X = largestBlock1.getX();
        int target2X = largestBlock2.getX();
        // Assuming camera is cce ntered on robot and the display is 320 pixels wide this approxiamtes the angle to the ball
        double yaw1 = ((target1X - 157.5) * 0.1904761905);
        double yaw2 = ((target2X - 157.5) * 0.1904761905);
        double yawAverage = (yaw1 + yaw2) / 2.0;
  
        // Information about the Big Orange Block is sent to NetworkTables for
        // Shuffleboard
        SmartDashboard.putNumber("Target 1 Angle", yaw1);
        SmartDashboard.putNumber("Target 2 Angle", yaw2);
        SmartDashboard.putNumber("Target 1 X", largestBlock1.getX());
        SmartDashboard.putNumber("Target 2 X", largestBlock2.getX());
        SmartDashboard.putNumber("Target 1 Y", largestBlock1.getY());
        SmartDashboard.putNumber("Target 2 Y", largestBlock2.getY());
        SmartDashboard.putNumber("Target 1 Box Width", largestBlock1.getWidth());
        SmartDashboard.putNumber("Target 2 Box Width", largestBlock2.getWidth());
        SmartDashboard.putNumber("Target 1 Box Height", largestBlock1.getHeight());
        SmartDashboard.putNumber("Target 2 Box Height", largestBlock2.getHeight());
        SmartDashboard.putNumber("Average Angle", yawAverage);

    }  
    }
}
