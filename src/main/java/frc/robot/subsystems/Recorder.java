package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTableValue;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

/**
 * Add values to NetworkTables to be logged on driverstation
 */
public class Recorder extends Subsystem {

   NetworkTable sd;
  //edu.wpi.first.networktables.NetworkTable sd;

  public Recorder(){
    sd= NetworkTable.getTable("Logger");
   // sdi= NetworkTableInstance.getTable("Loggeri");
   //sd.getSubTable("Logger");
  }

  @Override
  public void initDefaultCommand() {
    // setDefaultCommand(new MySpecialCommand());
  }
    /**
   * Runs continuously during runtime. Currently used to display SmartDashboard
   * values
   */
  public void periodic() {

    if (Robot.logger) {
      SmartDashboard.putNumber("Avg_Neo_Velocity", Robot.Chassis.getAverageNeoVelocity());
      sd.putNumber("/Logger/Avg_Neo_Velocity", Robot.Chassis.getAverageNeoVelocity());
      sd.putBoolean("/Logger/Left_Neo_Velocity",false);
      
      sd.putNumber("Driver_Rt_Stick_X_Axis", Robot.oi.driverJoystick.getRightStickX());
      sd.putNumber("Driver_Lt_Stick_Y_Axis", Robot.oi.driverJoystick.getLeftStickY());
      sd.putBoolean("Driver_BumperLeft", Robot.oi.driverJoystick.bumperLeft.get());
      sd.putBoolean("Driver_BumperRight", Robot.oi.driverJoystick.bumperRight.get());
      sd.putBoolean("Driver_TriggerLeft", Robot.oi.driverJoystick.triggerLeft.get());
      sd.putBoolean("Driver_TriggerRight", Robot.oi.driverJoystick.triggerRight.get());

      sd.putNumber("Operator_Rt_Stick_X_Axis", Robot.oi.operatorJoystick.getRightStickX());
      sd.putNumber("Operator_Rt_Stick_Y_Axis", Robot.oi.operatorJoystick.getRightStickY());
      sd.putNumber("Operator_Lt_Stick_X_Axis", Robot.oi.operatorJoystick.getLeftStickX());
      sd.putNumber("Operator_Lt_Stick_Y_Axis", Robot.oi.operatorJoystick.getLeftStickY());
      sd.putBoolean("Operator_GreenA", Robot.oi.operatorJoystick.greenA.get());
      sd.putBoolean("Operator_RedB", Robot.oi.operatorJoystick.redB.get());
      sd.putBoolean("Operator_BlueX", Robot.oi.operatorJoystick.blueX.get());
      sd.putBoolean("Operator_YellowY", Robot.oi.operatorJoystick.yellowY.get());
      sd.putBoolean("Operator_TriggerLeft", Robot.oi.operatorJoystick.triggerLeft.get());
      sd.putBoolean("Operator_TriggerRight", Robot.oi.operatorJoystick.triggerRight.get());
      sd.putBoolean("Operator_BumperLeft", Robot.oi.operatorJoystick.bumperLeft.get());
      sd.putBoolean("Operator_BumperRight", Robot.oi.operatorJoystick.bumperRight.get());
      sd.putBoolean("Operator_PovUp", Robot.oi.operatorJoystick.povUp.get());
      sd.putBoolean("Operator_PovDown", Robot.oi.operatorJoystick.povDown.get());
      sd.putBoolean("Operator_PovLeft", Robot.oi.operatorJoystick.povLeft.get());
      sd.putBoolean("Operator_PovRight", Robot.oi.operatorJoystick.povRight.get());
      sd.putBoolean("Operator_Start", Robot.oi.operatorJoystick.start.get());
      sd.putBoolean("Operator_Back", Robot.oi.operatorJoystick.back.get());

      sd.putBoolean("DriverStation_YellowButton",Robot.oi.operatorControls.YellowButton.get());
      sd.putBoolean("DriverStation_YellowSwitch",Robot.oi.operatorControls.YellowSwitch.get());

      sd.putDouble("Air_Pressure", Robot.AirCompressor.getPressure());
      sd.putBoolean("Air_Compressor", Robot.AirCompressor.status());

      sd.putBoolean("Auton_HatchKicker", Robot.AutoHatchKicker.status());

      //Cargo_BigBrother (in subsystem)

      sd.putBoolean("Cargo_Drawbridge", Robot.CargoDrawbridge.status());

      sd.putDouble("Cargo_Escalator", Robot.CargoEscalator.status());

      sd.putDouble("Cargo_Intake", Robot.CargoEscalator.status());

      sd.putDouble("Cargo_Intake", Robot.CargoScore.status());

      //chassis (in subsystem)

      sd.putBoolean("Climber_Release", Robot.ClimberPneumatics.status());

      sd.putBoolean("Hatch_Beak", Robot.HatchBeak.status());
      
      sd.putBoolean("Hatch_Extender", Robot.HatchLauncher.status());

      //  LED..........TODO:>?

      //Lift (in subsystem)

      sd.putDouble("Pidgeon_FusedHeading", Robot.Pigeon.getFusedHeading());
      sd.putDouble("Pidgeon_AbsoluteCompass", Robot.Pigeon.getAbsoluteCompassHeading());
      sd.putDouble("Pidgeon_Roll", Robot.Pigeon.getRoll());
      sd.putDouble("Pidgeon_Pitch", Robot.Pigeon.getPitch());
      sd.putDouble("Pidgeon_Yaw", Robot.Pigeon.getYaw());
      sd.putDouble("Pidgeon_AngularRate", Robot.Pigeon.getAngularRate());
      sd.putDouble("Pidgeon_Temp", Robot.Pigeon.getTemp());

      sd.putBoolean("Shifter_Low", Robot.Shifter.status());

      //PDP ........................TODO:

      //NerdyPath (in class)

      //BobDriveHelper ??TODO:??
    }
  }
}
