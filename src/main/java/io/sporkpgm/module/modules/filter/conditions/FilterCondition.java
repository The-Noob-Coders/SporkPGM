package io.sporkpgm.module.modules.filter.conditions;

import io.sporkpgm.module.modules.filter.Filter;
import io.sporkpgm.module.modules.filter.other.Context;
import io.sporkpgm.module.modules.filter.other.State;
import io.sporkpgm.map.SporkMap;
import io.sporkpgm.rotation.Rotation;

import static io.sporkpgm.module.modules.filter.other.State.ABSTAIN;

public class FilterCondition extends Filter {

	String title;
	Filter filter;

	public FilterCondition(String title, State state) {
		super("search-" + title, state);

		this.title = title;
	}

	public State filter(Context context) {
		boolean fetch = fetch();
		if(!fetch) {
			return ABSTAIN;
		}

		return filter.result(context);
	}

	public boolean fetch() {
		if(filter != null) {
			return true;
		}

		SporkMap map = Rotation.getMap();
		this.filter = map.getFilters().getFilter(title);

		if(filter != null) {
			return true;
		}

		return false;
	}

}
