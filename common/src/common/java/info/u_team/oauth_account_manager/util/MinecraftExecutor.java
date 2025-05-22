package info.u_team.oauth_account_manager.util;

import net.minecraft.client.Minecraft;

public class MinecraftExecutor {
	
	public static void executeOnMainThread(Runnable runnable) {
		Minecraft.getInstance().execute(runnable);
	}
	
}
