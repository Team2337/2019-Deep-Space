package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

/**
 * Add values to NetworkTables to be logged on driverstation
 */
public class Recorder extends Subsystem {



  public Recorder(){

  }

  @Override
  public void initDefaultCommand() {

  }
    /**
   * Runs continuously during runtime. Currently used to display SmartDashboard
   * values
   */
  public void periodic() {

    if (Robot.logger) {
      
      SmartDashboard.putNumber("Driver_Rt_Stick_X_Axis", Robot.oi.driverJoystick.getRightStickX());
      SmartDashboard.putNumber("Driver_Lt_Stick_Y_Axis", Robot.oi.driverJoystick.getLeftStickY());
      SmartDashboard.putBoolean("Driver_BumperLeft", Robot.oi.driverJoystick.bumperLeft.get());
      SmartDashboard.putBoolean("Driver_BumperRight", Robot.oi.driverJoystick.bumperRight.get());
      SmartDashboard.putBoolean("Driver_TriggerLeft", Robot.oi.driverJoystick.triggerLeft.get());
      SmartDashboard.putBoolean("Driver_TriggerRight", Robot.oi.driverJoystick.triggerRight.get());

      SmartDashboard.putNumber("Operator_Rt_Stick_X_Axis", Robot.oi.operatorJoystick.getRightStickX());
      SmartDashboard.putNumber("Operator_Rt_Stick_Y_Axis", Robot.oi.operatorJoystick.getRightStickY());
      SmartDashboard.putNumber("Operator_Lt_Stick_X_Axis", Robot.oi.operatorJoystick.getLeftStickX());
      SmartDashboard.putNumber("Operator_Lt_Stick_Y_Axis", Robot.oi.operatorJoystick.getLeftStickY());
      SmartDashboard.putBoolean("Operator_GreenA", Robot.oi.operatorJoystick.greenA.get());
      SmartDashboard.putBoolean("Operator_RedB", Robot.oi.operatorJoystick.redB.get());
      SmartDashboard.putBoolean("Operator_BlueX", Robot.oi.operatorJoystick.blueX.get());
      SmartDashboard.putBoolean("Operator_YellowY", Robot.oi.operatorJoystick.yellowY.get());
      SmartDashboard.putBoolean("Operator_TriggerLeft", Robot.oi.operatorJoystick.triggerLeft.get());
      SmartDashboard.putBoolean("Operator_TriggerRight", Robot.oi.operatorJoystick.triggerRight.get());
      SmartDashboard.putBoolean("Operator_BumperLeft", Robot.oi.operatorJoystick.bumperLeft.get());
      SmartDashboard.putBoolean("Operator_BumperRight", Robot.oi.operatorJoystick.bumperRight.get());
      SmartDashboard.putBoolean("Operator_PovUp", Robot.oi.operatorJoystick.povUp.get());
      SmartDashboard.putBoolean("Operator_PovDown", Robot.oi.operatorJoystick.povDown.get());
      SmartDashboard.putBoolean("Operator_PovLeft", Robot.oi.operatorJoystick.povLeft.get());
      SmartDashboard.putBoolean("Operator_PovRight", Robot.oi.operatorJoystick.povRight.get());
      SmartDashboard.putBoolean("Operator_Start", Robot.oi.operatorJoystick.start.get());
      SmartDashboard.putBoolean("Operator_Back", Robot.oi.operatorJoystick.back.get());

      SmartDashboard.putBoolean("DriverStation_YellowButton",Robot.oi.operatorControls.YellowButton.get());
      SmartDashboard.putBoolean("DriverStation_YellowSwitch",Robot.oi.operatorControls.YellowSwitch.get());

      SmartDashboard.putNumber("Air_Pressure", Robot.AirCompressor.getPressure());
      SmartDashboard.putBoolean("Air_Compressor", Robot.AirCompressor.status());
      SmartDashboard.putBoolean("Auton_HatchKicker", Robot.AutoHatchKicker.status());
      //Cargo_BigBrother (in subsystem)
      SmartDashboard.putBoolean("Cargo_Drawbridge", Robot.CargoDrawbridge.status());
      SmartDashboard.putNumber("Cargo_Escalator", Robot.CargoEscalator.status());
      SmartDashboard.putNumber("Cargo_Intake", Robot.CargoEscalator.status());
      SmartDashboard.putNumber("Cargo_Intake", Robot.CargoScore.status());
      //chassis (in subsystem)
      SmartDashboard.putBoolean("Climber_Deploy", Robot.ClimberDeploy.status());
      SmartDashboard.putBoolean("Hatch_Beak", Robot.HatchBeak.status());
      SmartDashboard.putBoolean("Hatch_Extender", Robot.HatchLauncher.status());
      SmartDashboard.putNumber("LED_Color", Robot.LED.status());

      //Lift (in subsystem)

      SmartDashboard.putNumber("Pigeon_FusedHeading", Robot.Pigeon.getFusedHeading());
      SmartDashboard.putNumber("Pigeon_AbsoluteCompass", Robot.Pigeon.getAbsoluteCompassHeading());
      SmartDashboard.putNumber("Pigeon_Roll", Robot.Pigeon.getRoll());
      SmartDashboard.putNumber("Pigeon_Pitch", Robot.Pigeon.getPitch());
      SmartDashboard.putNumber("Pigeon_Yaw", Robot.Pigeon.getYaw());
      SmartDashboard.putNumber("Pigeon_AngularRate", Robot.Pigeon.getAngularRate());
      SmartDashboard.putNumber("Pigeon_Temp", Robot.Pigeon.getTemp());

      SmartDashboard.putNumber("RoboWrangler_DriveSpeed", Robot.RoboWrangler.driveMotorStatus());
      SmartDashboard.putNumber("RoboWrangler_LassoSpeed", Robot.RoboWrangler.lassoMotorStatus());
      

      SmartDashboard.putBoolean("Shifter_Low", Robot.Shifter.status());

      SmartDashboard.putBoolean("TRexArms", Robot.TRexArms.status());

      //PDP (in Subsystem)

      //NerdyPath (in class)

      //BobDriveHelper
    }
  }
}
