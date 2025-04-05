package tools.important.tankhenics.machine.logic

import tools.important.tankhenics.SignalDirection
import tools.important.tankhenics.machine.Machine

class MachineXorGate(name: String?, posX: Double, posY: Double) : Machine(name, posX, posY) {
    init {
        description = "A XOR logic gate"
        setColor(100.0, 100.0, 0.0)
    }

    override fun updateWiring() {
        val poweredDirections = getRecievedSignalDirections()

        val up = poweredDirections.contains(SignalDirection.UP)
        val down = poweredDirections.contains(SignalDirection.DOWN)

        if ((up || down) && !(up && down)) {
            outputDirectionalSignal(SignalDirection.RIGHT)
        }
    }
}