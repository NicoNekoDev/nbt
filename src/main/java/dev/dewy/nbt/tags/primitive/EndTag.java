package dev.dewy.nbt.tags.primitive;

import dev.dewy.nbt.api.Tag;
import dev.dewy.nbt.api.registry.TagTypeRegistry;
import dev.dewy.nbt.tags.TagType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * The string tag (type ID 8) is used for storing a UTF-8 encoded {@code String}, prefixed by a length value stored as a 32-bit {@code int}.
 *
 * @author dewy
 */
@NoArgsConstructor
public class EndTag extends Tag {

    /**
     * Constructs a string tag with a given name.
     *
     * @param name the tag's name.
     */
    public EndTag(String name) {
        this.setName(name);
    }

    @Override
    public TagType getType() {
        return TagType.END;
    }

    @Override
    public Void getValue() {
        return null;
    }

    @Override
    public EndTag write(DataOutput output, int depth, TagTypeRegistry registry) throws IOException {
        return this;
    }

    @Override
    public EndTag read(DataInput input, int depth, TagTypeRegistry registry) throws IOException {
        return this;
    }

    @Override
    public Tag copy() {
        return new EndTag();
    }
}
