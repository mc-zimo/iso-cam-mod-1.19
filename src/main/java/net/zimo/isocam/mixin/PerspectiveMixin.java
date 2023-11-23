package net.zimo.isocam.mixin;

import net.minecraft.client.option.Perspective;
import net.zimo.isocam.Accessor.Option;
import net.zimo.isocam.Accessor.PerspectiveMixinAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Perspective.class)
public abstract class PerspectiveMixin implements PerspectiveMixinAccessor {

    @Shadow
    static Perspective[] VALUES;
    public Option option = Option.DEFAULT;

    @Inject(at = @At("HEAD"), method = "next", cancellable = true)
    public void next(CallbackInfoReturnable<Perspective> cir) {
        this.option = option.next();
        cir.setReturnValue(VALUES[(((Perspective)((Object)this)).ordinal() + 1) % VALUES.length]);
        cir.cancel();
    }

    @Override
    public Option getOption() {
        return option;
    }
}


