package tools.important.tankhenics.machine.transfer

import tools.important.tankhenics.SignalDirection
import tools.important.tankhenics.machine.Machine

class MachineTurnJointCCW(name: String?, posX: Double, posY: Double) : Machine(name, posX, posY) {
    init {
        description = "A turn-joint that will rotate a signal counter-clockwise"
        setColor(120.0, 70.0, 10.0)
    }

    override fun updateWiring() {
        val poweredDirections = getRecievedSignalDirections()

        for (direction in poweredDirections) {
            var newIndex = direction.ordinal-1
            if (newIndex < 0) newIndex = SignalDirection.entries.count()-1
            outputDirectionalSignal(SignalDirection.entries[newIndex])
        }
    }
}