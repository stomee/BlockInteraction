package stomee.blockinteraction.scraper

enum class EntryType {

    ITEM,
    TAG,
    LOOT_TABLE,
    GROUP,
    ALTERNATIVES,
    SEQUENCE,
    DYNAMIC,
    EMPTY;

    fun generateNamespace(): String =
        "minecraft:${name.lowercase()}"

}