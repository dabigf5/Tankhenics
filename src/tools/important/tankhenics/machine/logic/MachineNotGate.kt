package tools.important.tankhenics.machine.logic

import tools.important.tankhenics.SignalDirect
import tools.important.tankhenics.SignalDirection
import tools.important.tankhenics.machine.Machine

class MachineNotGate(name: String?, posX: Double, posY: Double) : Machine(name, posX, posY) {
    init {
        description = "A NOT logic gate that will output power when it is not being powered"
        setColor(100.0, 0.0, 0.0)
    }

    override fun updateWiring() {
        if (getRecievedSignals().none {it is SignalDirect && it.direction == SignalDirection.RIGHT})
        outputDirectionalSignal(SignalDirection.RIGHT)
    }
}