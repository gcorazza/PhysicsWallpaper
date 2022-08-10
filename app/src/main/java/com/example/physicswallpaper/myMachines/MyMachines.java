package com.example.physicswallpaper.myMachines;

import static android.graphics.Color.GRAY;
import static android.graphics.Color.LTGRAY;

import com.example.physicswallpaper.phunletDto.PhunletBodyDto;
import com.example.physicswallpaper.phunletDto.PhunletDtoBuilder;
import com.example.physicswallpaper.phunletDto.machine.JointDto;
import com.example.physicswallpaper.phunletDto.machine.MachineDto;
import com.example.physicswallpaper.phunletDto.machine.MachinePartDto;
import com.example.physicswallpaper.phunletDto.machine.RevoluteJointDto;

import org.jbox2d.common.Vec2;

import java.util.ArrayList;

public class MyMachines {

    public static MachineDto cutie() {

        ArrayList<MachinePartDto> machinePartDtos = new ArrayList<>();
        ArrayList<JointDto> jointDtos = new ArrayList<>();

        PhunletBodyDto body = PhunletDtoBuilder.createBody();
        PhunletDtoBuilder.addCircle(body, GRAY, 1, 10, new Vec2());
        PhunletDtoBuilder.addCircle(body, LTGRAY, 0.5f, 10, new Vec2(1.2f, 0));
        machinePartDtos.add(new MachinePartDto(body, new Vec2(), 0));
        machinePartDtos.add(new MachinePartDto(body, new Vec2(), 90));
        jointDtos.add(new RevoluteJointDto(0, 1, new Vec2(), true, 10, 10000));

        return new MachineDto(machinePartDtos, jointDtos);
    }

}
