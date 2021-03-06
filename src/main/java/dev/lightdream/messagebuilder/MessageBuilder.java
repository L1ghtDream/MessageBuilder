package dev.lightdream.messagebuilder;

import dev.lightdream.filemanager.FileManager;
import dev.lightdream.logger.Logger;
import org.jetbrains.annotations.Nullable;

import java.util.*;

@SuppressWarnings("unused")
public class MessageBuilder {

    private static boolean chatColor = false;
    private static boolean managerInitialized = false;
    private boolean cloned = false;
    private String base;
    private List<Object> placeholders = new ArrayList<>();
    private List<Object> values = new ArrayList<>();

    public MessageBuilder(Object base) {
        this.base = base.toString();
    }

    @SuppressWarnings("StringConcatenationInLoop")
    public MessageBuilder(String separator, Object... base) {
        this.base = "";
        for (int i = 0; i < base.length; i++) {
            this.base += base[i].toString();
            if (i != base.length - 1) {
                this.base += separator;
            }
        }
    }

    @SuppressWarnings("StringConcatenationInLoop")
    public MessageBuilder(String separator, List<?> base) {
        this.base = "";
        for (int i = 0; i < base.size(); i++) {
            this.base += base.get(i).toString();
            if (i != base.size() - 1) {
                this.base += separator;
            }
        }
    }

    public MessageBuilder(Object... base) {
        this("", base);
    }

    public MessageBuilder(List<?> base) {
        this("", base);
    }

    private MessageBuilder(String base, List<Object> placeholders, List<Object> values) {
        this.base = base;
        this.placeholders = placeholders;
        this.values = values;
    }

    public static void enableChatColor() {
        chatColor = true;
    }

    public static void disableChatColor() {
        chatColor = false;
    }

    public static void setChatColor(boolean chatColor) {
        MessageBuilder.chatColor = chatColor;
    }

    protected static void init() {
        managerInitialized = true;
    }

    public static void init(FileManager fileManager) {
        MessageBuilderManager.init(fileManager);
    }

    @Deprecated
    public MessageBuilder addPlaceholders(HashMap<Object, Object> placeholders) {
        MessageBuilder working = this;
        for (Object placeholder : placeholders.keySet()) {
            String value = placeholders.get(placeholder).toString();
            working = working.addPlaceholders(placeholder, value);
        }
        return working;
    }

    public MessageBuilder parse(HashMap<Object, Object> placeholders) {
        MessageBuilder working = this;
        for (Object placeholder : placeholders.keySet()) {
            String value = placeholders.get(placeholder).toString();
            working = working.parse(placeholder, value);
        }
        return working;
    }

    @Deprecated
    public MessageBuilder addPlaceholders(Object placeholder, Object value) {
        MessageBuilder working = this;
        if (!cloned) {
            working = clone();
        }
        working.placeholders.add(placeholder);
        working.values.add(value);
        return working;
    }

    public MessageBuilder parse(Object placeholder, Object value) {
        MessageBuilder working = this;
        if (!cloned) {
            working = clone();
        }
        working.placeholders.add(placeholder);
        working.values.add(value);
        return working;
    }

    public String parse() {
        if (!MessageBuilder.managerInitialized) {
            Logger.warn("MessageBuilder manager has not been initialized!");
        }
        String parsed = base;

        for (int i = 0; i < Math.min(placeholders.size(), values.size()); i++) {
            String placeholder = null;
            Object placeholderObj = placeholders.get(i);

            String value = "null";
            Object valueObj = values.get(i);

            if (placeholderObj != null) {
                placeholder = placeholderObj.toString();
            }
            if (valueObj != null) {
                value = valueObj.toString();
            }

            parsed = parsed.replace("%" + placeholder + "%", value);
        }

        if (chatColor) {
            parsed = parsed.replace("&", "??");
        }

        this.base = parsed;
        return parsed;
    }

    @SuppressWarnings("MethodDoesntCallSuperMethod")
    public MessageBuilder clone() {
        MessageBuilder output = new MessageBuilder(this.base, new ArrayList<>(this.placeholders), new ArrayList<>(this.values));
        output.cloned = true;
        return output;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageBuilder that = (MessageBuilder) o;
        return Objects.equals(base, that.base);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cloned, getBase(), placeholders, values);
    }

    @SuppressWarnings("unused")
    public MessageBuilder parseAndGet() {
        parse();
        return this;
    }

    @SuppressWarnings("unused")
    public List<String> split(String separator) {
        return new ArrayList<>(Arrays.asList(base.split(separator)));
    }

    @SuppressWarnings("unused")
    public @Nullable String getBase() {
        return this.base;
    }

    @SuppressWarnings("UnusedReturnValue")
    public MessageBuilder setBase(String base) {
        this.base = base;
        return this;
    }

    @Override
    public String toString() {
        return parse();
    }

    public String toHexString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode());
    }


}
