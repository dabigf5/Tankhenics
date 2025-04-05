package tools.important.tankhenics.machine.output

import tools.important.tankhenics.machine.Machine
import tools.important.tankhenics.util.redraw

class MachineLamp(name: String?, posX: Double, posY: Double) : Machine(name, posX, posY) {
    init {
        description = "A lamp that turns on when given a signal"

        updateColor()
    }

    var switched = false
        set(v) {
            if (v == field) return

            field = v
            updateColor()
            redraw()
        }

    fun updateColor() {
        if (switched)
            setColor(232.0, 252.0, 255.0)
        else
            setColor(30.0, 30.0, 30.0)
    }

    override fun updateWiring() {
        switched = getRecievedSignals().isNotEmpty()
    }
}