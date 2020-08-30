# RunnerVSHunter

Inspired by Dream's SpeedRunner VS Hunter series.
Note : This was made in Java 14

## Commands
```yaml
commands:
  setPrepareTime:
    description: Set the prepare time for the runner
    aliases: [ preparetime ]
    permission: rvsh.setPrepareTime
    usage: |
      /removerunner [Time In Second]
      /preparetime [Time In Second]
  setDeathMode:
    description: Set the death mode for the runner
    aliases: [ deathmode ]
    permission: rvsh.setPrepareTime
    usage: |
      /setdeathmode [Death Mode]
      /deathmode [Death Mode]
    # Death mode :
    # END_GAME - If one runner died, the game end
    # SPECTATOR - The runner that died, goes into spectator
    # SWITCH_TO_HUNTER - The runner that died, switch to hunter
  removeHunter:
    description: Remove someone from hunter team
    permission: rvsh.removeHunter
    usage: |
      /removehunter [List] [Of] [Player]
  removeRunner:
    description: Remove someone from runner team
    permission: rvsh.removeRunner
    usage: |
      /removerunner [List] [Of] [Player]
  addHunter:
    description: Add someone to the hunter team
    aliases: [ sethunter ]
    permission: rvsh.addHunter
    usage: |
      /addhunter [List] [Of] [Player]
      /sethunter [List] [Of] [Player]
  addRunner:
    description: Remove someone from runner team
    permission: rvsh.addRunner
    aliases: [ setrunner ]
    usage: |
      /addrunner [List] [Of] [Player]
      /setrunner [List] [Of] [Player]
  startGame:
    description: Start the Runner VS Hunter game
    permission: rvsh.startGame
    usage: |
      /startgame
  stopGame:
    description: Stop the Runner VS Hunter Game
    permission: rvsh.stopGame
    usage: |
      /stopgame
```