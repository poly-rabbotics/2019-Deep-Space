package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.controls.DriveController;

public class LatchCommand extends Command {

  private boolean isUp;
  public LatchCommand() {
    isUp = true;            //everything with this subsystem is programmed based on the assumption that the starting position is locked defensive
    requires (Robot.cargoMechanismLatch);
  }
  
  @Override
  protected void initialize() {
  }
  
  @Override
  protected void execute() {
    DriveController controller1 = Robot.controller1;
    DriveController controller2 = Robot.controller2;
    if(controller1.getMoveLatchUp()||controller2.getMoveLatchUp()) { 
      isUp = true;
    }
    if(controller1.getMoveArmsDown()||controller2.getMoveArmsDown()) {
      isUp = false;
    }
    if (isUp) {
      Robot.cargoMechanismLatch.spinUpwards();
    }
    else {
      Robot.cargoMechanismLatch.spinDownwards();
    }
  }
  
  @Override 
  protected boolean isFinished() {
    return false;
  }
  
    // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
