package tools.important.tankhenics.machine

import tanks.obstacle.Obstacle
import tools.important.tankhenics.*

abstract class Machine(name: String?, posX: Double, posY: Double) : Obstacle(name, posX, posY) {
    init {
        enableStacking = false
        destructible = false
    }

    abstract fun updateWiring()

    fun getRecievedSignals(): Set<Signal> {
        val recieved = mutableSetOf<Signal>()
        for (signal in Tankhenics.signals) {
            if (signal.getRecipients().contains(this)) recieved.add(signal)
        }
        return recieved.toSet()
    }
    fun getRecievedSignalDirections(): Set<SignalDirection> {
        val poweredDirections = mutableSetOf<SignalDirection>()

        for (signal in getRecievedSignals()) {
            if (signal !is SignalDirect) error("Somehow got a non-direct signal")
            poweredDirections.add(signal.direction)
        }

        return poweredDirections
    }

    fun outputDirectionalSignal(direction: SignalDirection) {
        Tankhenics.newSignals.add(SignalDirect(this, direction))
    }
    fun outputSignal() {
        for (direction in SignalDirection.entries) {
            outputDirectionalSignal(direction)
        }
    }
    fun outputGroupSignal(groupId: Int) {
        Tankhenics.newSignals.add(SignalGroup(this, groupId))
    }

    protected fun setColor(r: Double, g: Double, b: Double) {
        colorR = r
        colorG = g
        colorB = b

        for (i in 0..<default_max_height) {
            stackColorR[i] = r
            stackColorG[i] = g
            stackColorB[i] = b
        }
    }
}