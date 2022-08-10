package com.example.physicswallpaper.phunletDto.machine;

import java.util.List;

public class MachineDto {
    private final List<MachinePartDto> machinePartDtos;
    private final List<JointDto> jointDtos;

    public MachineDto(List<MachinePartDto> machinePartDtos, List<JointDto> jointDtos) {
        this.machinePartDtos = machinePartDtos;
        this.jointDtos = jointDtos;
    }

    public List<MachinePartDto> getMachinePartDtos() {
        return machinePartDtos;
    }

    public List<JointDto> getJointDtos() {
        return jointDtos;
    }
}
