/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.kauailabs.navx.frc.AHRS;
import com.kauailabs.navx.frc.AHRS.SerialDataType;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/*import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
*/
import frc.robot.commands.DriveCommand;
import frc.robot.commands.HatchPusherCommand;
import frc.robot.commands.LiftCommandGroup;
import frc.robot.commands.WheelArmCommand;
import frc.robot.commands.ArmAngleCommand;
import frc.robot.controls.*;
import frc.robot.subsystems.*;
import frc.robot.subsystems.vision.Cameras;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  public static Drive drive = new Drive();
  public static HatchPusher hatchPusher = new HatchPusher();
  public static WheelArm wheelArm = new WheelArm();
  public static LiftSystem liftSystem = new LiftSystem();
  public static ArmAngle armAngle = new ArmAngle();
  public static DriveController controller = new F310Controller();
  public static OI m_oi;
  public int myCounter = 0;
  AHRS ahrs;

  Command m_autonomousCommand;
  SendableChooser<Command> m_chooser = new SendableChooser<>();

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    m_oi = new OI();
    // chooser.addOption("My Auto", new MyAutoCommand());
    //SmartDashboard.putData("Auto mode", m_chooser);

    /*
    // hatchCamera = CameraServer.getInstance().startAutomaticCapture("hatch",0);
    // hatchCamera.setResolution(640, 480);
     portCamera = CameraServer.getInstance().startAutomaticCapture("port",1);
     portCamera.setResolution(1280, 960);

    new Thread(()->{
      UsbCamera camera = CameraServer.getInstance().startAutomaticCapture(0);
      camera.setResolution(640, 480);
      CvSink cvSink = CameraServer.getInstance().getVideo();
      CvSource outputStream = CameraServer.getInstance().putVideo("7042", 640, 480);
      Mat source = new Mat();
      Mat output = new Mat();
      while(!Thread.interrupted()){
        cvSink.grabFrame(source);
        Imgproc.cvtColor(source, output, Imgproc.COLOR_BGR2GRAY);
        outputStream.putFrame(output);
      }
    }).start();
    */
		Cameras.setup(); // Setup and Connection to Pixy2 and Microsoft Camera
		SmartDashboard.putNumber("Iteration", myCounter);
    try {
			/***********************************************************************
			 * navX-MXP:
			 * - Communication via RoboRIO MXP (SPI, I2C, TTL UART) and USB.            
			 * - See http://navx-mxp.kauailabs.com/guidance/selecting-an-interface.
			 * 
			 * navX-Micro:
			 * - Communication via I2C (RoboRIO MXP or Onboard) and USB.
			 * - See http://navx-micro.kauailabs.com/guidance/selecting-an-interface.
			 * 
			 * Multiple navX-model devices on a single robot are supported.
			 ************************************************************************/
            //ahrs = new AHRS(SerialPort.Port.kUSB1);
            ahrs = new AHRS(SerialPort.Port.kMXP, SerialDataType.kProcessedData, (byte)50);
            ahrs.enableLogging(true);
        } catch (RuntimeException ex ) {
            DriverStation.reportError("Error instantiating navX MXP:  " + ex.getMessage(), true);
        }

  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
		SmartDashboard.putNumber("Iteration", myCounter);
            /* Display 6-axis Processed Angle Data                                      */
            SmartDashboard.putBoolean(  "IMU_Connected",        ahrs.isConnected());
            SmartDashboard.putBoolean(  "IMU_IsCalibrating",    ahrs.isCalibrating());
            SmartDashboard.putNumber(   "IMU_Yaw",              ahrs.getYaw());
            SmartDashboard.putNumber(   "IMU_Pitch",            ahrs.getPitch());
            SmartDashboard.putNumber(   "IMU_Roll",             ahrs.getRoll());
            
            /* Display tilt-corrected, Magnetometer-based heading (requires             */
            /* magnetometer calibration to be useful)                                   */
            
            SmartDashboard.putNumber(   "IMU_CompassHeading",   ahrs.getCompassHeading());
            
            /* Display 9-axis Heading (requires magnetometer calibration to be useful)  */
            SmartDashboard.putNumber(   "IMU_FusedHeading",     ahrs.getFusedHeading());

            /* These functions are compatible w/the WPI Gyro Class, providing a simple  */
            /* path for upgrading from the Kit-of-Parts gyro to the navx MXP            */
            
            SmartDashboard.putNumber(   "IMU_TotalYaw",         ahrs.getAngle());
            SmartDashboard.putNumber(   "IMU_YawRateDPS",       ahrs.getRate());

            /* Display Processed Acceleration Data (Linear Acceleration, Motion Detect) */
            
            SmartDashboard.putNumber(   "IMU_Accel_X",          ahrs.getWorldLinearAccelX());
            SmartDashboard.putNumber(   "IMU_Accel_Y",          ahrs.getWorldLinearAccelY());
            SmartDashboard.putBoolean(  "IMU_IsMoving",         ahrs.isMoving());
            SmartDashboard.putBoolean(  "IMU_IsRotating",       ahrs.isRotating());

            /* Display estimates of velocity/displacement.  Note that these values are  */
            /* not expected to be accurate enough for estimating robot position on a    */
            /* FIRST FRC Robotics Field, due to accelerometer noise and the compounding */
            /* of these errors due to single (velocity) integration and especially      */
            /* double (displacement) integration.                                       */
            
            SmartDashboard.putNumber(   "Velocity_X",           ahrs.getVelocityX());
            SmartDashboard.putNumber(   "Velocity_Y",           ahrs.getVelocityY());
            SmartDashboard.putNumber(   "Displacement_X",       ahrs.getDisplacementX());
            SmartDashboard.putNumber(   "Displacement_Y",       ahrs.getDisplacementY());
            
            /* Display Raw Gyro/Accelerometer/Magnetometer Values                       */
            /* NOTE:  These values are not normally necessary, but are made available   */
            /* for advanced users.  Before using this data, please consider whether     */
            /* the processed data (see above) will suit your needs.                     */
            
            SmartDashboard.putNumber(   "RawGyro_X",            ahrs.getRawGyroX());
            SmartDashboard.putNumber(   "RawGyro_Y",            ahrs.getRawGyroY());
            SmartDashboard.putNumber(   "RawGyro_Z",            ahrs.getRawGyroZ());
            SmartDashboard.putNumber(   "RawAccel_X",           ahrs.getRawAccelX());
            SmartDashboard.putNumber(   "RawAccel_Y",           ahrs.getRawAccelY());
            SmartDashboard.putNumber(   "RawAccel_Z",           ahrs.getRawAccelZ());
            SmartDashboard.putNumber(   "RawMag_X",             ahrs.getRawMagX());
            SmartDashboard.putNumber(   "RawMag_Y",             ahrs.getRawMagY());
            SmartDashboard.putNumber(   "RawMag_Z",             ahrs.getRawMagZ());
            SmartDashboard.putNumber(   "IMU_Temp_C",           ahrs.getTempC());
            SmartDashboard.putNumber(   "IMU_Timestamp",        ahrs.getLastSensorTimestamp());
            
            /* Omnimount Yaw Axis Information                                           */
            /* For more info, see http://navx-mxp.kauailabs.com/installation/omnimount  */
            AHRS.BoardYawAxis yaw_axis = ahrs.getBoardYawAxis();
            SmartDashboard.putString(   "YawAxisDirection",     yaw_axis.up ? "Up" : "Down" );
            SmartDashboard.putNumber(   "YawAxis",              yaw_axis.board_axis.getValue() );
            
            /* Sensor Board Information                                                 */
            SmartDashboard.putString(   "FirmwareVersion",      ahrs.getFirmwareVersion());
            
            /* Quaternion Data                                                          */
            /* Quaternions are fascinating, and are the most compact representation of  */
            /* orientation data.  All of the Yaw, Pitch and Roll Values can be derived  */
            /* from the Quaternions.  If interested in motion processing, knowledge of  */
            /* Quaternions is highly recommended.                                       */
            SmartDashboard.putNumber(   "QuaternionW",          ahrs.getQuaternionW());
            SmartDashboard.putNumber(   "QuaternionX",          ahrs.getQuaternionX());
            SmartDashboard.putNumber(   "QuaternionY",          ahrs.getQuaternionY());
            SmartDashboard.putNumber(   "QuaternionZ",          ahrs.getQuaternionZ());
            
            /* Connectivity Debugging Support                                           */
            SmartDashboard.putNumber(   "IMU_Byte_Count",       ahrs.getByteCount());
            SmartDashboard.putNumber(   "IMU_Update_Count",     ahrs.getUpdateCount());
		Cameras.run();
  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   * You can use it to reset any subsystem information you want to clear when
   * the robot is disabled.
   */
  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString code to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional commands to the
   * chooser code above (like the commented example) or additional comparisons
   * to the switch structure below with additional strings & commands.
   */
  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_chooser.getSelected();

    /*
     * String autoSelected = SmartDashboard.getString("Auto Selector",
     * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
     * = new MyAutoCommand(); break; case "Default Auto": default:
     * autonomousCommand = new ExampleCommand(); break; }
     */

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.start();
    }
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) 
      m_autonomousCommand.cancel();
    new DriveCommand().start();
    new HatchPusherCommand().start();
    new WheelArmCommand().start();
    new LiftCommandGroup().start();
    new ArmAngleCommand().start();
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
