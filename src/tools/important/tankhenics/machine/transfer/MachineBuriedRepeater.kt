package tools.important.tankhenics.machine.transfer

import tanks.Game
import tanks.gui.screen.leveleditor.ScreenLevelEditor
import tools.important.tankhenics.machine.Machine

class MachineBuriedRepeater(name: String?, posX: Double, posY: Double) : Machine(name, posX, posY) {
    init {
        description = "A buried repeater that will propagate signals in a straight line"
        setColor(80.0, 60.0, 5.0)
        tankCollision = false
        bulletCollision = false
    }

    override fun updateWiring() {
        val poweredDirections = getRecievedSignalDirections()

        for (direction in poweredDirections) {
            outputDirectionalSignal(direction)
        }
    }

    override fun draw() {
        if (Game.screen is ScreenLevelEditor) {
            super.draw()
        }
    }
}