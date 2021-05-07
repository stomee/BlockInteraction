package stomee.blockinteraction

import net.minestom.server.instance.block.Block
import net.minestom.server.item.ItemStack
import net.minestom.server.item.Material
import org.json.JSONArray
import org.json.JSONObject
import kotlin.io.path.Path
import kotlin.io.path.div
import kotlin.io.path.exists
import kotlin.io.path.readText

object BlockScraper {

    val path = Path("minecraft_data/data/minecraft/loot_tables/blocks/")

    fun grab(block: Block): List<ItemStack>? {

        // get the path
        val blockJSON = path / "${block.name.lowercase()}.json"

        // return if the file doesn't exist
        if (!blockJSON.exists()) return null

        // get all pools this item has
        val pools = JSONObject(blockJSON.readText()).getJSONArray("pools") ?: return null

        return pools.map { unCastedPool ->

            val pool = unCastedPool as? JSONObject ?: return@map listOf()

            val entries = pool.getJSONArray("entries") ?: return@map listOf()
            entries.map entry@ {
                val entry = it as? JSONObject ?: return@entry null

                val type = entry.getString("type") ?: return@entry null

                if (type != "minecraft:item") return@entry null

                val item = entry.getString("name") ?: return@entry null

                Material.values().firstOrNull { material ->
                    material.getName() == item
                } ?: return@entry null
            }
        }
            .flatten()
            .filterNotNull()
            .map { ItemStack.of(it) }
    }

}