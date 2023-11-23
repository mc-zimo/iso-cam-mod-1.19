package net.zimo.isocam.Accessor;

import net.minecraft.client.option.Perspective;
import net.minecraft.entity.Entity;
import net.minecraft.world.BlockView;

public interface CameraMixinAccessor {
    void update(BlockView area, Entity focusedEntity, Perspective perspective, float tickDelta);
}
