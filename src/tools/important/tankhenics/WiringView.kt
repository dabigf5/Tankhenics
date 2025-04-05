package tools.important.tankhenics

import tanks.Drawing
import tanks.Game
import tools.important.tankhenics.util.fillArrow
import tools.important.tankhenics.util.gridToGame

fun drawWiringView() {
    val drawing = Drawing.drawing!!
    val grid = Tankhenics.grid!!

    for (y in 0..<grid.height) {
        for (x in 0..<grid.width) {
            val gameX = x.gridToGame()
            val gameY = y.gridToGame()

            drawing.setColor(100.0, 100.0, 130.0, 75.0)
            drawing.fillRect(gameX, gameY, Game.tile_size, Game.tile_size)

            drawing.setColor(100.0, 100.0, 150.0, 75.0)
            drawing.drawRect(gameX, gameY, Game.tile_size, Game.tile_size)
        }
    }


    // TODO: add an indicator for groupid signals
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