# Basic Chess GUI

This project aims to create a GUI in which to play chess that closely mimics the style of Lichess.

## Building

This project uses the gradle build system, with the wrapper.

To build the project

```
./gradlew build
```

To run the project
```
./gradlew run
```

## Current Known Bugs
-GUI doesn't register all clicks
-On the movement of some pieces, the GUI resizes the squares so they are not all the same size
## Goals
-Choose which piece you promote too (very important but not implemented yet)
-Pre-game screen where you can choose colors for the board and other QOL bits and bobs
-Have create new game, resume game, and play from FEN options
