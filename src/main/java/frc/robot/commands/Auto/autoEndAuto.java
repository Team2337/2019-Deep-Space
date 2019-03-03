package frc.robot.commands.Auto;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;

public class autoEndAuto extends InstantCommand {

    protected void initialize() {
        Robot.autonomousCommand.cancel();
    }
}