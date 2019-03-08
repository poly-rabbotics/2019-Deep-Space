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


public class LiftRetractFront extends Command {
  Timer t = new Timer();
  public static double RETRACT_FRONT_DELAY = 3.0;
  public LiftRetractFront() {
    requires(Robot.liftSystem);
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }
  //private static final Timer t2 = new Timer();
  //private static final double stall2 = 2.0;

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    t.start();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.liftSystem.stopMotors();
    Robot.liftSystem.retractFrontSolenoids(); 
    //t2.delay(stall2); 
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return t.get()>=RETRACT_FRONT_DELAY;
    
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
