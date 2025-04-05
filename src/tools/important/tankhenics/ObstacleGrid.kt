package tools.important.tankhenics

import tanks.Game
import tanks.obstacle.Obstacle
import tools.important.tankhenics.util.gridX
import tools.important.tankhenics.util.gridY

private fun getObstacleAtGridPos(x: Int, y: Int): Obstacle? {
    for (obstacle in Game.obstacles) {
        val obstacleGridX = obstacle.gridX
        val obstacleGridY = obstacle.gridY

        if (obstacleGridX == x && obstacleGridY == y) return obstacle
    }

    return null
}

class ObstacleGrid {
    private val grid: Array<Obstacle?>
    val height: Int = Game.currentLevel.sizeY
    val width: Int = Game.currentLevel.sizeX

    init {
        grid = Array(width * height) {null}
        refresh()
    }

    fun refresh() {
        for (y in 0..<height) {
            for (x in 0..<width) {
                grid[y * width + x] = getObstacleAtGridPos(x, y)
            }
        }
    }

    fun get(x: Int, y: Int): Obstacle? {
        if (x < 0 || y < 0 || x >= width || y >= height) return null

        return grid[y * width + x]
    }
}