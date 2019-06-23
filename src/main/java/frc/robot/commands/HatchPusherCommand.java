/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

/**
   * ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
   * Same as the other commands. Step 1: Every 20 milliseconds (or however frequently 
   * execute is called), find out what the drive controller is telling the hatch mechanism 
   * to do. Then, tell the hatch mechanism to do that thing.
   * ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.controls.DriveController;

public class HatchPusherCommand extends Command {
  public HatchPusherCommand() {
    requires(Robot.hatchPusher);
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
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
    if(controller1.getToggleHatchPusher()||controller2.getToggleHatchPusher()){ //accepts input from both controllers, even though we just use one. This is probably bad programming.
        Robot.hatchPusher.extend(); //extend if being told to extend, retract otherwise.
      }
      else{
        Robot.hatchPusher.retract();
      }
    }



  

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false; //This command is never finished.
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
