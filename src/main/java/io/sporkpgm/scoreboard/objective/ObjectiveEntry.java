package io.sporkpgm.scoreboard.objective;

import io.sporkpgm.module.ObjectiveModule;
import io.sporkpgm.scoreboard.ScoreboardEntry;
import io.sporkpgm.scoreboard.SporkScoreboard;
import io.sporkpgm.scoreboard.UpdateableEntry;

import static io.sporkpgm.util.Chars.CROSS;
import static io.sporkpgm.util.Chars.TICK;
import static org.bukkit.ChatColor.GREEN;
import static org.bukkit.ChatColor.RED;
import static org.bukkit.ChatColor.WHITE;

public class ObjectiveEntry extends UpdateableEntry {

	protected boolean complete;
	protected ObjectiveModule objective;

	public ObjectiveEntry(ObjectiveModule objective, SporkScoreboard scoreboard) {
		super(objective.getName(), scoreboard);
		this.complete = objective.isCompleted();
		this.objective = objective;
		update(true);
	}

	public ObjectiveModule getObjective() {
		return objective;
	}

	public void update(boolean force) {
		if(complete == objective.isCompleted() && !force) {
			return;
		}

		String name = " " + (objective.isCompleted() ? GREEN + "" + TICK : RED + "" + CROSS) + " " + WHITE + objective.getName();
		ScoreboardEntry entry = scoreboard.getEntry(name, true);
		name = entry.getName();
		scoreboard.getEntries().remove(entry);

		update(name);
		if(!scoreboard.getEntries().contains(this)) {
			scoreboard.getEntries().add(this);
		}
	}

}
