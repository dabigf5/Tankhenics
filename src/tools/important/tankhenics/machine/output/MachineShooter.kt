package tools.important.tankhenics.machine.output

import tanks.Game
import tanks.bullet.Bullet
import tanks.tank.TankPlayer
import tools.important.tankhenics.machine.Machine

class MachineShooter(name: String?, posX: Double, posY: Double) : Machine(name, posX, posY) {
    init {
        description = "A shooter machine that will fire a basic bullet in the direction it is powered"
        setColor(0.0, 50.0, 50.0)
    }

    var fired = false

    override fun updateWiring() {
        val poweredDirections = getRecievedSignalDirections()

        if (poweredDirections.isEmpty()) {
            fired = false
            return
        }

        if (fired) return
        fired = true
        for (direction in poweredDirections) {
            val bullet = Bullet(
                posX + direction.xOffset * Game.tile_size,
                posY + direction.yOffset * Game.tile_size,
                0,
                Game.dummyTank,
                TankPlayer.default_bullet
            )
            bullet.vX = direction.xOffset * bullet.item.speed
            bullet.vY = direction.yOffset * bullet.item.speed
            Game.movables.add(bullet)
            //Game.eventsOut.add(EventShootBullet(bullet))
        }
    }
}