package io.sporkpgm.map;

import io.sporkpgm.Spork;
import io.sporkpgm.module.ModuleContext;
import io.sporkpgm.module.builder.BuilderContext;
import io.sporkpgm.module.modules.info.Contributor;
import io.sporkpgm.module.modules.info.InfoModule;
import io.sporkpgm.util.FileUtil;
import io.sporkpgm.util.Log;
import io.sporkpgm.util.StringUtil;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class SporkLoader {

	private File folder;
	private Document document;

	private InfoModule info;
	private ModuleContext modules;

	public SporkLoader(File folder) {
		this.folder = folder;

		File file = new File(folder, "map.xml");
		SAXBuilder sax = new SAXBuilder();
		try {
			this.document = sax.build(file);
		} catch(JDOMException | IOException e) {
			throw new IllegalStateException("Unable to parse Document for '" + folder.getName() + "'", e);
		}

		this.modules = new ModuleContext(this);

		BuilderContext context = new BuilderContext(document);
		this.modules.add(InfoModule.class, context);

		this.info = this.modules.getModule(InfoModule.class);
	}

	public File getFolder() {
		return folder;
	}

	public Document getDocument() {
		return document;
	}

	public ModuleContext getModules() {
		return modules;
	}

	public String getName() {
		return info.getName();
	}

	public int getMaxPlayers() {
		return info.getMaxPlayers();
	}

	public String getObjective() {
		return info.getObjective();
	}

	public String getShortDescription() {
		return info.getShortDescription();
	}

	public List<Contributor> getContributors() {
		return info.getContributors();
	}

	public List<Contributor> getAuthors() {
		return info.getAuthors();
	}

	public List<String> getRules() {
		return info.getRules();
	}

	public String getVersion() {
		return info.getVersion();
	}

	public boolean copy(File destination) {
		FileUtil.delete(destination);

		String[] copy = new String[]{"level.dat|true", "region|true", "data|false"};
		for(String string : copy) {
			String[] split = string.split("|");
			String name = split[0];
			boolean required = StringUtil.convertStringToBoolean(split[1], true);

			File file = new File(folder, name);
			if(required && !file.exists()) {
				Log.debug(name + " did not exist inside " + folder.getName());
				FileUtil.delete(destination);
				return false;
			}

			File dest = new File(destination, name);
			try {
				FileUtil.copy(file, dest);
			} catch(Exception e) {
				if(Spork.isDebug()) {
					e.printStackTrace();
				}

				FileUtil.delete(destination);
				return false;
			}
		}

		return true;
	}

}
