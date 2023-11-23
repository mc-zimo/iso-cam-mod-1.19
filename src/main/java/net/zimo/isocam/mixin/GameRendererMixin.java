package net.zimo.isocam.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.world.BlockView;
import net.zimo.isocam.Accessor.CameraMixinAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {

    @Shadow
    private MinecraftClient client;

    @Redirect(method = "renderWorld", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/Camera;update(Lnet/minecraft/world/BlockView;Lnet/minecraft/entity/Entity;ZZF)V"))
    public void updateCamera(Camera camera, BlockView area, Entity focusedEntity, boolean thirdPerson, boolean inverseView, float tickDelta) {

        if (camera instanceof CameraMixinAccessor)
            ((CameraMixinAccessor) camera).update(area, focusedEntity, this.client.options.getPerspective(), tickDelta);
        else camera.update(area, focusedEntity, thirdPerson, inverseView, tickDelta);
    }
}
