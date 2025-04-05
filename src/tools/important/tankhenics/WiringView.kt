package tools.important.tankhenics

import tanks.Drawing
import tanks.Game
import tools.important.tankhenics.machine.Machine
import tools.important.tankhenics.util.fillArrow
import tools.important.tankhenics.util.gridToGame

fun drawWiringView() {
    val drawing = Drawing.drawing!!
    val grid = Tankhenics.grid!!

    drawing.setFontSize(10.0)
    for (y in 0..<grid.height) {
        for (x in 0..<grid.width) {
            val obstacle = grid.get(x, y)

            drawing.setColor(100.0, 100.0, 100.0, 75.0)
            val gameX = x.gridToGame()
            val gameY = y.gridToGame()
            drawing.fillRect(gameX, gameY, Game.tile_size, Game.tile_size)
            drawing.drawRect(gameX, gameY, Game.tile_size, Game.tile_size)

            if (obstacle == null) continue
            if (obstacle !is Machine) continue

            drawing.setColor(obstacle.colorR, obstacle.colorG, obstacle.colorB)
        }
    }

    for (signal in Tankhenics.signals) {
        when (signal) {
            is SignalDirect -> {
                val sourceX = signal.source.posX
                val sourceY = signal.source.posY
                val recipientX = signal.recipientX.gridToGame()
                val recipientY = signal.recipientY.gridToGame()

                drawing.setColor(255.0, 255.0, 0.0, 100.0)
                fillArrow(sourceX, sourceY, recipientX, recipientY, 10.0, 15.0)
            }
        }
    }

    drawing.setColor(255.0, 255.0, 255.0)
    drawing.setInterfaceFontSize(25.0)
    drawing.drawInterfaceText(drawing.interfaceSizeX * 0.5, drawing.interfaceSizeY * 0.1, "Wiring View")
}