package futurepack.api.interfaces;

import java.util.Collections;
import java.util.List;

public interface IOptimizeable extends Comparable<IOptimizeable>
{
	int getPoints();
	
	void addPoint();
	
	void resetPoints();
	
	@Override
	default int compareTo(IOptimizeable o)
	{
		return o.getPoints()- getPoints();
	}
	
	public static void sortList(List<? extends IOptimizeable> col)
	{
		Collections.sort(col);
	}
}
