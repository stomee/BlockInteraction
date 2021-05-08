package stomee.blockinteraction.scraper

import net.minestom.server.instance.block.Block
import net.minestom.server.item.ItemStack
import net.minestom.server.item.Material
import org.json.JSONObject
import kotlin.io.path.Path
import kotlin.io.path.div
import kotlin.io.path.exists
import kotlin.io.path.readText

/**
 * > https://minecraft.fandom.com/wiki/Loot_table
 */
object BlockScraper {

    val path = Path("minecraft_data/data/minecraft/loot_tables/blocks/")

    fun getMaterialByName(name: String) = Material.values().firstOrNull { material ->
        material.getName() == name
    }


    fun grab(block: Block): List<ItemStack>? {

        // get the path
        val blockJSON = path / "${block.name.lowercase()}.json"

        // return if the file doesn't exist
        if (!blockJSON.exists()) return null

        // get all pools this item has
        val pools = JSONObject(blockJSON.readText()).getJSONArray("pools") ?: return null

        return pools.asSequence().map { unCastedPool ->

            val pool = unCastedPool as? JSONObject ?: return@map listOf()

            val entries = pool.getJSONArray("entries") ?: return@map listOf()
            return@map entries.map mapEntry@ { it as? JSONObject ?: return@map null }.mapNotNull(EntryProcessor::process)
        }
            .filterNotNull()
            .flatten()
            .flatten()
            .map { ItemStack.of(it) }.toList()
    }

}