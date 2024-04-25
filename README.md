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
- En passant can screw with the programs ability to recognize a move would put the king in check
- Doesn't do promotion
- Doesn't recognize game end
## Goals
- Pre-game screen where you can choose colors for the board and other QOL bits and bobs
- Have create new game, resume game, and play from FEN options
