package stomee.blockinteraction

import net.minestom.server.entity.Player
import net.minestom.server.event.player.PlayerBlockBreakEvent
import net.minestom.server.item.ItemStack
import net.minestom.server.item.Material
import world.cepi.kstom.Manager
import world.cepi.kstom.addEventCallback

fun hook(player: Player) {
    player.addEventCallback<PlayerBlockBreakEvent> {
        val item = ItemStack.of(
            Material.values()
                .firstOrNull { it.isBlock && it.block.blockId == blockStateId }
                ?: return@addEventCallback
        )

        player.inventory.addItemStack(item)
    }
}