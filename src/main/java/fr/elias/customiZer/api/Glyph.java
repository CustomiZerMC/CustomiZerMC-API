package fr.elias.customiZer.api;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a single registered font image (glyph) in CustomiZer.
 *
 * <p>Glyphs are Unicode Private Use Area characters that render custom PNG
 * images in-game via the resource pack's {@code minecraft:default} font.
 * CustomiZer reserves U+EC00 and above (above ItemsAdder's U+E000–U+EBFF range).
 *
 * <p>Obtain instances via {@link CustomiZerAPI#getGlyph(String, String)},
 * {@link CustomiZerAPI#getAllGlyphs()}, or {@link CustomiZerAPI#searchGlyphs(String)}.
 *
 * <h3>PlaceholderAPI</h3>
 * <pre>
 *   %customizer_glyph_&lt;pack&gt;_&lt;id&gt;%         -&gt; the rendered character (use in chat / lore)
 *   %customizer_glyph_unicode_&lt;pack&gt;_&lt;id&gt;%  -&gt; Unicode escape (e.g. \\uEC00)
 *   %customizer_glyph_hex_&lt;pack&gt;_&lt;id&gt;%      -&gt; hex codepoint  (e.g. EC00)
 * </pre>
 *
 * <h3>gui.yml / text tags</h3>
 * <pre>
 *   &lt;font_image:packName:fontId&gt;
 * </pre>
 */
public final class Glyph {

    private final String packName;
    private final String fontId;
    private final String namespace;
    private final String texturePath;
    private final int height;
    private final int ascent;
    private final int xOffset;
    private final int codepoint;

    /** Constructed internally by PackManager — use the API to obtain instances. */
    public Glyph(@NotNull String packName, @NotNull String fontId,
                 @NotNull String namespace, @NotNull String texturePath,
                 int height, int ascent, int xOffset, int codepoint) {
        this.packName    = packName;
        this.fontId      = fontId;
        this.namespace   = namespace;
        this.texturePath = texturePath;
        this.height      = height;
        this.ascent      = ascent;
        this.xOffset     = xOffset;
        this.codepoint   = codepoint;
    }

    @NotNull public String getPackName()    { return packName; }
    @NotNull public String getFontId()      { return fontId; }
    @NotNull public String getNamespace()   { return namespace; }
    @NotNull public String getTexturePath() { return texturePath; }
    public int getHeight()  { return height; }
    public int getAscent()  { return ascent; }
    public int getXOffset() { return xOffset; }
    public int getCodepoint() { return codepoint; }

    @NotNull
    public String getCharacter() {
        return new String(Character.toChars(codepoint));
    }

    @NotNull
    public String getUnicodeEscape() {
        if (codepoint <= 0xFFFF) {
            return "\\u" + String.format("%04X", codepoint);
        }
        char[] surrogates = Character.toChars(codepoint);
        return "\\u" + String.format("%04X", (int) surrogates[0])
             + "\\u" + String.format("%04X", (int) surrogates[1]);
    }

    @NotNull
    public String getHex() {
        return String.format("%04X", codepoint);
    }

    @NotNull
    public String getTag() {
        return "<font_image:" + packName + ":" + fontId + ">";
    }

    @NotNull
    public String toConfigSnippet() {
        return "    " + fontId + ":\n"
             + "      glyph: '" + getUnicodeEscape() + "'\n"
             + "      path: " + texturePath + "\n"
             + "      scale_ratio: " + height + "\n"
             + "      y_position: " + ascent + "\n"
             + (xOffset != 0 ? "      x_position: " + xOffset + "\n" : "");
    }

    @Override
    public String toString() {
        return "Glyph{" + packName + ":" + fontId + " U+" + getHex() + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Glyph g)) return false;
        return codepoint == g.codepoint
                && packName.equals(g.packName)
                && fontId.equals(g.fontId);
    }

    @Override
    public int hashCode() {
        return 31 * packName.hashCode() + fontId.hashCode();
    }
}
