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
public class Logger extends Subsystem {

   NetworkTable sd;
  //edu.wpi.first.networktables.NetworkTable sd;

  

  @Override
  public void initDefaultCommand() {
    // setDefaultCommand(new MySpecialCommand());
  }
    /**
   * Runs continuously during runtime. Currently used to display SmartDashboard
   * values
   */
  public void periodic() {

    sd= NetworkTable.getTable("Logger");
   // sdi= NetworkTableInstance.getTable("Loggeri");
   //sd.getSubTable("Logger");

    if (Robot.logger) {
      SmartDashboard.putNumber("Avg_Neo_Velocity", Robot.Chassis.getAverageNeoVelocity());
      sd.putNumber("/Logger/Avg_Neo_Velocity", Robot.Chassis.getAverageNeoVelocity());
      sd.putBoolean("/Logger/Left_Neo_Velocity",false);
      
      

    }
  }
}
