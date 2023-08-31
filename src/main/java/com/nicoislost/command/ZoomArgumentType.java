package com.nicoislost.command;

import com.google.common.collect.ImmutableMap;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.nicoislost.Zooms;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

/**
 * A custom argument type for zooms
 **/
public class ZoomArgumentType implements ArgumentType<Zooms> {

    private final ImmutableMap<String, Zooms> zooms =
            Arrays.stream(Zooms.values()).collect(ImmutableMap.toImmutableMap(
                    zoom -> zoom.getName().toLowerCase().replaceAll("\\W", "_"),
                    zoom -> zoom
            ));


    public static ZoomArgumentType zoom() {
        return new ZoomArgumentType();
    }

    public static Zooms getZoom(CommandContext<?> c, String zoom) {
        return c.getArgument(zoom, Zooms.class);
    }

    @Override
    public @NotNull Zooms parse(StringReader reader) throws CommandSyntaxException {

        String name = reader.readUnquotedString();

        Zooms zoom;
        if ((zoom = zooms.get(name)) != null) {
            return zoom;
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
