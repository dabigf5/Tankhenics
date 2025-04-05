package tools.important.tankhenics.machine.input

import tanks.Movable
import tanks.bullet.Bullet
import tanks.bullet.BulletGas
import tanks.bullet.legacy.BulletAir
import tanks.bullet.legacy.BulletFlame
import tools.important.tankhenics.machine.Machine
import tools.important.tankhenics.util.redraw

class MachineSwitch(name: String?, posX: Double, posY: Double) : Machine(name, posX, posY) {
    init {
        destructible = false
        description = "A switch that gets flipped when shot"
        allowBounce = false
        checkForObjects = true

        updateColor()
    }

    override fun onObjectEntry(m: Movable) {
        if (m is Bullet
            && m !is BulletGas // just in case
            && m !is BulletAir
            && m !is BulletFlame
            ) switched = !switched
    }

    var switched = false
        set(v) {
            field = v
            updateColor()
            redraw()
        }

    fun updateColor() {
        if (switched)
            setColor(0.0, 255.0, 0.0)
        else
            setColor(255.0, 0.0, 0.0)
    }

    override fun updateWiring() {
        if (switched) outputSignal()
    }
}