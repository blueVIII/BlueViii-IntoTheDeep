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
        Lift lift3 = new Lift(hardwareMap);
        SlideIntake slideIntake1 = new SlideIntake(hardwareMap);
        RobotServos servos   = new RobotServos(hardwareMap);

        TrajectoryActionBuilder initialDrive = drive.actionBuilder(initialPose)
                .strafeTo(new Vector2d(53 - halfWidth, 63 - halfLength), null, new ProfileAccelConstraint(-80, 80))
                ;

        // building trajectories
        TrajectoryActionBuilder driveToBucket = drive.actionBuilder(new Pose2d(53 - halfWidth, 63 - halfLength, Math.toRadians(270)))
                .splineToLinearHeading(new Pose2d(66 - halfWidth, 66 -  halfLength, Math.toRadians(30)),Math.toRadians(30), null, new ProfileAccelConstraint(-80, 80));

        TrajectoryActionBuilder driveBack = drive.actionBuilder(new Pose2d(67 - halfWidth, 67 - halfLength, Math.toRadians(30)))
                .lineToY(65 - halfLength)
                .splineToLinearHeading(new Pose2d(57 - halfWidth, 54 - halfLength, Math.toRadians(78)),  Math.toRadians(78),null, new ProfileAccelConstraint(-80, 80))
                ; //-1170

        TrajectoryActionBuilder driveToBucket2 = drive.actionBuilder(new Pose2d(57 - halfWidth, 54 - halfLength, Math.toRadians(90)))
                .splineToLinearHeading(new Pose2d(67 - halfWidth, 67 -  halfLength, Math.toRadians(40)),Math.toRadians(40), null, new ProfileAccelConstraint(-80, 80));

        TrajectoryActionBuilder driveBack2 = drive.actionBuilder(new Pose2d(67 - halfWidth, 67 - halfLength, Math.toRadians(25)))
                .lineToY(62 - halfLength)
                ; //-1170

        // creating actions
        Action trajectoryBucketAction = driveToBucket.build();
        Action trajectoryBucketAction2 = driveToBucket2.build();
        Action trajectoryBack = driveBack.build();
        Action trajectoryBack2 = driveBack2.build();
        Action liftToHighBox = lift3.liftUp(4800);
        Action liftToHighBox2 = lift3.liftUp(4800);
        Action liftDown = lift3.liftDown(0);
        Action liftDown2 = lift3.liftDown(0);
        Action openTopClaw = servos.moveTopClaw(0.0);
        Action openTopClaw2 = servos.moveTopClaw(0.0);
        Action flipTClawOut = servos.moveFlipTClaw(0.45); // for sample
        Action flipTClawOut2 = servos.moveFlipTClaw(0.45); // for sample
        Action rotateTClaw = servos.moveRotateTClaw(1); // for sample
        Action rotateTClaw2 = servos.moveRotateTClaw(1); // for sample
        Action closeTopClaw = servos.moveTopClaw(1.0);
        Action closeTopClaw2 = servos.moveTopClaw(1.0);
        Action closeBottomClaw = servos.moveBottomClaw(1.0);
        Action openBottomClaw = servos.moveBottomClaw(0.0);
        Action openBottomClaw2 = servos.moveBottomClaw(0.0);
        Action rotateArmOut    = servos.moveRotateArm(1.0);
        Action rotateArmIn    = servos.moveRotateArm(0.0);
        Action slideIntakeOut = slideIntake1.slideOut(-520);
        Action slideIntakeIn = slideIntake1.slideIn(-120);
        Action rotateBClaw = servos.moveRotateBClaw(0.70);
        Action flipTClawIn = servos.moveFlipTClaw(0.95);
        Action rotateTClawIn = servos.moveRotateTClaw(0.65);
        Action openTClaw = servos.moveTopClaw(0.45);
        //Action slideIntakeOut = slideIntake2.slideMoveAction(-1079, 0.7);
        //Action slideInTakeIn = slideIntake2.slideMoveAction((int)slideIntakeStartPos, 0.7);

        Actions.runBlocking(closeTopClaw);

        while (!isStarted() && !isStopRequested()) {
            telemetry.update();
        }

        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("Autonomous", "Started");

            Actions.runBlocking(
                    new SequentialAction(
                            rotateBClaw,
                            flipTClawOut,
                            rotateTClaw,
                            liftToHighBox,
                            trajectoryBucketAction,
                            new SleepAction(0.5),
                            openTopClaw,
                            new SleepAction(0.5),
                            trajectoryBack,
                            new SleepAction(0.5),
                            liftDown,
                            slideIntakeOut,
                            new SleepAction(1.3),
                            openBottomClaw,
                            rotateArmOut,
                            new SleepAction(1.0),
                            closeBottomClaw,
                            new SleepAction(1.0),
                            slideIntakeIn,
                            new SleepAction(1.3),
                            rotateArmIn,
                            new SleepAction(1.0),
                            openTClaw,
                            rotateTClawIn,
                            flipTClawIn,
                            new SleepAction(1.0),
                            closeTopClaw2,
                            new SleepAction(0.5),
                            openBottomClaw2,
                            new SleepAction(0.5),
                            flipTClawOut2,
                            rotateTClaw2,
                            liftToHighBox2,
                            trajectoryBucketAction2,
                            new SleepAction(0.5),
                            openTopClaw2,
                            new SleepAction(0.5),
                            trajectoryBack2,
                            liftDown2
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
