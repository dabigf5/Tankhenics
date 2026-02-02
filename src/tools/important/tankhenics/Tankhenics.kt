package tools.important.tankhenics

import main.Tanks
import org.lwjgl.glfw.GLFW
import tanks.Game
import tanks.Panel
import tanks.extension.Extension
import tanks.gui.screen.Screen
import tanks.gui.screen.ScreenGame
import tanks.gui.screen.ScreenPartyLobby
import tools.important.tankhenics.machine.Machine
import tools.important.tankhenics.machine.input.MachineReceiver
import tools.important.tankhenics.machine.input.MachineSwitch
import tools.important.tankhenics.machine.logic.MachineAndGate
import tools.important.tankhenics.machine.logic.MachineNotGate
import tools.important.tankhenics.machine.logic.MachineOrGate
import tools.important.tankhenics.machine.logic.MachineXorGate
import tools.important.tankhenics.machine.output.MachineLamp
import tools.important.tankhenics.machine.output.MachineShooter
import tools.important.tankhenics.machine.output.MachineTransmitter
import tools.important.tankhenics.machine.transfer.*

object Tankhenics {
    const val UPDATE_DELAY: Double = 0.0

    val newSignals: MutableSet<Signal> = mutableSetOf()
    val signals: MutableSet<Signal> = mutableSetOf()

    var grid: ObstacleGrid? = null
    var lastScreen: Screen? = null

    fun loadWiring() {
        grid = ObstacleGrid()
    }

    fun cleanupWiring() {
        grid = null
        signals.clear()
        newSignals.clear()
    }

    fun pushSignals() {
        signals.clear()

        for (newSignal in newSignals) {
            signals.add(newSignal)
        }

        newSignals.clear()
    }

    fun updateWiring() {
        val grid = grid ?: return

        // yes, doing this every update does rip directly into the shithole of performance,
        // but any checks would be iterating through Game.obstacles anyways, because
        // Game.removeObstacles is always empty when I check for some reason
        grid.refresh()

        for (y in 0..<grid.height) {
            for (x in 0..<grid.width) {
                val entry = grid.get(x, y) ?: continue
                if (entry !is Machine) continue

                entry.updateWiring()
            }
        }

        pushSignals()
    }
}

class TankhenicsExtension : Extension("Tankhenics") {
    var remainingUpdateCooldown: Double = 0.0

    override fun setUp() {
        // transfer //
        Game.registerObstacle(MachineRepeater::class.java, "tk_repeater")
        Game.registerObstacle(MachineBuriedRepeater::class.java, "tk_buriedrepeater")
        Game.registerObstacle(MachineWire::class.java, "tk_wire")
        Game.registerObstacle(MachineTurnJointCCW::class.java, "tk_turnjointccw")
        Game.registerObstacle(MachineTurnJointCW::class.java, "tk_turnjointcw")

        // input //
        Game.registerObstacle(MachineSwitch::class.java, "tk_switch")
        Game.registerObstacle(MachineReceiver::class.java, "tk_reciever")
        // output //
        Game.registerObstacle(MachineLamp::class.java, "tk_lamp")
        Game.registerObstacle(MachineShooter::class.java, "tk_shooter")
        Game.registerObstacle(MachineTransmitter::class.java, "tk_transmitter")

        // logic //
        Game.registerObstacle(MachineNotGate::class.java, "tk_notgate")
        Game.registerObstacle(MachineAndGate::class.java, "tk_andgate")
        Game.registerObstacle(MachineOrGate::class.java, "tk_orgate")
        Game.registerObstacle(MachineXorGate::class.java, "tk_xorgate")
    }

    override fun draw() {
        if (Tankhenics.grid != null && Game.game.window.validPressedKeys.contains(GLFW.GLFW_KEY_X)) drawWiringView()
    }


    override fun update() {
        val screen = Game.screen
        if (!ScreenPartyLobby.isClient && (screen is ScreenGame && screen.playing && !screen.paused && !ScreenGame.finished)) {
            remainingUpdateCooldown -= Panel.frameFrequency
            if (remainingUpdateCooldown <= 0) {
                remainingUpdateCooldown = Tankhenics.UPDATE_DELAY
                Tankhenics.updateWiring()
            }
        }

        if (!ScreenPartyLobby.isClient) {
            if (screen != Tankhenics.lastScreen) {
                if (screen is ScreenGame) Tankhenics.loadWiring() else Tankhenics.cleanupWiring()
            }
        }

        Tankhenics.lastScreen = screen
    }
}

fun main() {
    Tanks.launchWithExtensions(arrayOf("debug"), arrayOf(TankhenicsExtension()), null)
}