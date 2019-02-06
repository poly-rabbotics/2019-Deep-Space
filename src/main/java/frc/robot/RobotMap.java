/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;
import org.usfirst.frc.team4999.controllers.LogitechF310;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.VictorSP;
/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
  // For example to map the left and right motors, you could define the
  // following variables to use with your drivetrain subsystem.
  // public static int leftMotor = 1;
  // public static int rightMotor = 2;

  // If you are using multiple modules, make sure to define both the port
  // number and the module. For example you with a rangefinder:
  // public static int rangefinderPort = 1;
  // public static int rangefinderModule = 1;
  public static final Spark leftFront = new Spark(0);// TODO: Set to actual port numbers
  public static final Spark rightFront = new Spark(1);
  public static final Spark leftBack = new Spark(2);
  public static final Spark rightBack = new Spark(3);

  public static final VictorSP wheelArmLeft = new VictorSP(4);;// TODO: Set to actual port numbers
  public static final VictorSP wheelArmRight = new VictorSP(5);
  public static final VictorSP wheelArmAngle = new VictorSP(6);

  public static final DoubleSolenoid hatchSolenoidTop = new DoubleSolenoid(0,1); // TODO: set to actual solenoid values
  public static final DoubleSolenoid hatchSolenoidLeft = new DoubleSolenoid(2,3);
  public static final DoubleSolenoid hatchSolenoidRight = new DoubleSolenoid(4,5); //placeholder values

  public static final LogitechF310 controller1 = new LogitechF310(0);// TODO: PORT NUMBERS
}
