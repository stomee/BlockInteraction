package stomee.blockinteraction

import net.minestom.server.entity.ItemEntity
import net.minestom.server.entity.Player
import net.minestom.server.event.player.PlayerBlockBreakEvent
import net.minestom.server.item.ItemStack
import net.minestom.server.item.Material
import net.minestom.server.utils.time.TimeUnit
import world.cepi.kstom.Manager
import world.cepi.kstom.addEventCallback

fun hook(player: Player) {
    player.addEventCallback<PlayerBlockBreakEvent> {
        val item = ItemStack.of(
            Material.values()
                .firstOrNull { it.isBlock && it.block.blockId == blockStateId }
                ?: return@addEventCallback
        )

        val entity = ItemEntity(item, blockPosition.toPosition().add(.5, .25, .5), player.instance!!)

        entity.setInstance(player.instance!!, blockPosition.toPosition().add(.5, .25, .5))

        entity.setPickupDelay(2, TimeUnit.SECOND)
    }
}