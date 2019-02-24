package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import frc.robot.commands.AirCompressor.compressorTurnOn;

/**
 * By default, the compressor automatically turns itself on/off depending on the
 * pressure, but it can be disabled in a power-intensive situation
 */
public class AirCompressor extends Subsystem {

  public Compressor compressor = new Compressor();

  // Returns a voltage proportional to the air pressure in the robots air system
  public AnalogInput pressureSensor = new AnalogInput(Robot.Constants.pressureSensorPort);

  /**
   * By default, the compressor automatically turns itself on/off depending on the
   * pressure, but it can be disabled in a power-intensive situation
   */
  public AirCompressor() {

  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new compressorTurnOn());
  }

  /**
   * Enables the compressor to turn on when the air pressure gets below a given
   * pressure (Default 90 psi)
   */
  public void enable() {
    compressor.start();
  }

  /**
   * Disables the compressor
   */
  public void disable() {
    compressor.stop();
  }

  /**
   * Gets the pressure of the air system (PSI)
   * 
   * @return The pressure of the air system (PSI)
   */
  public double getPressure() {
    return (int) (pressureSensor.getVoltage() / 21.37);
  }

}