package futurepack.api.interfaces;

public interface ITilePropertyStorage
{
	int getProperty(int id);

    void setProperty(int id, int value);

    int getPropertyCount();
}
