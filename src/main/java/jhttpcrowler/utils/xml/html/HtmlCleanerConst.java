package jhttpcrowler.utils.xml.html;

/**
 * Html Cleaner constants
 *
 * @author Sergey Prilukin
 */
public final class HtmlCleanerConst {
    public static final int TO_DEFAULT = 0;
    public static final int TO_SIMPLE_STRING = 1;
    public static final int TO_PRETTY_STRING = 2;
    public static final int TO_BROWSER_COMPACT_STRING = 4;
    public static final int TO_INPUT_STREAM = 8;
    public static final int TO_NODE = 16;

    public static final String ADVANCED_XML_ESCAPE = "advancedXmlEscape";
    public static final String CDATA_FOR_SCRIPT_AND_STYLE = "cdataForScriptAndStyle";
    public static final String SPECIAL_ENTITIES = "specialEntities";
    public static final String RECOGNIZE_UNICODE_CHARS = "recognizeUnicodeChars";
    public static final String OMIT_UNKNOWN_TAGS = "omitUnknownTags";
    public static final String USE_EMPTY_ELEMENT_TAGS = "useEmptyElementTags";
    public static final String TREAT_UNKNOWNTAGS_AS_CONTENT = "treatUnknownTagsAsContent";
    public static final String OMIT_DEPRECATED_TAGS = "omitDeprecatedTags";
    public static final String TREAT_DEPRTAGS_AS_CONTENT = "treatDeprTagsAsContent";
    public static final String OMIT_XML_DECL = "omitXmlDecl";
    public static final String OMIT_COMMENTS = "omitComments";
    public static final String OMIT_HTML_ENVELOPE = "omitHtmlEnvelope";
    public static final String OMIT_DOCTYPE_DECLARATION = "omitDoctypeDeclaration";
    public static final String ALLOW_MULTIWORD_ATTRIBUTES = "allowMultiWordAttributes";
    public static final String ALLOW_HTML_INSIDE_ATTRIBUTES = "allowHtmlInsideAttributes";
    public static final String NAMESPACES_AWARE = "namespacesAware";
    public static final String HYPHEN_REPLACEMENT = "hyphenReplacement";
    public static final String PRUNE_TAGS = "pruneTags";
    public static final String BOOLEAN_ATTS = "booleanAtts";
    public static final String IGNORE_QUEST_AND_EXCLAM = "ignoreQuestAndExclam";
}
