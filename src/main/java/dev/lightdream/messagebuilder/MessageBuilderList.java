package dev.lightdream.messagebuilder;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class MessageBuilderList extends GenericMessageBuilder<List<String>> {


    /**
     * Can use the separator %%new-line%% to split the message into multiple lines.
     *
     * @param base The base message to use.
     */
    public MessageBuilderList(List<String> base) {
        super(base);
    }

    public MessageBuilderList(List<String> base, List<Object> placeholders, List<Object> values) {
        super(base, placeholders, values);
    }

    @Override
    protected boolean equals(List<String> o1, List<String> o2) {
        boolean output = true;

        if (o1 == null) {
            return o2 == null;
        }

        if (o1.size() != o2.size()) {
            return false;
        }

        for (int i = 0; i < o1.size(); i++) {
            output = output && o1.get(i).equals(o2.get(i));
        }

        return output;
    }

    @Override
    protected String convertToString() {
        return parse().toString();
    }

    @Override
    protected List<String> parsePlaceholder(List<String> base, String placeholder, String value) {
        if (base == null) {
            return null;
        }

        List<String> output = new ArrayList<>();

        for (String line : base) {
            output.add(line.replace(placeholder, value));
        }

        return output;
    }

    @Override
    public GenericMessageBuilder<List<String>> clone() {
        return new MessageBuilderList(base, new ArrayList<>(placeholders), new ArrayList<>(values));
    }

}
