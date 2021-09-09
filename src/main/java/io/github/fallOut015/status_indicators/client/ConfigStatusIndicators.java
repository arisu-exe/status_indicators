package io.github.fallOut015.status_indicators.client;

import io.github.fallOut015.status_indicators.MainStatusIndicator;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import org.apache.commons.lang3.tuple.Pair;

public class ConfigStatusIndicators {
    public static final ClientConfig CLIENT;
    public static final ForgeConfigSpec CLIENT_SPEC;

    static {
        final Pair<ClientConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ClientConfig::new);
        CLIENT_SPEC = specPair.getRight();
        CLIENT = specPair.getLeft();
    }

    private static boolean see_when_others_are_paused = true;
    private static boolean show_others_when_paused = true;

    private static boolean see_when_others_are_chatting = true;
    private static boolean show_others_when_chatting = true;

    private static boolean see_when_others_are_commanding = true;
    private static boolean show_others_when_commanding = true;

    private static boolean tab_menu_status = true;
    private static boolean chat_status = true;
    private static boolean playerhead_status = true;

    public static void bakeConfig() {
        see_when_others_are_paused = CLIENT.SEE_WHEN_OTHERS_ARE_PAUSED.get();
        show_others_when_paused = CLIENT.SHOW_OTHERS_WHEN_PAUSED.get();

        see_when_others_are_chatting = CLIENT.SEE_WHEN_OTHERS_ARE_CHATTING.get();
        show_others_when_chatting = CLIENT.SHOW_OTHERS_WHEN_CHATTING.get();

        see_when_others_are_commanding = CLIENT.SEE_WHEN_OTHERS_ARE_COMMANDING.get();
        show_others_when_commanding = CLIENT.SHOW_OTHERS_WHEN_COMMANDING.get();

        tab_menu_status = CLIENT.TAB_MENU_STATUS.get();
        chat_status = CLIENT.CHAT_STATUS.get();
        playerhead_status = CLIENT.PLAYERHEAD_STATUS.get();
    }

    public static class ClientConfig {
        public final ForgeConfigSpec.BooleanValue SEE_WHEN_OTHERS_ARE_PAUSED;
        public final ForgeConfigSpec.BooleanValue SHOW_OTHERS_WHEN_PAUSED;

        public final ForgeConfigSpec.BooleanValue SEE_WHEN_OTHERS_ARE_CHATTING;
        public final ForgeConfigSpec.BooleanValue SHOW_OTHERS_WHEN_CHATTING;

        public final ForgeConfigSpec.BooleanValue SEE_WHEN_OTHERS_ARE_COMMANDING;
        public final ForgeConfigSpec.BooleanValue SHOW_OTHERS_WHEN_COMMANDING;

        public final ForgeConfigSpec.BooleanValue TAB_MENU_STATUS;
        public final ForgeConfigSpec.BooleanValue CHAT_STATUS;
        public final ForgeConfigSpec.BooleanValue PLAYERHEAD_STATUS;

        public ClientConfig(ForgeConfigSpec.Builder builder) {
            this.SEE_WHEN_OTHERS_ARE_PAUSED = builder.translation("status_indicators.config.see_when_others_are_paused").define("see_when_others_are_paused", true);
            this.SHOW_OTHERS_WHEN_PAUSED = builder.translation("status_indicators.config.show_others_when_paused").define("show_others_when_paused", true);

            this.SEE_WHEN_OTHERS_ARE_CHATTING = builder.translation("status_indicators.config.see_when_others_are_chatting").define("see_when_others_are_chatting", true);
            this.SHOW_OTHERS_WHEN_CHATTING = builder.translation("status_indicators.config.show_others_when_chatting").define("show_others_when_chatting", true);

            this.SEE_WHEN_OTHERS_ARE_COMMANDING = builder.translation("status_indicators.config.see_when_others_are_commanding").define("see_when_others_are_commanding", true);
            this.SHOW_OTHERS_WHEN_COMMANDING = builder.translation("status_indicators.config.show_others_when_commanding").define("show_others_when_commanding", true);

            this.TAB_MENU_STATUS = builder.translation("status_indicators.config.tab_menu_status").define("tab_menu_status", true);
            this.CHAT_STATUS = builder.translation("status_indicators.config.chat_status").define("chat_status", true);
            this.PLAYERHEAD_STATUS = builder.translation("status_indicators.config.playerhead_status").define("playerhead_status", true);
        }
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = MainStatusIndicator.MODID)
    public static class ModEvents {
        @SubscribeEvent
        public static void onModConfigEvent(final ModConfigEvent event) {
            if(event.getConfig().getSpec() == ConfigStatusIndicators.CLIENT_SPEC) {
                ConfigStatusIndicators.bakeConfig();
            }
        }
    }
}
