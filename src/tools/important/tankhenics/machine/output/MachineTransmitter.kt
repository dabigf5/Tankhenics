package tools.important.tankhenics.machine.output

import tanks.obstacle.ObstacleTeleporter
import tanks.tankson.MetadataProperty
import tools.important.tankhenics.SignalDirect
import tools.important.tankhenics.machine.Machine

class MachineTransmitter(name: String?, posX: Double, posY: Double) : Machine(name, posX, posY) {
    @MetadataProperty(
        id = "group_id",
        name = "Group ID",
        image = "id.png",
        selector = "group_id",
        keybind = "editor.groupID"
    )
    var groupID: Int = 0

    init {
        description = "A transmitter that will activate Receivers with the same Group ID"
        primaryMetadataID = "group_id"

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

    override fun getMetadata(): String {
        return groupID.toString()
    }
}