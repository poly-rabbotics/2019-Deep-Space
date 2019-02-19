//Modified version of code from https://github.com/Team3128/frc-java-3128/blob/master/src/org/team3128/hardware/encoder/velocity/CTREMagneticEncoder.java

package frc.robot.sensors;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSource;

/**
 * Driver for a CTRE Magnetic Encoder using DIO ports on the roborio.
 * 
 * When instantiated, it sets the quadrature distance from the absolute angle.
 * So, between 0 and 1 rotations. When reset, the distance goes to zero.
 * 
 * Internally, it uses a Counter to measure the PWM and a WPILib Encoder object
 * to measure the quadrature part.
 * 
 * @author Narwhal
 *
 */
public class CTREMagneticEncoder implements PIDSource //Method to implement PIDSource is also added here
{
	// had to get this from a forum post by a CTR employee
	private static final int PULSES_PER_REVOLUTION = 1024;

	Encoder encoder;
	Counter pwmCounter; //Used to determine period of PWM (period = pulse width?)

	double offsetDegrees; //Initial position

	/**
	 * 
	 * @param dataAPort
	 *            DIO port with the "A" data line plugged in (pin 7 on the
	 *            encoder)
	 * @param dataBPort
	 *            DIO port with the "B" data line plugged in to it (pin 5 on the
	 *            encoder)
	 * @param pwmPort
	 *            DIO port connected to pin 9 on the encoder, the PWM pin
	 * 
	 * @param inverted
	 *            whether or not the encoder is inverted
	 */
	public CTREMagneticEncoder(int dataAPort, int dataBPort, int pwmPort, double pulsesPerRevolution, boolean inverted) 
	{
		encoder = new Encoder(dataAPort, dataBPort);  //The encoder is only the object for handling (relative) 
								//quadrature feature of the encoder, not a (absolute) PWM feature
		encoder.setDistancePerPulse(360/pulsesPerRevolution); //"Distance" (the argument) is in degrees
		
		pwmCounter = new Counter(pwmPort); //instantiate pwmCounter
		pwmCounter.setSemiPeriodMode(true); //only count rising edges
					//Actually, I think the above comment from the original code is the opposite of what the program is doing -- it sets to count both rising and falling
		
		//wait for the pwm signal to be counted
		try
		{
			Thread.sleep(5);
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
		offsetDegrees = 0; //Untested; used because getAngle() uses offsetDegrees, so offsetDegrees has to be initialized to zero first
		offsetDegrees = getAngle(); //Store initial position
		
	}
	//I have NO CLUE why this was not overloaded in the original: 
	/**
	 * 
	 * @param dataAPort
	 *            DIO port with the "A" data line plugged in (pin 7 on the
	 *            encoder)
	 * @param dataBPort
	 *            DIO port with the "B" data line plugged in to it (pin 5 on the
	 *            encoder)
	 * @param pwmPort
	 *            DIO port connected to pin 9 on the encoder, the PWM pin
	 * 
	 * @param inverted
	 *            whether or not the encoder is inverted
	 */
	public CTREMagneticEncoder(int dataAPort, int dataBPort, int pwmPort, boolean inverted) //Overloaded because pulses per revolution 
		//is already defined as a constant, and because every CTREMagneticEncoder should have the same pulses per revolution anyway
	{
		encoder = new Encoder(dataAPort, dataBPort);
		encoder.setDistancePerPulse(360/PULSES_PER_REVOLUTION); //"Distance" (the argument) is in degrees
		
		pwmCounter = new Counter(pwmPort);
		pwmCounter.setSemiPeriodMode(true); //only count rising edges
		
		//wait for the pwm signal to be counted
		try
		{
			Thread.sleep(5);
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
		offsetDegrees = 0; //Untested
		offsetDegrees = getAngle();
		
	}
	
	@Override
	public void setPIDSourceType(PIDSourceType pidSource) { //Empty because we don't want to change the PIDSourceType
	}//For our purposes it should always be displacement
	
	@Override
	public PIDSourceType getPIDSourceType() {
		return PIDSourceType.kDisplacement; //Don't need to return a member var because this will stay displacement based
	}
	
	@Override
	public double pidGet() {
		return getAngle();	//Value used in PID is absolute position from PWM
	}

	public double getSpeedInRPM()
	{
		// getRate returns rotations / second
		return encoder.getRate() * 60;
	}

	public void clear()
	{
		encoder.reset();
		offsetDegrees = 0;	//Set current position as initial position (re-zero)
	}

	public double getDistanceInDegrees()
	{
		return encoder.getDistance() + (offsetDegrees / 360);
		//Since this returns degrees I have no idea why they converted offsetDegrees to radians.
		//(I think this is a mistake. However, fortunately we're not using it anyway I hope.)
	}

	public double getAngle() //This is the one we need
	{
		//from 1 to 4096 us
		return ((pwmCounter.getPeriod() - 1e-6) / 4095e-6) * 360 + offsetDegrees;
		//I added offsetDegrees -- it wasn't in the original
	}

	public double getRawValue()
	{
		return pwmCounter.getPeriod(); //Here I am confused. How do we trust that it will return 
		//period between rising and falling and not the period between falling and rising? 
		//If it isn't consistent, it wouldn't work
	}

	public boolean canRevolveMultipleTimes()
	{
		return true;
	}

}
