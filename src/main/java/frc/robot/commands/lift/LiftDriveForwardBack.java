/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.lift;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.Timer;

public class LiftDriveForwardBack extends Command {
  Timer t = new Timer();
  private static final double DRIVE_DELAY = 3.0;

  public LiftDriveForwardBack() {
    super();
    requires(Robot.liftSystem);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    t.start();
    t.reset();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.liftSystem.driveForward();
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    // return t.hasPeriodPassed(DRIVE_DELAY);
    return false;
  }

  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
