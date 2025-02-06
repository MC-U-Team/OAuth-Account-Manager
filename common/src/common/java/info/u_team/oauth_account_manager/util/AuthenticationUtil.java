package info.u_team.oauth_account_manager.util;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.slf4j.LoggerFactory;

import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.minecraft.UserApiService;
import com.mojang.authlib.minecraft.UserApiService.UserProperties;
import com.mojang.authlib.yggdrasil.ProfileResult;
import com.mojang.realmsclient.RealmsAvailability;
import com.mojang.realmsclient.client.RealmsClient;
import com.mojang.realmsclient.gui.RealmsDataFetcher;
import com.mojang.util.UndashedUuid;

import info.u_team.oauth_account_manager.OAuthAccountManagerReference;
import net.hycrafthd.simple_minecraft_authenticator.SimpleMinecraftAuthentication;
import net.hycrafthd.simple_minecraft_authenticator.method.AuthenticationMethod;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.User;
import net.minecraft.client.gui.screens.social.PlayerSocialManager;
import net.minecraft.client.multiplayer.ProfileKeyPairManager;
import net.minecraft.client.multiplayer.chat.report.ReportEnvironment;
import net.minecraft.client.multiplayer.chat.report.ReportingContext;
import net.minecraft.client.telemetry.ClientTelemetryManager;
import net.minecraft.server.LoggedPrintStream;

public class AuthenticationUtil {
	
	private static final HttpClient CLIENT = HttpClient.newHttpClient();
	
	public static CompletableFuture<Boolean> isAccessTokenValid(String accessToken) {
		final URI uri;
		try {
			uri = new URI(OAuthAccountManagerReference.ACCESS_TOKEN_VALID_ENDPOINT);
		} catch (URISyntaxException ex) {
			throw new AssertionError(ex);
		}
		
		final HttpRequest request = HttpRequest.newBuilder(uri) //
				.header("Authorization", "Bearer " + accessToken) //
				.timeout(Duration.ofSeconds(15)) //
				.GET() //
				.build();
		
		return CLIENT.sendAsync(request, BodyHandlers.discarding()).thenApply(response -> response.statusCode() == 200);
	}
	
	public static AuthenticationMethod createWebAuthenticationMethod() {
		return SimpleMinecraftAuthentication.getMethod("web").get().create(new LoggedPrintStream("OAuth-Account-Manager", System.out), System.in);
	}
	
	public static MinecraftAccountData createMinecraftAccountData(LoadedAccount loadedAccount) throws AuthenticationException {
		final Minecraft minecraft = Minecraft.getInstance();
		
		final var msUser = loadedAccount.user();
		
		final User user = new User(msUser.name(), UndashedUuid.fromStringLenient(msUser.uuid()), msUser.accessToken(), Optional.of(msUser.xuid()), Optional.of(msUser.clientId()), User.Type.byName(msUser.type()));
		final CompletableFuture<ProfileResult> profileFuture = CompletableFuture.supplyAsync(() -> minecraft.getMinecraftSessionService().fetchProfile(user.getProfileId(), true), Util.nonCriticalIoPool());
		final UserApiService userApiService = minecraft.authenticationService.createUserApiService(msUser.accessToken());
		final CompletableFuture<UserProperties> userPropertiesFuture = CompletableFuture.supplyAsync(() -> {
			try {
				return userApiService.fetchProperties();
			} catch (final AuthenticationException ex) {
				LoggerFactory.getLogger(Minecraft.class).error("Failed to fetch user properties", ex);
				return UserApiService.OFFLINE_PROPERTIES;
			}
		}, Util.nonCriticalIoPool());
		final PlayerSocialManager playerSocialManager = new PlayerSocialManager(minecraft, userApiService);
		final ClientTelemetryManager clientTelemetryManager = new ClientTelemetryManager(minecraft, userApiService, user);
		final ProfileKeyPairManager profileKeyPairManager = ProfileKeyPairManager.create(userApiService, user, minecraft.gameDirectory.toPath());
		final ReportingContext reportingContext = ReportingContext.create(ReportEnvironment.local(), userApiService);
		
		return new MinecraftAccountData(user, profileFuture, userApiService, userPropertiesFuture, playerSocialManager, clientTelemetryManager, profileKeyPairManager, reportingContext);
	}
	
	public static void setMinecraftAccountData(MinecraftAccountData data) {
		final Minecraft minecraft = Minecraft.getInstance();
		
		minecraft.user = data.user;
		minecraft.profileFuture = data.profileFuture;
		minecraft.userApiService = data.userApiService;
		minecraft.userPropertiesFuture = data.userPropertiesFuture;
		minecraft.getSplashManager().user = data.user;
		minecraft.playerSocialManager = data.playerSocialManager;
		minecraft.telemetryManager = data.telemetryManager;
		minecraft.profileKeyPairManager = data.profileKeyPairManager;
		minecraft.reportingContext = data.reportingContext;
		
		final RealmsClient realmsClient = RealmsClient.create(minecraft);
		minecraft.realmsDataFetcher = new RealmsDataFetcher(realmsClient);
		RealmsAvailability.future = null; // Force refresh realms
	}
	
	public static record MinecraftAccountData(User user, CompletableFuture<ProfileResult> profileFuture, UserApiService userApiService, CompletableFuture<UserProperties> userPropertiesFuture, PlayerSocialManager playerSocialManager, ClientTelemetryManager telemetryManager, ProfileKeyPairManager profileKeyPairManager, ReportingContext reportingContext) {
	}
}
