package dev.dewy.nbt.api.registry;

import dev.dewy.nbt.api.Tag;
import dev.dewy.nbt.tags.TagType;
import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * A registry mapping {@code byte} tag type IDs to tag type classes. Used to register custom-made {@link Tag} types.
 *
 * @author dewy
 */
public class TagTypeRegistry {
    private final Map<Byte, Supplier<Tag>> idRegistry = new HashMap<>();
    private final Map<String, Byte> nameToId = new HashMap<>();
    private final Map<Byte, String> idToName = new HashMap<>();

    {
        TagType.registerAll(this);
    }

    /**
     * Register a custom-made tag type with a unique {@code byte} ID. IDs 0-12 (inclusive) are reserved and may not be used.
     *
     * @param id      the tag type's unique ID used in reading and writing.
     * @param name    the tag type's unique name used in reading and writing.
     * @param factory the tag factory.
     * @throws TagTypeRegistryException if the ID provided is either registered already or is a reserved ID (0-12 inclusive).
     */
    public void registerTagType(byte id, @NonNull String name, @NonNull Supplier<Tag> factory) throws TagTypeRegistryException {
        if (id == 0) {
            throw new TagTypeRegistryException("Cannot register NBT tag type " + name + " with ID " + id + ", as that ID is reserved.");
        }

        if (this.idRegistry.containsKey(id)) {
            throw new TagTypeRegistryException("Cannot register NBT tag type " + name + " with ID " + id + ", as that ID is already in use by the tag type " + this.idToName.get(id));
        }

        if (idRegistry.containsValue(factory)) {
            byte existing = 0;
            for (Map.Entry<Byte, Supplier<Tag>> entry : this.idRegistry.entrySet()) {
                if (entry.getValue().equals(factory)) {
                    existing = entry.getKey();
                }
            }

            throw new TagTypeRegistryException("NBT tag type " + name + " already registered under ID " + existing);
        }

        this.idRegistry.put(id, factory);
        this.nameToId.put(name, id);
        this.idToName.put(id, name);
    }

    /**
     * Deregister a custom-made tag type with a provided tag type ID.
     *
     * @param id the ID of the tag type to deregister.
     * @return if the tag type was deregistered successfully.
     */
    public boolean unregisterTagType(byte id) {
        if (id >= 0 && id <= 12) {
            return false;
        }

        String name = this.idToName.get(id);
        if (name == null) return false;

        return this.idRegistry.remove(id) != null && this.nameToId.remove(name) != null && this.idToName.remove(id) != null;
    }

    /**
     * Deregister a custom-made tag type with a provided tag name.
     *
     * @param name the name of the tag type to deregister.
     * @return if the tag type was deregistered successfully.
     */
    public boolean unregisterTagType(@NonNull String name) {
        byte id = this.nameToId.get(name);

        return this.unregisterTagType(id);
    }

    /**
     * Deregister a custom-made tag type with a provided tag type ID and class value.
     *
     * @param id      the ID of the tag type to deregister.
     * @param factory the factory of the tag type to deregister.
     * @return if the tag type was deregistered successfully.
     */
    public boolean unregisterTagType(byte id, Supplier<Tag> factory) {
        String name = this.idToName.get(id);
        if (name == null) return false;

        return this.idRegistry.remove(id, factory) && this.nameToId.remove(name, id) && this.idToName.remove(id, name);
    }

    /**
     * Deregister a custom-made tag type with a provided tag type ID and class value.
     *
     * @param name    the name of the tag type to deregister.
     * @param factory the factory of the tag type to deregister.
     * @return if the tag type was deregistered successfully.
     */
    public boolean unregisterTagType(@NonNull String name, Supplier<Tag> factory) {
        Byte id = this.nameToId.get(name);
        if (id == null) return false;

        return this.unregisterTagType(id, factory);
    }

    /**
     * Returns a tag type factory from the registry from a provided {@code byte} ID.
     *
     * @param id the ID of the tag type to retrieve.
     * @return a tag type factory from the registry from a provided {@code byte} ID.
     */
    public Supplier<Tag> getFactoryFromId(byte id) {
        return this.idRegistry.get(id);
    }

    /**
     * Returns a tag type factory from the registry from a provided {@code name}.
     *
     * @param name the name of the tag type to retrieve.
     * @return a tag type factory from the registry from a provided {@code name}.
     */
    public Supplier<Tag> getFactoryFromId(@NonNull String name) {
        Byte id = this.nameToId.get(name);
        if (id == null) return null;

        return this.getFactoryFromId(id);
    }
}
