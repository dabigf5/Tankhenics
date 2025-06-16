package tools.important.tankhenics.machine.output

import tanks.obstacle.ObstacleTeleporter
import tools.important.tankhenics.SignalDirect
import tools.important.tankhenics.machine.Machine

class MachineTransmitter(name: String?, posX: Double, posY: Double) : Machine(name, posX, posY) {
    init {
        description = "A transmitter that will activate Receivers with the same Group ID"
        enableGroupID = true
        val (r, g, b) = ObstacleTeleporter.getColorFromID(groupID)
        setColor(r/2, g/2, b/2)
    }

    override fun updateWiring() {
        val signals = getRecievedSignals()
        if (signals.any {it is SignalDirect}) {
            outputGroupSignal(groupID)
        }
    }

    override fun setMetadata(data: String) {
        groupID = data.toDouble().toInt()
        val (r, g, b) = ObstacleTeleporter.getColorFromID(groupID)
        setColor(r/2, g/2, b/2)
    }
}