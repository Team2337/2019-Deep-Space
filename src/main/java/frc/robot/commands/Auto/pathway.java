package frc.robot.commands.Auto;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;

public class Pathway {
   public static double pathweaverConversion;  //17.5 //35.166666666 - Our conversion from our trials, 33.33333 
   /**
    * Converts inches to pathweaver units
    * @param inch - desired distance
    * @return returns the pathweaver value
    */
   private static double convert(double inch) {
       pathweaverConversion = (inch*0.33);
    return (inch/(pathweaverConversion));
   }

   public static Waypoint[] points = new Waypoint[] {
       // Waypoint @ x=0, y=0,   exit angle=0 radians
        new Waypoint(0, 0, 0),            
        new Waypoint(3, -1, 0)
        //new Waypoint(convert(195), 0, 0) //256 inches out
        //180 = 237
    };

    public static Waypoint[] pointsPoints = new Waypoint[] {
        new Waypoint(0, 0, 0),
        new Waypoint(4, 0, 0)
    };
}