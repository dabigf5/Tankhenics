package tools.important.tankhenics

import tanks.Game
import tanks.obstacle.Obstacle
import tools.important.tankhenics.machine.input.MachineReceiver
import tools.important.tankhenics.util.gridX
import tools.important.tankhenics.util.gridY

interface Signal {
    fun getRecipients(): Set<Obstacle>
}

enum class SignalDirection(val xOffset: Double, val yOffset: Double) {
    UP(0.0, -1.0),
    RIGHT(1.0, 0.0),
    DOWN(0.0, 1.0),
    LEFT(-1.0, 0.0)
    ;

    fun isVertical(): Boolean {
        return this == UP || this == DOWN
    }

    fun getOpposite(): SignalDirection {
        return when (this) {
            UP -> DOWN
            DOWN -> UP
            LEFT -> RIGHT
            RIGHT -> LEFT
        }
    }
}

class SignalDirect(
    val source: Obstacle,
    val direction: SignalDirection,
) : Signal {
    val offsetX = when(direction) {
        SignalDirection.LEFT -> -1
        SignalDirection.RIGHT -> 1
        else -> 0
    }

    val offsetY = when(direction) {
        SignalDirection.UP -> -1
        SignalDirection.DOWN -> 1
        else -> 0
    }

    val recipientX = source.gridX + offsetX
    val recipientY = source.gridY + offsetY

    val recipient: Obstacle?
        get() {
            return Tankhenics.grid!!.get(recipientX, recipientY)
        }

    override fun getRecipients(): Set<Obstacle> {
        val recipient = recipient ?: return setOf()
        return setOf(recipient)
    }
}

class SignalGroup(
    val source: Obstacle,
    val groupId: Int,
) : Signal {
    override fun getRecipients(): Set<Obstacle> {
        val recipients = mutableSetOf<Obstacle>()
        for (obstacle in Game.obstacles) {
            if (obstacle is MachineReceiver && obstacle.groupID == groupId) {
                recipients.add(obstacle)
            }
        }
        return recipients
    }
}
