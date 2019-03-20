package frc.robot.nerdyfiles;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

public class PDP extends Subsystem {


    public PDP() {


    }

  public void initDefaultCommand() {
  }

  public void periodic() {
    SmartDashboard.putNumber("PDP_Total_Current", Robot.PDP.getTotalCurrent());
    SmartDashboard.putNumber("PDP_Port_0", Robot.PDP.getCurrent(0));
    SmartDashboard.putNumber("PDP_Port_1", Robot.PDP.getCurrent(1));
    SmartDashboard.putNumber("PDP_Port_2", Robot.PDP.getCurrent(2));
    SmartDashboard.putNumber("PDP_Port_3", Robot.PDP.getCurrent(3));
    SmartDashboard.putNumber("PDP_Port_4", Robot.PDP.getCurrent(4));
    SmartDashboard.putNumber("PDP_Port_5", Robot.PDP.getCurrent(5));
    SmartDashboard.putNumber("PDP_Port_6", Robot.PDP.getCurrent(6));
    SmartDashboard.putNumber("PDP_Port_7", Robot.PDP.getCurrent(7));
    SmartDashboard.putNumber("PDP_Port_8", Robot.PDP.getCurrent(8));
    SmartDashboard.putNumber("PDP_Port_9", Robot.PDP.getCurrent(9));
    SmartDashboard.putNumber("PDP_Port_10", Robot.PDP.getCurrent(10));
    SmartDashboard.putNumber("PDP_Port_11", Robot.PDP.getCurrent(11));
    SmartDashboard.putNumber("PDP_Port_12", Robot.PDP.getCurrent(12));
    SmartDashboard.putNumber("PDP_Port_13", Robot.PDP.getCurrent(13));
    SmartDashboard.putNumber("PDP_Port_14", Robot.PDP.getCurrent(14));
    SmartDashboard.putNumber("PDP_Port_15", Robot.PDP.getCurrent(15));

    SmartDashboard.putNumber("PDP_Temperature", Robot.PDP.getTemperature());
   // SmartDashboard.putNumber("PDP_", Robot.PDP.);

  }

}