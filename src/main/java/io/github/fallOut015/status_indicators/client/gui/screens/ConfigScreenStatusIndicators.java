package io.github.fallOut015.status_indicators.client.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.fallOut015.status_indicators.client.ConfigStatusIndicators;
import net.minecraft.client.CycleOption;
import net.minecraft.client.Option;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

// Based on https://leo3418.github.io/2021/03/31/forge-mod-config-screen-1-16.html

public class ConfigScreenStatusIndicators extends Screen {
    public static final CycleOption<Boolean> SEE_WHEN_OTHERS_ARE_PAUSED = CycleOption.createOnOff("status_indicators.config.see_when_others_are_paused", (p_168382_) -> {
        return p_168382_.autoJump;
    }, (p_168384_, p_168385_, p_168386_) -> {
        p_168384_.autoJump = p_168386_;
    });

    private static final Component GUI_DONE = new TranslatableComponent("gui.done");

    private final Screen parent;
    private static final Option[] OPTIONS = new Option[] {};

    public ConfigScreenStatusIndicators(Screen parent) {
        super(new TranslatableComponent("status_indicators.config.title"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        if(this.minecraft != null) {
            this.optionsRowList = new OptionsRowList(this.minecraft, this.width, this.height, 24, this.height - 32, 25);

            this.optionsRowList.addSmall(
                    new BooleanOption(
                            "status_indicators.config.see_when_others_are_paused",
                            new TranslatableComponent("status_indicators.config.see_when_others_are_paused.desc"),
                            u -> ConfigStatusIndicators.CLIENT.SEE_WHEN_OTHERS_ARE_PAUSED.get(),
                            (u, newValue) -> ConfigStatusIndicators.CLIENT.SEE_WHEN_OTHERS_ARE_PAUSED.set(newValue)),
                    new BooleanOption(
                            "status_indicators.config.show_others_when_paused",
                            new TranslatableComponent("status_indicators.config.show_others_when_paused.desc"),
                            u -> ConfigStatusIndicators.CLIENT.SHOW_OTHERS_WHEN_PAUSED.get(),
                            (u, newValue) -> ConfigStatusIndicators.CLIENT.SHOW_OTHERS_WHEN_PAUSED.set(newValue)
                    )
            );

            this.children.add(this.optionsRowList);
        }

        this.addButton(new Button((this.width - 200) / 2, this.height - 26, 200, 20, GUI_DONE, button -> this.onClose()));
    }
    @Override
    public void onClose() {
        ConfigStatusIndicators.bakeConfig();
        if(this.minecraft != null) {
            this.minecraft.setScreen(this.parent);
        }
    }
    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        this.optionsRowList.render(matrixStack, mouseX, mouseY, partialTicks);
        drawCenteredString(matrixStack, this.font, this.title.getString(), this.width / 2, 8, 0xFFFFFF);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }
}
