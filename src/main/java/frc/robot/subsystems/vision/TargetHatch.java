package frc.robot.subsystems.vision;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.vision.Cameras;
import io.github.pseudoresonance.pixy2api.Pixy2CCC.Block;

public class TargetHatch {

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
          } else if (block.getWidth() > largestBlock.getWidth()) {
            largestBlock = block;
          }
        }
      }

      int ballX = largestBlock.getX();
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
