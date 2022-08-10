package com.example.physicswallpaper.phunletDto.machine;

import com.example.physicswallpaper.phunletDto.PhunletBodyDto;

import org.jbox2d.common.Vec2;

public class MachinePartDto {
    private final PhunletBodyDto phunletBodyDto;
    private final Vec2 offset;
    private final float offDegree;

    public MachinePartDto(PhunletBodyDto phunletBodyDto, Vec2 offset, float offDegree) {
        this.phunletBodyDto = phunletBodyDto;
        this.offset = offset;
        this.offDegree = offDegree;
    }


    public PhunletBodyDto getPhunletBodyDto() {
        return phunletBodyDto;
    }

    public Vec2 getOffset() {
        return offset;
    }

    public float getOffDegree() {
        return offDegree;
    }
}
