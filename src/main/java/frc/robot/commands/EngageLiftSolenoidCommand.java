/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.controls.DriveController;

public class EngageLiftSolenoidCommand extends Command {
  Timer t = new Timer();
  private static final double SOLENOID_DELAY = 4.0;
  
	public EngageLiftSolenoidCommand() {
    requires(Robot.liftSystem);
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
    DriveController controller = Robot.controller;
    if(controller.getStartLift()){
      Robot.liftSystem.engageSolenoids();
      t.start();
    
    }
    if(controller.getStopLift()){
      Robot.liftSystem.retractAllSolenoids();
      t.start();
    }

    
    
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return t.get()>=SOLENOID_DELAY;
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
