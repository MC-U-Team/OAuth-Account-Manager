package info.u_team.oauth_account_manager.screen.list;

import java.util.Optional;
import java.util.UUID;

import info.u_team.oauth_account_manager.screen.AccountCheckValidScreen;
import info.u_team.oauth_account_manager.screen.AccountLoginScreen;
import info.u_team.oauth_account_manager.screen.AccountUseScreen;
import info.u_team.oauth_account_manager.util.AuthenticationUtil;
import info.u_team.oauth_account_manager.util.LoadedAccount;
import info.u_team.oauth_account_manager.util.MinecraftAccounts;
import info.u_team.u_team_core.gui.elements.ScrollableList;
import net.minecraft.client.gui.screens.Screen;

public class AccountSelectionList extends ScrollableList<AccountSelectionEntry> {
	
	private final Screen ourScreen;
	
	public AccountSelectionList(Screen ourScreen) {
		super(0, 0, 0, 0, 36, 0);
		this.ourScreen = ourScreen;
		setRenderTopAndBottom(false);
		setRenderTransparentBorder(true);
	}
	
	@Override
	public int getRowWidth() {
		return 305;
	}
	
	@Override
	protected int getScrollbarPosition() {
		return width / 2 + 154;
	}
	
	public void loadEntries() {
		final UUID selectedUUID = getSelected() != null ? getSelected().getUuid() : null;
		clearEntries();
		MinecraftAccounts.getAccountUUIDs().stream().sorted().map(uuid -> new AccountSelectionEntry(this, uuid)).forEach(entry -> {
			addEntry(entry);
			if (entry.getUuid().equals(selectedUUID)) {
				setSelected(entry);
			}
		});
		setScrollAmount(getScrollAmount());
	}
	
	public void useSelectedEntry() {
		final AccountSelectionEntry entry = getSelected();
		if (entry == null) {
			return;
		}
		
		final UUID uuid = entry.getUuid();
		
		if (!MinecraftAccounts.isLoaded(uuid)) {
			showLoginScreen(uuid);
		} else {
			showUseScreen(uuid, () -> showLoginScreen(uuid));
		}
	}
	
	public void deleteSelectedEntry() {
		final AccountSelectionEntry entry = getSelected();
		if (entry != null) {
			removeEntry(entry);
		}
		MinecraftAccounts.removeAccount(entry.getUuid());
	}
	
	private void showLoginScreen(UUID uuid) {
		final AccountLoginScreen loginScreen = new AccountLoginScreen(ourScreen);
		loginScreen.login(Optional.of(uuid), () -> AuthenticationUtil.createWebAuthenticationMethod().existingAuthentication(MinecraftAccounts.getAccount(uuid)), screen -> {
			showUseScreen(uuid, () -> showLoginScreen(uuid));
		});
		minecraft.setScreen(loginScreen);
	}
	
	private void showUseScreen(UUID uuid, Runnable retry) {
		final LoadedAccount loadedAccount = MinecraftAccounts.getLoadedAccount(uuid);
		if (loadedAccount == null) {
			retry.run();
			return;
		}
		final AccountCheckValidScreen validScreen = new AccountCheckValidScreen(ourScreen, retry, () -> {
			final AccountUseScreen useScreen = new AccountUseScreen(ourScreen, MinecraftAccounts.getGameProfile(uuid), loadedAccount);
			minecraft.setScreen(useScreen);
		});
		validScreen.checkAccount(loadedAccount.user().accessToken());
		minecraft.setScreen(validScreen);
	}
	
}
