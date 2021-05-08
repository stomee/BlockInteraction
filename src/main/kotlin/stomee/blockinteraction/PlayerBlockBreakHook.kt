package stomee.blockinteraction

import net.minestom.server.entity.ItemEntity
import net.minestom.server.entity.Player
import net.minestom.server.event.player.PlayerBlockBreakEvent
import net.minestom.server.instance.Instance
import net.minestom.server.instance.block.Block
import net.minestom.server.item.ItemStack
import net.minestom.server.utils.BlockPosition
import net.minestom.server.utils.time.TimeUnit
import stomee.blockinteraction.scraper.BlockScraper
import world.cepi.kstom.addEventCallback

fun generateItem(item: ItemStack, position: BlockPosition, instance: Instance) {
    val entity = ItemEntity(item, position.toPosition().add(.5, .25, .5), instance)

    entity.setInstance(instance, position.toPosition().add(.5, .25, .5))

    entity.setPickupDelay(30, TimeUnit.TICK)
}

fun hook(player: Player) {
    player.addEventCallback<PlayerBlockBreakEvent> {

        val items = BlockScraper.grab(Block.values().firstOrNull { it.blockId == blockStateId } ?: return@addEventCallback )

        items?.forEach { item -> generateItem(item, blockPosition, player.instance!!) }
    }
}