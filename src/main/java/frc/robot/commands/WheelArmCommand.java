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

public class WheelArmCommand extends Command {
  public WheelArmCommand() {
    requires(Robot.wheelArm);
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
    /*if(Robot.wheelArm.isInwards()||Robot.wheelArm.isOutwards()){
      if(controller1.getToggleInwards()||controller1.getToggleOutwards()||controller1.getToggleInwards()||controller1.getToggleOutwards()){
        Robot.wheelArm.stopArms();
      }
    
    }
    */
    if(Robot.wheelArm.isInwards()){
      if(controller1.getToggleInwards()||controller2.getToggleInwards()){
        Robot.wheelArm.stopArms();
      }
    }
    if(Robot.wheelArm.isOutwards()){
      if(controller1.getToggleOutwards()||controller2.getToggleOutwards()){
        Robot.wheelArm.stopArms();
      }
    }

    if(controller1.getToggleInwards()||controller2.getToggleInwards()){
      Robot.wheelArm.spinInwards();
    }
    if(controller1.getToggleOutwards()||controller2.getToggleOutwards()){
      Robot.wheelArm.spinOutwards();
    }
    if(Robot.wheelArm.isInwards()==false&&Robot.wheelArm.isOutwards()==false){
      Robot.wheelArm.neutral();
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
