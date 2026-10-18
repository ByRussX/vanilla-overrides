package byrussx.vanillaoverrides.config;

import java.util.List;
import java.util.Objects;

public class ModRule<T>
{
    private final String name;
    private final String description;
    private final T defaultValue;
    private T value;

    public ModRule(String name, String description, T defaultValue, List<T> options)
    {
        this.name = name;
        this.description = description;
        this.defaultValue = defaultValue;
        this.value = defaultValue;
    }

    public T get()
    {
        return value;
    }

    public void set(T newValue)
    {
        this.value = newValue;
    }

    public T getDefault()
    {
        return defaultValue;
    }

    public boolean isDefault()
    {
        return Objects.equals(value, defaultValue);
    }

    public String getName()
    {
        return name;
    }

    public String getDescription()
    {
        return description;
    }

}
