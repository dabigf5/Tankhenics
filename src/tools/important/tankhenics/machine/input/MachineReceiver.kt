package tools.important.tankhenics.machine.input

import tanks.obstacle.ObstacleTeleporter
import tools.important.tankhenics.SignalGroup
import tools.important.tankhenics.machine.Machine

class MachineReceiver(name: String?, posX: Double, posY: Double) : Machine(name, posX, posY) {
    init {
        description = "A reciever that will output direct signals when a Transmitter with the same GroupID is activated"
        enableGroupID = true
        val (r, g, b) = ObstacleTeleporter.getColorFromID(groupID)
        setColor(r, g, b)
    }

    override fun updateWiring() {
        if (getRecievedSignals().any { it is SignalGroup }) {
            outputSignal()
        }
    }

    override fun setMetadata(data: String) {
        groupID = data.toDouble().toInt()
        val (r, g, b) = ObstacleTeleporter.getColorFromID(groupID)
        setColor(r, g, b)
    }
}