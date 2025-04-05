package tools.important.tankhenics.util

import tanks.Drawing
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

fun fillLine(x1: Double, y1: Double, x2: Double, y2: Double, width: Double) {
    val halfWidth = width / 2

    val dx = x2 - x1
    val dy = y2 - y1

    val length = sqrt(dx*dx + dy*dy)
    val unitX = dx / length
    val unitY = dy / length

    val perpX = -unitY * halfWidth
    val perpY = unitX * halfWidth

    val x1a = x1 + perpX
    val y1a = y1 + perpY
    val x1b = x1 - perpX
    val y1b = y1 - perpY

    val x2a = x2 + perpX
    val y2a = y2 + perpY
    val x2b = x2 - perpX
    val y2b = y2 - perpY

    Drawing.drawing.fillQuad(x1a, y1a, x2a, y2a, x2b, y2b, x1b, y1b)
}

fun fillArrow(x1: Double, y1: Double, x2: Double, y2: Double, width: Double, headLength: Double = 50.0, headAngle: Double = PI / 2) {
    fillLine(x1, y1, x2, y2, width)

    val dx = x2 - x1
    val dy = y2 - y1

    val length = sqrt(dx*dx + dy*dy)
    val unitX = dx / length
    val unitY = dy / length

    val baseX = x2 - unitX * headLength
    val baseY = y2 - unitY * headLength

    val sinAngle = sin(headAngle)
    val cosAngle = cos(headAngle)

    val wingX1 = cosAngle * unitX - sinAngle * unitY
    val wingY1 = sinAngle * unitX + cosAngle * unitY

    val wingX2 = cosAngle * unitX + sinAngle * unitY
    val wingY2 = -sinAngle * unitX + cosAngle * unitY

    val leftX = baseX + wingX1 * headLength
    val leftY = baseY + wingY1 * headLength

    val rightX = baseX + wingX2 * headLength
    val rightY = baseY + wingY2 * headLength

    fillLine(x2, y2, leftX, leftY, width)
    fillLine(x2, y2, rightX, rightY, width)
}