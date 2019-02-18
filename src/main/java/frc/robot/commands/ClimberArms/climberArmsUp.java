package frc.robot.commands.ClimberArms;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class climberArmsUp extends Command {

    public climberArmsUp(){
        requires(Robot.ClimberArms);
    }

    @Override
    protected void initialize() {
        Robot.ClimberArms.moveUp(1);
    }
    @Override
    protected void execute() {

    }

    @Override
    protected boolean isFinished() {
        return true;
    }
    @Override
    protected void end() {

    }

    @Override
    protected void interrupted() {
    this.end();
    }

}