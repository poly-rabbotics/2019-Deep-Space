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
        Block largestBlock = null;
        // Checks for Biggest Block that is of the Orange Color Signature
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
        
        int ballX = largestBlock.getX();
        // Assuming camera is cce ntered on robot and the display is 320 pixels wide this approxiamtes the angle to the ball
        double yaw = ((ballX - 157.5) * 0.1904761905);
  
        // Information about the Big Orange Block is sent to NetworkTables for
        // Shuffleboard
        SmartDashboard.putNumber("Ball Angle", yaw);
        SmartDashboard.putNumber("Ball X", largestBlock.getX());
        SmartDashboard.putNumber("Ball Y", largestBlock.getY());
        SmartDashboard.putNumber("Ball Box Width", largestBlock.getWidth());
        SmartDashboard.putNumber("Ball Box Height", largestBlock.getHeight());

    }  
    }
}
