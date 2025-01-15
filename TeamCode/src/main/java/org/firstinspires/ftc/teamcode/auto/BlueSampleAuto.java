package org.firstinspires.ftc.teamcode.auto;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ProfileAccelConstraint;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.Trajectory;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
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
        Pose2d initialPose = new Pose2d(48 - halfWidth, 72 - halfLength, Math.toRadians(270));

        // Use RR drivetrain
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        // mechanism initialization
        Lift lift3 = new Lift(hardwareMap, telemetry);
        lift3.Init();
        SlideIntake slideIntake1 = new SlideIntake(hardwareMap, telemetry);
        slideIntake1.Init();
        RobotServos servos   = new RobotServos(hardwareMap);
        double slideIntakeStartPos = slideIntake1.GetPosition();

        TrajectoryActionBuilder initialDrive = drive.actionBuilder(initialPose)
                .strafeTo(new Vector2d(53 - halfWidth, 63 - halfLength), null, new ProfileAccelConstraint(-80, 80))
                ;

        // building trajectories
        TrajectoryActionBuilder driveToBucket = drive.actionBuilder(new Pose2d(53 - halfWidth, 63 - halfLength, Math.toRadians(270)))
                .splineToLinearHeading(new Pose2d(70 - halfWidth, 70 -  halfLength, Math.toRadians(20)),Math.toRadians(20), null, new ProfileAccelConstraint(-80, 80));

        TrajectoryActionBuilder driveBack = drive.actionBuilder(new Pose2d(70 - halfWidth, 70 - halfLength, Math.toRadians(20)))
                .setTangent(Math.toRadians(30))
                .lineToY(65 - halfLength, null, new ProfileAccelConstraint(-80, 80))
                ;

        // creating actions
        Action trajectoryBucketAction = driveToBucket.build();
        Action trajectoryBack = driveBack.build();
        Action liftToHighBox = lift3.liftUp(4800, 0.8);
        Action liftDown = lift3.liftDown(0, 0.8);
        Action openTopClaw = servos.moveTopClaw(0.0);
        Action flipTClawOut = servos.moveFlipTClaw(0.45); // for sample
        Action rotateTClaw = servos.moveRotateTClaw(1); // for sample
        Action closeTopClaw = servos.moveTopClaw(1.0);
        Action closeBottomClaw = servos.moveBottomClaw(1.0);
        Action openBottomClaw = servos.moveBottomClaw(0.0);
        Action rotateArmOut    = servos.moveRotateArm(1.0);
        Action rotateArmIn    = servos.moveRotateArm(0.0);
        //Action slideIntakeOut = slideIntake2.slideMoveAction(-1079, 0.7);
        //Action slideInTakeIn = slideIntake2.slideMoveAction((int)slideIntakeStartPos, 0.7);

        Actions.runBlocking(closeTopClaw);

        while (!isStarted() && !isStopRequested()) {
            telemetry.update();
        }

        waitForStart();

        while (opModeIsActive()) {
            lift3.Init();
            telemetry.addData("Autonomous", "Started");

            Actions.runBlocking(
                    new SequentialAction(
                            flipTClawOut,
                            rotateTClaw,
                            liftToHighBox,
                            trajectoryBucketAction,
                            new SleepAction(0.5),
                            openTopClaw,
                            new SleepAction(0.5),
                            trajectoryBack,
                            new SleepAction(0.5),
                            liftDown
                            /*openBottomClaw,
                            slideIntakeOut,
                            rotateArmOut,
                            new SleepAction(1),
                            closeBottomClaw,
                            new SleepAction(0.5),
                            rotateArmIn,
                            slideInTakeIn))*/));

        }
    }
}
