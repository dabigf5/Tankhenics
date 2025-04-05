package tools.important.tankhenics.machine.transfer

import tools.important.tankhenics.SignalDirection
import tools.important.tankhenics.machine.Machine

class MachineWire(name: String?, posX: Double, posY: Double) : Machine(name, posX, posY) {
    init {
        description = "A wire that will send signals in every direction it's not being powered, if it is being powered"
        setColor(10.0, 10.0, 10.0)
    }

    override fun updateWiring() {
        val poweredDirections = getRecievedSignalDirections()
        if (poweredDirections.isEmpty()) return

        val outputDirections = SignalDirection.entries - poweredDirections

        for (direction in outputDirections) {
            outputDirectionalSignal(direction.getOpposite())
        }
    }
}