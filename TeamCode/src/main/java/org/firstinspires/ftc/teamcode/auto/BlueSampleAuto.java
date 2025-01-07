package org.firstinspires.ftc.teamcode.auto;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ProfileAccelConstraint;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.MecanumDrive;

@Config
@Autonomous(name="Blue Sample Auto", group = "Autonomous")
public class BlueSampleAuto extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        double halfWidth = 7.4375;
        double halfLength = 8.125;
        Pose2d initialPose = new Pose2d(-24 - halfWidth, -72 + halfLength, Math.toRadians(90));

        // Use RR drivetrain
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        // mechanism initalization
        Lift lift = new Lift(hardwareMap, telemetry);
        lift.Init();
        SlideIntake slideIntake = new SlideIntake(hardwareMap, telemetry);
        slideIntake.Init();
        RobotServos servos   = new RobotServos(hardwareMap);

        // building trajectories
        TrajectoryActionBuilder driveToBucket = drive.actionBuilder(initialPose)
                .splineToLinearHeading(new Pose2d(-70 -halfWidth, -70 + halfLength, Math.toRadians(210)),Math.toRadians(210), null, new ProfileAccelConstraint(-80, 80));

        // createing actions
        Action trajectoryBucketAction = driveToBucket.build();
        Action liftToHighBox = lift.moveLiftAction(4800, 0.8);
        Action liftDown = lift.moveLiftAction(0, 0.8);
        Action openTopClaw = servos.moveTopClaw(0.0);
        Action closeTopClaw = servos.moveTopClaw(1.0);
        Action rotateArmOut    = servos.moveRotateArm(0.7);

        Actions.runBlocking(closeTopClaw);

        while (!isStarted() && !isStopRequested()) {
            telemetry.update();
        }

        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("Autonomous", "Started");

            Actions.runBlocking(
                    new SequentialAction(
                            trajectoryBucketAction,
                            liftToHighBox,
                            liftDown
                    ));
        }
    }
}
