package de.miraisoft.ash2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;

import de.miraisoft.ash2.config.AshConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;

public class AshCommands {
	
	private static final String COMMAND_TOGGLE = "ash2toggle";
	private static final String COMMAND_RESET = "ash2reset";
	private static final String COMMAND_FPS = "ash2togglefps";
	private static final String COMMAND_COORDS = "ash2togglecoords";
	private static final String COMMAND_DIRECTION = "ash2toggledirection";
	private static final String COMMAND_COLOR = "ash2color";
	private static final String COMMAND_LIGHTLEVEL = "ash2togglelightlevel";
	private static final String COMMAND_CONCISE_COORDS = "ash2toggleconcisecoords";
	
	private final static Logger logger = LogManager.getLogger(); 

    public static AshConfig config;

	public static void registerCommands() {

		CommandDispatcher<FabricClientCommandSource> commandDispatcher = ClientCommandManager.DISPATCHER;

		if (config == null) {
			config = AutoConfig.getConfigHolder(AshConfig.class).getConfig();
		}

		if (commandDispatcher == null) {
			logger.info("Command Dispatcher is null");
			return;
		}

		commandDispatcher.register(ClientCommandManager.literal(COMMAND_TOGGLE).executes(context -> {
			config.showHud = !config.showHud;
			AshMod.configManager.save();
			return 1;
		}));

		commandDispatcher.register(ClientCommandManager.literal(COMMAND_RESET).executes(context -> {
			config = new AshConfig();
			AshMod.configManager.save();
			return 1;
		}));

        
        commandDispatcher.register(ClientCommandManager.literal(COMMAND_FPS).executes(context -> {
            config.showFps = !config.showFps;
            AshMod.configManager.save();
            return 1;
        }));

        commandDispatcher.register(ClientCommandManager.literal(COMMAND_COORDS).executes(context -> {
            config.showCoords = !config.showCoords;
            AshMod.configManager.save();
            return 1;
        }));

        commandDispatcher.register(ClientCommandManager.literal(COMMAND_DIRECTION).executes(context -> {
            config.showDirection = !config.showDirection;
            AshMod.configManager.save();
            return 1;
        }));

        commandDispatcher.register(ClientCommandManager.literal(COMMAND_COLOR)
            .then(ClientCommandManager.argument("r", IntegerArgumentType.integer())
	            .then(ClientCommandManager.argument("g", IntegerArgumentType.integer())
                    .then(ClientCommandManager.argument("b", IntegerArgumentType.integer())
                        .executes(context -> {
                            int r = IntegerArgumentType.getInteger(context,"r");
                            int g = IntegerArgumentType.getInteger(context,"g");
                            int b = IntegerArgumentType.getInteger(context,"b");

                            config.hudColor = b + (g << 8) + (r << 16);
                            AshMod.configManager.save();
                            return 1;
	    })))));
        
        commandDispatcher.register(ClientCommandManager.literal(COMMAND_LIGHTLEVEL).executes(context -> {
            config.showLightLevel = !config.showLightLevel;
            AshMod.configManager.save();
            return 1;
        }));
        
        commandDispatcher.register(ClientCommandManager.literal(COMMAND_CONCISE_COORDS).executes(context -> {
            config.conciseCoords = !config.conciseCoords;
            AshMod.configManager.save();
            return 1;
        }));
        
    }
}