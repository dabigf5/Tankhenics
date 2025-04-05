package tools.important.tankhenics.machine.transfer

import tools.important.tankhenics.machine.Machine

class MachineRepeater(name: String?, posX: Double, posY: Double) : Machine(name, posX, posY) {
    init {
        description = "A repeater that will propagate signals in a straight line"
        setColor(90.0, 70.0, 10.0)
    }

    override fun updateWiring() {
        val poweredDirections = getRecievedSignalDirections()

        for (direction in poweredDirections) {
            outputDirectionalSignal(direction)
        }
    }
}