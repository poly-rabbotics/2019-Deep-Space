/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.commands.DriveCommand;

import static org.usfirst.frc.team4999.utils.Utils.map;

/**
 * Add your docs here.
 */
public class Drive extends Subsystem {
  private SpeedControllerGroup leftMotors = new SpeedControllerGroup(Robot.leftFront, Robot.leftBack);
  private SpeedControllerGroup rightMotors = new SpeedControllerGroup(Robot.rightFront, Robot.rightBack);

  private DifferentialDrive drive = new DifferentialDrive(leftMotors, rightMotors);

  public Drive() {
    super("Drive");
    addChild("Left Motors", leftMotors);
    addChild("Right Motors", rightMotors);
    drive.setDeadband(0);

  }

  /*
   * public void arcadeDrive(double moveRequest, double turnRequest, double
   * speedLimiter) { moveRequest = map(moveRequest, -1, 1, -speedLimiter,
   * speedLimiter); turnRequest = map(turnRequest, -1, 1, -speedLimiter,
   * speedLimiter); drive.arcadeDrive(moveRequest, turnRequest); }
   */
  public void arcadeDrive(double moveRequest, double turnRequest) {

    drive.arcadeDrive(moveRequest, turnRequest);
  }

  public void stop() {
    drive.stopMotor();
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new DriveCommand());
  }

}
