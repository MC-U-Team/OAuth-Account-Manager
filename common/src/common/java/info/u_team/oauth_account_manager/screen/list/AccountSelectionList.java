package info.u_team.oauth_account_manager.screen.list;

import java.util.UUID;

import info.u_team.oauth_account_manager.util.MinecraftAccounts;
import info.u_team.u_team_core.gui.elements.ScrollableList;
import net.minecraft.client.gui.screens.Screen;

public class AccountSelectionList extends ScrollableList<AbstractAccountSelectionEntry> {
	
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
		MinecraftAccounts.getAccountUUIDs().stream().sorted().map(uuid -> new AccountSelectionEntry(ourScreen, this, uuid, MinecraftAccounts.getGameProfile(uuid))).forEach(entry -> {
			addEntry(entry);
			if (entry.getUuid().equals(selectedUUID)) {
				setSelected(entry);
			}
		});
		addEntryToTop(new LaunchedAccountSelectionEntry(ourScreen, this));
		setScrollAmount(getScrollAmount());
	}
	
	public void useSelectedEntry() {
		final AbstractAccountSelectionEntry entry = getSelected();
		if (entry != null) {
			entry.useEntry();
		}
	}
	
	public void deleteSelectedEntry() {
		if (getSelected() instanceof AccountSelectionEntry entry) {
			removeEntry(entry);
			MinecraftAccounts.removeAccount(entry.getUuid());
		}
	}
}
