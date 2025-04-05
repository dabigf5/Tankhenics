package tools.important.tankhenics.util

import tanks.Game
import tanks.obstacle.Obstacle

fun Obstacle.redraw() {
    Game.redrawObstacles.add(this)
}



fun Int.gridToGame(): Double {
    return (this * Game.tile_size) + Game.tile_size / 2
}
fun Double.gameToGrid(): Int {
    return (this / Game.tile_size).toInt()
}

val Obstacle.gridX: Int
    get() = posX.gameToGrid()

val Obstacle.gridY: Int
    get() = posY.gameToGrid()