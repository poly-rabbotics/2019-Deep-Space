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
  double upPressed = false;
  double downPressed = false;
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
    DriveController controller = Robot.controller;
   /*if(Robot.armAngle.getMoving()) {
    if(controller.getMoveArmsUp()||controller.getMoveArmsDown()) 
        Robot.armAngle.stopSpin();
    }
   if(controller.getMoveArmsUp()){
    Robot.armAngle.spinUpwards();
   }
   if(controller.getMoveArmsDown()){
     Robot.armAngle.spinDownwards();
   }*/
    if (controller.getMoveArmsUp() && !upPressed)  //Only do this the first time we detect the button is pressed
    {
      upPressed = true;
      Robot.armAngle.spinUpwards();
    }
    else if (controller.getMoveArmsDown() && !downPressed) //Only do this the first time we detect the button is pressed
    {
      downPressed = true;
      Robot.armAngle.spinDownwards();
    }
    if (!controller.getMoveArmsUp())  //acknowledge that the user let go of up button so it can be used again
    {
      upPressed = false;
    }
    if (!controller.getMoveArmsDown())  //acknowledge that the user let go of down button so it can be used again
    {
      downPressed = false;
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
