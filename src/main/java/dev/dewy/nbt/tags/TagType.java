package dev.dewy.nbt.tags;

import dev.dewy.nbt.api.Tag;
import dev.dewy.nbt.api.registry.TagTypeRegistry;
import dev.dewy.nbt.api.registry.TagTypeRegistryException;
import dev.dewy.nbt.tags.array.ByteArrayTag;
import dev.dewy.nbt.tags.array.IntArrayTag;
import dev.dewy.nbt.tags.array.LongArrayTag;
import dev.dewy.nbt.tags.collection.CompoundTag;
import dev.dewy.nbt.tags.collection.ListTag;
import dev.dewy.nbt.tags.primitive.*;

import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Defines the 12 standard NBT tag types and their IDs supported by this library, laid out in the Notchian spec.
 *
 * @author dewy
 */
public class TagType implements Predicate<TagType> {
    public static TagType END = new TagType(0, "end", EndTag::new);

    /**
     * ID: 1
     *
     * @see ByteTag
     */
    public static TagType BYTE = new TagType(1, "byte", true, ByteTag::new);

    /**
     * ID: 2
     *
     * @see ShortTag
     */
    public static TagType SHORT = new TagType(2, "short", true, ShortTag::new);

    /**
     * ID: 3
     *
     * @see IntTag
     */
    public static TagType INT = new TagType(3, "int", true, IntTag::new);

    /**
     * ID: 4
     *
     * @see LongTag
     */
    public static TagType LONG = new TagType(4, "long", true, LongTag::new);

    /**
     * ID: 5
     *
     * @see FloatTag
     */
    public static TagType FLOAT = new TagType(5, "float", true, FloatTag::new);

    /**
     * ID: 6
     *
     * @see DoubleTag
     */
    public static TagType DOUBLE = new TagType(6, "double", true, DoubleTag::new);

    /**
     * ID: 7
     *
     * @see ByteArrayTag
     */
    public static TagType BYTE_ARRAY = new TagType(7, "byte_array", ByteArrayTag::new);

    /**
     * ID: 8
     *
     * @see StringTag
     */
    public static TagType STRING = new TagType(8, "string", StringTag::new);

    /**
     * ID: 9
     *
     * @see ListTag
     */
    public static TagType LIST = new TagType(9, "list", ListTag::new);

    /**
     * ID: 10
     *
     * @see CompoundTag
     */
    public static TagType COMPOUND = new TagType(10, "compound", CompoundTag::new);

    /**
     * ID: 11
     *
     * @see IntArrayTag
     */
    public static TagType INT_ARRAY = new TagType(11, "int_array", IntArrayTag::new);

    /**
     * ID: 12
     *
     * @see LongArrayTag
     */
    public static TagType LONG_ARRAY = new TagType(12, "long_array", LongArrayTag::new);

    private final int id;
    private final String name;
    private final Supplier<Tag> factory;
    private final boolean isNumber;

    public TagType(int id, String name, Supplier<Tag> factory) {
        this(id, name, false, factory);
    }

    public TagType(int id, String name, boolean isNumber, Supplier<Tag> factory) {
        this.id = id;
        this.name = name;
        this.factory = factory;
        this.isNumber = isNumber;
    }

    public byte getId() {
        return (byte) this.id;
    }

    public String getName() {
        return this.name;
    }

    public Supplier<Tag> getFactory() {
        return this.factory;
    }

    public boolean isNumber() {
        return this.isNumber;
    }

    @Override
    public boolean test(TagType that) {
        return this == that;
    }

    public static void registerAll(TagTypeRegistry registry) {
        try {
            registry.registerTagType(BYTE.getId(), BYTE.getName(), BYTE.getFactory());
        } catch (TagTypeRegistryException ex) {
            // Should never happen.
            ex.printStackTrace();
        }
    }
}
