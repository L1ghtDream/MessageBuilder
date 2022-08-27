package dev.lightdream.messagebuilder;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;
import dev.lightdream.filemanager.FileManager;
import dev.lightdream.messagebuilder.data_management.deserializer.MessageBuilderDeserializer;
import dev.lightdream.messagebuilder.data_management.deserializer.MessageBuilderListDeserializer;
import dev.lightdream.messagebuilder.data_management.serializer.MessageBuilderListSerializer;
import dev.lightdream.messagebuilder.data_management.serializer.MessageBuilderSerializer;

@SuppressWarnings("unused")
public class MessageBuilderManager {

    public static String colorChar = "&";
    public static String colorCharReplace = "§";
    private static boolean initialized = false;
    private static boolean chatColor = false;

    public static void setColorChar(String colorChar) {
        MessageBuilderManager.colorChar = colorChar;
    }

    public static void setColorCharReplace(String colorCharReplace) {
        MessageBuilderManager.colorCharReplace = colorCharReplace;
    }

    public static void setupSpigot() {
        setColorChar("&");
        setColorCharReplace("§");
    }

    public static void setupForge() {
        setColorChar("&");
        setColorCharReplace("\u00a7");
    }

    public static boolean isInitialized() {
        return initialized;
    }

    public static void init(FileManager fileManager) {
        JsonSerializer<MessageBuilder> messageBuilderSerializer = new MessageBuilderSerializer();
        JsonSerializer<MessageBuilderList> messageBuilderListJsonSerializer = new MessageBuilderListSerializer();

        JsonDeserializer<MessageBuilder> messageBuilderDeserializer = new MessageBuilderDeserializer();
        JsonDeserializer<MessageBuilderList> messageBuilderListDeserializer = new MessageBuilderListDeserializer();

        fileManager.setGsonBuilder(
                fileManager.getGsonBuilder()
                        .registerTypeAdapter(MessageBuilder.class, messageBuilderSerializer)
                        .registerTypeAdapter(MessageBuilder.class, messageBuilderListJsonSerializer)
                        .registerTypeAdapter(MessageBuilder.class, messageBuilderDeserializer)
                        .registerTypeAdapter(MessageBuilder.class, messageBuilderListDeserializer)
        );

        init();
    }

    public static void init() {
        initialized = true;
    }

    public static void disableChatColor() {
        chatColor = false;
    }

    public static void enableChatColor() {
        chatColor = true;
    }

    public static boolean isChatColor() {
        return chatColor;
    }
}
