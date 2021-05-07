package stomee.blockinteraction

import net.minestom.server.extensions.Extension;
import world.cepi.kstom.Manager

class BlockInteraction : Extension() {

    override fun initialize() {

        Manager.connection.addPlayerInitialization(::hook)

        logger.info("[BlockInteraction] has been enabled!")
    }

    override fun terminate() {

        Manager.connection.removePlayerInitialization(::hook)

        logger.info("[BlockInteraction] has been disabled!")
    }

}