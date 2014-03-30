package io.sporkpgm.region.types;

import io.sporkpgm.util.Log;
import io.sporkpgm.util.RegionUtil;
import org.bukkit.util.Vector;

import java.util.List;

public class RectangleRegion extends CuboidRegion {

	public RectangleRegion(String name, BlockRegion min, BlockRegion max) {
		super(name, new BlockRegion(min.getStringX(), "-oo", min.getStringZ()), new BlockRegion(max.getStringX(), "oo", max.getStringZ()));
	}

	@Override
	public boolean isInside(BlockRegion block) {
		return isInside(block, false);
	}

	@Override
	public boolean isInside(BlockRegion block, boolean log) {
		Vector point = new Vector(block.getX(), block.getY(), block.getZ());
		double xMin, xMax, zMin, zMax;
		xMin = getPoints()[1].getX();
		xMax = getPoints()[0].getX();
		zMin = getPoints()[1].getZ();
		zMax = getPoints()[0].getZ();

		boolean x = (xMin <= point.getX()) && (point.getX() <= xMax);
		String xI = "(" + xMin + " <= " + point.getX() + " && " + point.getX() + " <= " + xMax + ")";

		boolean z = (zMin <= point.getZ()) && (point.getZ() <= zMax);
		String zI = "(" + zMin + " <= " + point.getZ() + " && " + point.getZ() + " <= " + zMax + ")";

		if(log) {
			Log.info("Checking " + this + " to match " + point);
			Log.info("X: " + xI + " = " + x);
			Log.info("Z: " + zI + " = " + z);
		}
		return x && z;
	}

	public List<BlockRegion> getValues() {
		return RegionUtil.cuboid(new BlockRegion(one.x, "0", one.z), new BlockRegion(two.x, "256", two.z));
	}

}
