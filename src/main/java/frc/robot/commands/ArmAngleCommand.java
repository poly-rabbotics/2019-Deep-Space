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
<<<<<<< HEAD
    Robot.armAngle.setSpeed(Robot.controller.getArmsSpeed());
=======
    DriveController controller = Robot.controller;
   if(Robot.armAngle.getMoving()) {
    if(controller.getMoveArmsUp()||controller.getMoveArmsDown()) 
        Robot.armAngle.stopSpin();
    }
   if(controller.getMoveArmsUp()){
    Robot.armAngle.spinUpwards();
   }
   if(controller.getMoveArmsDown()){
     Robot.armAngle.spinDownwards();
   }
    

>>>>>>> parent of e30b30a... "Fixed" Delays for Lift System
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
