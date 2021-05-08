package stomee.blockinteraction.scraper

import net.minestom.server.item.Material
import org.json.JSONObject

object EntryProcessor {
    fun process(entry: JSONObject): List<Material>? {

        val type = entry.getString("type") ?: return null

        val typeEnum = EntryType.values().firstOrNull { entryType -> entryType.generateNamespace() == type } ?: return null

        return when (typeEnum) {
            EntryType.ITEM -> listOf(BlockScraper.getMaterialByName(entry.getString("name") ?: return null) ?: return null)
            EntryType.ALTERNATIVES -> {
                entry.getJSONArray("children")
                    .map { it as? JSONObject ?: return@map null }
                    .filterNotNull()
                    .mapNotNull(::process)
                    .flatten()
            }
            else -> return null
        }
    }
}