package io.sporkpgm.event.user;

import io.sporkpgm.user.User;
import org.bukkit.event.HandlerList;

public class UserAddEvent extends UserEvent {

	private static final HandlerList handlers = new HandlerList();

	public UserAddEvent(User user) {
		super(user);
	}

	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

}
