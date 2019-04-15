package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Talon;
import frc.robot.RobotMap;

public class CargoMechanismLatch extends Subsystem {
  private Talon latchMotor;
  public static final double CURRENT_THRESHOLD = 9.0;  //stall current is 12A
  public CargoMechanismLatch(){
    super("CargoMechanismLatch");
    latchMotor = RobotMap.latchMotor;
    addChild("latch motor", latchMotor);
  }
  /*
  * Postcondition: return false if unable to continue moving upwards
  */
  public boolean spinUpwards() {
    double current = pdp.getCurrent(1); //TODO: CHANGE TO ACTUAL PORT VALUE
    if (current > CURRENT_THRESHOLD)
    {
      latchMotor.set(0.1);
      return false;
    }
    else {
      latchMotor.set(1);
      return true;
    }
  }
