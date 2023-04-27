package info.u_team.oauth_account_manager.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.hycrafthd.minecraft_authenticator.login.AuthenticationFile;
import net.minecraft.client.Minecraft;

public class MinecraftAccounts {
	
	private static final Map<UUID, AuthenticationFile> ACCOUNTS = new HashMap<>();
	
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	
	private static final Path ACCOUNT_DIRECTORY = Minecraft.getInstance().gameDirectory.toPath().resolve(".oauth-account-manager");
	private static final Path ACCOUNT_FILE = ACCOUNT_DIRECTORY.resolve("accounts.mcoauth");
	
	public static void load() throws IOException {
		ensureDirectoryExists();
		
		final JsonObject json;
		
		try (final BufferedReader reader = Files.newBufferedReader(ACCOUNT_FILE, StandardCharsets.UTF_8)) {
			json = GSON.fromJson(reader, JsonObject.class);
		}
		
		for (final Entry<String, JsonElement> entry : json.entrySet()) {
			ACCOUNTS.put(UUID.fromString(entry.getKey()), AuthenticationFile.readCompressed(Base64.getDecoder().decode(entry.getValue().getAsString())));
		}
	}
	
	public static void save() throws IOException {
		ensureDirectoryExists();
		
		final JsonObject json = new JsonObject();
		
		for (final Entry<UUID, AuthenticationFile> entry : ACCOUNTS.entrySet()) {
			json.addProperty(entry.getKey().toString(), Base64.getEncoder().encodeToString(entry.getValue().writeCompressed()));
		}
		
		try (final Writer writer = Files.newBufferedWriter(ACCOUNT_FILE, StandardCharsets.UTF_8)) {
			GSON.toJson(json, writer);
		}
	}
	
	private static void ensureDirectoryExists() throws IOException {
		Files.createDirectories(ACCOUNT_DIRECTORY);
	}
	
	public static Map<UUID, AuthenticationFile> getAccounts() {
		return ACCOUNTS;
	}
	
}
