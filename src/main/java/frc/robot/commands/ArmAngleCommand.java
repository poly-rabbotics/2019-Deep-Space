/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.controls.DriveController;

public class ArmAngleCommand extends Command {
  public ArmAngleCommand() {
    requires(Robot.armAngle);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    DriveController controller1 = Robot.controller1;
    DriveController controller2 = Robot.controller2;
   if(Robot.armAngle.getMoving()) {
    if(controller1.getMoveArmsUp()||controller1.getMoveArmsDown()||controller2.getMoveArmsUp()||controller2.getMoveArmsDown()) 
        Robot.armAngle.stopSpin();
    }
   if(controller1.getMoveArmsUp()||controller2.getMoveArmsUp()){
    Robot.armAngle.spinUpwards();
   }
   if(controller1.getMoveArmsDown()||controller2.getMoveArmsDown()){
     Robot.armAngle.spinDownwards();
   }
    

  }

  // Make this return true when this Command no longer needs to run execute()
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
