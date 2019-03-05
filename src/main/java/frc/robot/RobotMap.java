/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import org.usfirst.frc.team4999.controllers.LogitechF310;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.Encoder;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {

  public static final PWMVictorSPX wheelArmLeft = new PWMVictorSPX(4);// TODO: Set to actual port numbers
  public static final PWMVictorSPX wheelArmRight = new PWMVictorSPX(1);
  public static final PWMVictorSPX wheelArmAngle = new PWMVictorSPX(0);
  public static final PWMVictorSPX leftLiftWheel = new PWMVictorSPX(3);
  public static final PWMVictorSPX rightLiftWheel = new PWMVictorSPX(2);

  public static final Encoder wheelArmEncoder = new Encoder(0, 1, false);// TODO: Fix encoder constructor

  public static final DoubleSolenoid hatchSolenoidTop = new DoubleSolenoid(0, 3); // TODO: set to actual solenoid values
  public static final DoubleSolenoid liftSystemBack = new DoubleSolenoid(1, 5);
  public static final DoubleSolenoid liftSystemFront = new DoubleSolenoid(2, 4);

  public static final LogitechF310 controller1 = new LogitechF310(1);
}
