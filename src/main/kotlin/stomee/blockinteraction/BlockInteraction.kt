package stomee.blockinteraction

import net.minestom.server.extensions.Extension;

class BlockInteraction : Extension() {

    override fun initialize() {
        logger.info("[BlockInteraction] has been enabled!")
    }

    override fun terminate() {
        logger.info("[BlockInteraction] has been disabled!")
    }

}