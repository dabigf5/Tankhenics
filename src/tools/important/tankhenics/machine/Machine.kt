package tools.important.tankhenics.machine

import tanks.Drawing
import tanks.Game
import tanks.obstacle.Obstacle
import tools.important.tankhenics.*


abstract class Machine(name: String?, posX: Double, posY: Double) : Obstacle(name, posX, posY) {
    init {
        destructible = false
    }

    override fun draw3dOutline(p0: Double, p1: Double, p2: Double, p3: Double) {
        val drawing = Drawing.drawing

        drawing.fillBox(this.posX, this.posY, this.startHeight * 50.0, 51.0, 51.0, 1.0, 0.toByte())
    }

    override fun getTileHeight(): Double {
        return 1.0
    }

    override fun draw() {
            val drawing = Drawing.drawing
            drawing.setColor(this.colorR, this.colorG, this.colorB, this.colorA, this.glow)
            if (Game.enable3d) {
                drawing.fillBox(
                    this,
                    this.posX,
                    this.posY,
                    this.startHeight * 50.0,
                    draw_size,
                    draw_size,
                    draw_size,
                )
            } else {
                drawing.fillRect(this, posX, posY, draw_size, draw_size)
            }
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
        TODO("Group Signals")
        // Tankhenics.newSignals.add(SignalGroup(this, groupId))
    }

    protected fun setColor(r: Double, g: Double, b: Double) {
        colorR = r
        colorG = g
        colorB = b
    }
}