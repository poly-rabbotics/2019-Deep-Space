package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Talon;
import frc.robot.RobotMap;

public class CargoMechanismLatch extends Subsystem {
  private Talon latchMotor;
  private static final double CURRENT_THRESHOLD = 7.0;  //stall current is 12A
  private static final double STEP = 0.1;
  private static final double TOLERANCE = 0.3;
  private static final double DOWN_DELAY = 1.2; //seat motor is 24 rpm if no mechanical resistance. This should be time for almost 180 degree rotation
  
  private static Timer t.start();
  
  private double motorControl;
  private double t_up_last;
  
  public CargoMechanismLatch(){
    super("CargoMechanismLatch");
    latchMotor = RobotMap.latchMotor;
    motorControl = 0;
    timeLast = 0;                        //If the mechanism doesn't start the game rotated all the way up, there will be problems. it will rotate down too much
    addChild("latch motor", latchMotor);
  }
  /*
  * Postcondition: return false if unable to continue moving upwards
  */
  public void spinUpwards() {
    double current = pdp.getCurrent(1); //TODO: CHANGE TO ACTUAL PORT VALUE
    if (current > CURRENT_THRESHOLD + TOLERANCE)
    {
      motorControl -= STEP;     //TODO: Make sure this spins it the correct direction
    }
    else if (current < CURRENT_THRESHOLD - TOLERANCE) {
      motorControl += STEP;
    }
    latchMotor.set(motorControl);
    timeLast = t.get();
  }
  public void spinDownwards() {
    if (t.get() - timeLast > 1.2) {
      latchMotor.set(-1);
    }
    else {
      latchMotor.set(0);
    }
  }
