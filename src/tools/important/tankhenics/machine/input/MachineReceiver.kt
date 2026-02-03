package tools.important.tankhenics.machine.input

import tanks.obstacle.ObstacleTeleporter
import tanks.tankson.MetadataProperty
import tools.important.tankhenics.SignalGroup
import tools.important.tankhenics.machine.Machine


class MachineReceiver(name: String?, posX: Double, posY: Double) : Machine(name, posX, posY) {
    @JvmField
    @field:MetadataProperty(
        id = "group_id",
        name = "Group ID",
        image = "id.png",
        selector = "group_id",
        keybind = "editor.groupID"
    )
    var groupID: Int = 0

    init {
        description = "A reciever that will output direct signals when a Transmitter with the same GroupID is activated"
        primaryMetadataID = "group_id"
        type = ObstacleType.extra

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

    override fun getMetadata(): String {
        return groupID.toString()
    }
}