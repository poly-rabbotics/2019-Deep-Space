/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.triggers;

import edu.wpi.first.wpilibj.buttons.Trigger;
import java.util.function.BooleanSupplier;;

/**
 * Add your docs here.
 */
public class BooleanTrigger extends Trigger {
  private BooleanSupplier condition;

  public BooleanTrigger(BooleanSupplier cond) {
    condition = cond;
  }

  @Override
  public boolean get() {
    return condition.getAsBoolean();
  }
}
