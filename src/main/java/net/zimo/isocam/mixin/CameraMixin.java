package net.zimo.isocam.mixin;

import net.minecraft.client.option.Perspective;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.BlockView;
import net.zimo.isocam.Accessor.CameraMixinAccessor;
import net.zimo.isocam.Accessor.PerspectiveMixinAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Camera.class)
public abstract class CameraMixin implements CameraMixinAccessor {

    @Shadow
    private boolean ready;
    @Shadow
    private BlockView area;
    @Shadow
    private Entity focusedEntity;
    @Shadow
    private boolean thirdPerson;
    @Shadow
    private float lastCameraY;
    @Shadow
    private float yaw;
    @Shadow
    private float cameraY;
    @Shadow
    private float pitch;

    @Shadow
    protected abstract void setRotation(float yaw, float pitch);

    @Shadow
    protected abstract void setPos(double lerp, double v, double lerp1);

    @Shadow
    protected abstract double clipToSpace(double v);

    @Shadow
    protected abstract void moveBy(double v, double v1, double v2);

    private boolean isometric = false;

    public void update(BlockView area, Entity focusedEntity, Perspective perspective, float tickDelta) {
        this.ready = true;
        this.area = area;
        this.focusedEntity = focusedEntity;
        this.thirdPerson = !perspective.isFirstPerson();
        this.setRotation(focusedEntity.getYaw(tickDelta), focusedEntity.getPitch(tickDelta));
        this.setPos(MathHelper.lerp(tickDelta, focusedEntity.prevX, focusedEntity.getX()), MathHelper.lerp(tickDelta, focusedEntity.prevY, focusedEntity.getY()) + (double) MathHelper.lerp(tickDelta, this.lastCameraY, this.cameraY), MathHelper.lerp(tickDelta, focusedEntity.prevZ, focusedEntity.getZ()));
        if ((Object) perspective instanceof PerspectiveMixinAccessor) {
            isometric = !((PerspectiveMixinAccessor) (Object) perspective).getOption().isDefault();
        }
        if (thirdPerson) {
            if (isometric) {
                double dx = 1, dy = 10, dz = 1;
                float angle = 1;
                Direction[] d = Direction.getEntityFacingOrder(focusedEntity);

                if (!perspective.isFrontView()) {
                    d = new Direction[]{d[0], d[1], d[2]};
                }else{
                    d = new Direction[]{d[3], d[4], d[5]};
                }

                for (Direction d2 : d) {
                    switch (d2) {
                        case NORTH:
                            angle *= 135;
                            dz *= 10;
                            break;
                        case SOUTH:
                            angle *= 45;
                            dz *= -10;
                            break;
                        case EAST:
                            angle *= -1;
                            dx *= -10;
                            break;
                        case WEST:
                            angle *= 1;
                            dx *= 10;
                            break;
                        case UP:
                            break;
                        case DOWN:
                            break;
                    }
                }
                this.setRotation(angle, 45.0f);
                this.setPos(MathHelper.lerp(tickDelta, focusedEntity.prevX, focusedEntity.getX()) + dx,
                        MathHelper.lerp(tickDelta, focusedEntity.prevY, focusedEntity.getY()) + dy,
                        MathHelper.lerp(tickDelta, focusedEntity.prevZ, focusedEntity.getZ()) + dz);

            } else {
                if (perspective.isFrontView()) {
                    this.setRotation(this.yaw + 180.0f, -this.pitch);
                }
                this.moveBy(-this.clipToSpace(4.0), 0.0, 0.0);
            }
        } else if (focusedEntity instanceof LivingEntity && ((LivingEntity) focusedEntity).isSleeping()) {
            Direction direction = ((LivingEntity) focusedEntity).getSleepingDirection();
            this.setRotation(direction != null ? direction.asRotation() - 180.0f : 0.0f, 0.0f);
            this.moveBy(0.0, 0.3, 0.0);
        }

    }

}
