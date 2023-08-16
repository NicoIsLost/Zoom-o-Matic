package com.nicoislost.command;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.nicoislost.Zooms;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

/**
 * A custom argument type for zooms
 **/
public class ZoomArgumentType implements ArgumentType<Zooms> {

    HashMap<String, Zooms> zooms = new HashMap<>();

    private ZoomArgumentType() {
        for (Zooms zoom : Zooms.values()) {
            if (zoom == Zooms.NONE) continue;
            zooms.put(zoom.getName().toLowerCase().replaceAll("\\W", "_"), zoom);
        }
    }

    public static ZoomArgumentType zoom() {
        return new ZoomArgumentType();
    }

    public static Zooms getZoom(CommandContext<?> c, String zoom) {
        return c.getArgument(zoom, Zooms.class);
    }

    @Override
    public Zooms parse(StringReader reader) throws CommandSyntaxException {

        String name = reader.readUnquotedString();

        if (zooms.containsKey(name)) {
            return zooms.get(name);
        }

        throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherUnknownArgument().createWithContext(reader);
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        for (String name : zooms.keySet()) {
            if (name.startsWith(builder.getRemaining().toLowerCase())) {
                builder.suggest(name);
            }
        }
        return builder.buildFuture();
    }
}
