/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot; //Say what package this file is in. This lets this file access
                   //other files that are in the same package. To access files
                   //outside of this package, we have to import them.
                   //Note that it is just the file path
                   //(after the java folder) except with . replacing /

//This is the first file outside of the package that we have to import.
//We include this just for code readability: unless I am mistaken, LogitechF310
//is just a wrapper for our Human Interface Device (GenericHID) that replaces 
//the channel numbers with things that are more human-readable, so we can say
//controller1.getX(Hand.kLeft) instead of controller1.getRawAxis(2) (because who
//would guess that 2 represents the channel # of the left-hand X button)
import org.usfirst.frc.team4999.controllers.LogitechF310;

//DigitalInput is similar. It doesn't do complicated processing, it just tells us
//if a Digital I/O (DIO) pin is high or low.
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid; //used to control pneumatic solenoids
                                             //(explained in HatchMechanism.java)
//Until we import our 3 types of motor controllers (Spark, Talon, and VictorSP),
//this file doesn't know what those motor controllers are, and it can't do anything
//with them.
import edu.wpi.first.wpilibj.Spark;
//import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.VictorSP;
//XboxController is just like the LogitechF310 wrapper we got from Momentum, except
//we get it from FIRST, not Momentum (team 4999).
import edu.wpi.first.wpilibj.XboxController;
//import frc.robot.controls.XBoxController; //Ask Juliana or Mr. Powers because I don't get it. 
                                          //Because the B
                                          //in XBox is capitalized, and lower down in
                                          //the code it's not, we do not use this class
                                          //in this file.
//import edu.wpi.first.wpilibj.Encoder; //Commented out because we didn't use this. We would
                                        //have needed it to use an encoder to get arm position.
/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 
 * Postscript: "magic numbers" are hard-coded numbers (fixed values like 8.4 or 220) that are
 * not variables. If you define a variable or a constant called, for example, TICKS_PER_ROTATION,
 * everyone knows what that value is doing. If you just suddenly divide by 220, people won't 
 * realize that you just were dividing by the # of ticks per rotation. Besids, if that number changes,
 * you would have to hunt for that 220 instead of just going to the top of the file where you wrote
 * that TICKS_PER_ROTATION = 220
 * TLDR: Magic numbers are numbers without names, and they are bad!
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
  
  
  /*
  * ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
  * These are the motor controllers used in the drivetrain -- which are all cheap ($40) white Sparks.
  * They are connected in RoboRIO PWM ports 8, 7, 6, and 5, respectively, so that the RoboRIO
  * can control them.
  * ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
  */
  public static final Spark leftFront = new Spark(8);
  public static final Spark leftBack = new Spark(7);
  public static final Spark rightFront = new Spark(6);
  public static final Spark rightBack = new Spark(5);
  
  /*
  * ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
  * These are the motor controllers used in the arm mechanism.
  * They are connected in RoboRIO PWM ports 0, 1, 4, 3, and 2, respectively, so that the RoboRIO
  * can control them.
  * ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
  */
                                                              //There's a story behind the below comment. The wires were
                                                              //the wrong lengths, so they swapped ports. It was nice
                                                              //of Juliana to make a note of that in the code.
  public static final VictorSP wheelArmLeft = new VictorSP(0);// 2019-03-15 switch 0 and 4 - won't reach this was 4 --> 0
  public static final VictorSP wheelArmRight = new VictorSP(1);
  public static final VictorSP wheelArmAngle = new VictorSP(4);// 2019-03-15 switch 0 and 4 - won't reach this was 0 --> 4
  public static final VictorSP leftLiftWheel = new VictorSP(3); 
  public static final VictorSP rightLiftWheel = new VictorSP(2);

 // public static final Encoder wheelArmEncoder = new Encoder(0,1,false); //commented out b/c we didn't use this
  /*
  * ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
  * These are the limit switches that tell us if the arm has gone all the way up or all the way
  * down, so we know to stop trying to move the arm. (Remember, we don't want to try to move when
  * we can't because that makes motors overheat. The electrical energy becomes heat instead of 
  * motion!)
  * The limit switches are connected to DIO (Digital Input/Output) ports 2 and 3, respectively
  * ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
  */
  public static final DigitalInput armSwitch = new DigitalInput(2);
  public static final DigitalInput armSwitch2 = new DigitalInput(3);

  /*
  * ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
  * Create computer representations of the double solenoids, and say what ports they're 
  * connected to. They're connected to two ports: one to extend the cylinder, and one to
  * retract it.
  * ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! 
  */
  //Forward port is 0 and reverse port is 3. That means that, when we say hatchSolenoidTop.set(Value.kReverse),
  //we are delivering power to port 0 on the Pneumatics Control Module.
  //(For consistency in our code, I think we generally used "forward" to mean "extended" and "reverse" to mean
  //retracted. However, the documentation calls them "forward" and "reverse."
  public static final DoubleSolenoid hatchSolenoidTop = new DoubleSolenoid(0,3);
  public static final DoubleSolenoid liftSystemFront = new DoubleSolenoid(1,4);
  public static final DoubleSolenoid liftSystemBack = new DoubleSolenoid(2,5);
  
  public static final LogitechF310 controller1 = new LogitechF310(0);
  public static final XboxController controller2 = new XboxController(1); //This is super confusing, but this is
                                                                          //the XboxController (lowercase b) that we
                                                                          //took from FRC, not the XBoxController (uppercase B)
                                                                          //that we made ourselves.
}
