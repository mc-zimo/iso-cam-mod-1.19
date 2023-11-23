package net.zimo.isocam.Accessor;

import net.minecraft.client.option.Perspective;
import net.minecraft.entity.Entity;
import net.minecraft.world.BlockView;
import net.zimo.isocam.mixin.PerspectiveMixin;

public interface PerspectiveMixinAccessor {
    void update(BlockView area, Entity focusedEntity, Perspective perspective, float tickDelta);
    Option getOption();
}
