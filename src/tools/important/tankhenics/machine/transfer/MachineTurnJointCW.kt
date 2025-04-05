package tools.important.tankhenics.machine.transfer

import tools.important.tankhenics.SignalDirection
import tools.important.tankhenics.machine.Machine

class MachineTurnJointCW(name: String?, posX: Double, posY: Double) : Machine(name, posX, posY) {
    init {
        description = "A turn-joint that will rotate a signal clockwise"
        setColor(80.0, 70.0, 140.0)
    }

    override fun updateWiring() {
        val poweredDirections = getRecievedSignalDirections()

        for (direction in poweredDirections) {
            var newIndex = direction.ordinal+1
            if (newIndex >= SignalDirection.entries.count()) newIndex = 0
            outputDirectionalSignal(SignalDirection.entries[newIndex])
        }
    }
}