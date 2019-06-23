/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

/**
   * ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
   * Structured the same way as the other commands, but this one does more different things.
   * ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.controls.DriveController;

public class ManualLiftCommand extends Command {
  public ManualLiftCommand() {
    requires(Robot.liftSystem);
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  /**
   * ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
   * Lift up the front if told to do so, lift up the back if told to do so, lower the 
   * front if told to do so, lower the back if told to do so, drive forward if told to do so,
   * stop driving forward if told to do so. That's it.
   * ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
*/
  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    DriveController controller2 = Robot.controller2;

    if(controller2.getEngageFrontSolenoid()){
      Robot.liftSystem.engageFrontSolenoids();
    }

    if(controller2.getEngageBackSolenoid()){
      Robot.liftSystem.engageBackSolenoids();
    }

    if(controller2.getRetractFrontSolenoid()){
      Robot.liftSystem.retractFrontSolenoids();
    }

    if(controller2.getRetractBackSolenoid()){
      Robot.liftSystem.retractBackSolenoids();
    }
    
    if(controller2.getDriveLiftForward()){
      Robot.liftSystem.driveForward();
    }
   else{
      Robot.liftSystem.stopMotors();
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
