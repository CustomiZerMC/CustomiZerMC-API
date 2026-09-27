package fr.elias.customiZer.api;

import org.jetbrains.annotations.Nullable;

/** Modern visual model metadata; null fields mean no explicit setting.
 * Kept separate to preserve PackItemInfo's existing constructor and record shape. */
public record PackModelInfo(@Nullable String itemModel, @Nullable String resolvedModelKey,
                            @Nullable String equippableModel, @Nullable String equippableSound,
                            @Nullable String tooltipStyle) { }
