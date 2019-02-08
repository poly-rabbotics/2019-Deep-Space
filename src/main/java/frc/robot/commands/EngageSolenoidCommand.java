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

public class EngageSolenoidCommand extends Command {
  public EngageSolenoidCommand() {
    requires(Robot.liftSystem);
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }
private static final Timer t = new Timer();
private static final double stall = 3.0;
  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    /*if(getStartLift){
      Robot.liftSystem.engageSolenoids();
      t.delay(stall);
    }
    */
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

    
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
