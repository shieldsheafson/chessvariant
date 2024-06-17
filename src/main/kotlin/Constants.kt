package chessui.src.main.kotlin

const val SQUARESPERSIDE = 7
const val TOTALSQUARES = SQUARESPERSIDE * SQUARESPERSIDE

const val UP = -1 * SQUARESPERSIDE
const val DOWN = SQUARESPERSIDE
const val LEFT = -1
const val RIGHT = 1
const val UPLEFT = -1 * (SQUARESPERSIDE + 1)
const val UPRIGHT = -1 * (SQUARESPERSIDE - 1)
const val DOWNRIGHT = SQUARESPERSIDE + 1
const val DOWNLEFT = SQUARESPERSIDE - 1

val DIRECTIONS = listOf(UP, UPRIGHT, RIGHT, DOWNRIGHT, DOWN, DOWNLEFT, LEFT, UPLEFT)

const val WHITE = true
const val BLACK = false
