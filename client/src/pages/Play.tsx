import { useMancalaGame } from "../contexts/MancalaGameContext";
import { playGame } from "../services/api";
import { isGameState, Pit } from "../types";

// import PitButton from "../components/PitButton";
import { ReactNode } from "react";

export const Play = () => {
  const { gameState, setGameState } = useMancalaGame();

  const playPit = async (index: number) => {
    const result = await playGame(index);

    if (isGameState(result)) {
      setGameState(result);
    }
  };

  return (
    <div>
      Player 1: {gameState?.players[0].name} <br />
      Player 2: {gameState?.players[1].name} <br />
      {gameState?.gameStatus.endOfGame ? (
        <div> Player {gameState?.gameStatus.winner} has won </div>
      ) : (
        <div>
          {" "}
          Player{" "}
          {gameState?.players[0].hasTurn
            ? gameState?.players[0].name
            : gameState?.players[1].name}{" "}
          has turn{" "}
        </div>
      )}
      <br />
      <br />
      {gameState?.players[1].pits
        .slice(0)
        .reverse()
        .map((pit: Pit): ReactNode => {
          if (pit.index % 7 !== 6)
            return (
              <button
                type="button"
                className={"btn btn-primary"}
                disabled={!gameState?.players[1].hasTurn}
                onClick={() => playPit(pit.index + 7)}
                key={pit.index + 7}
              >
                {pit.nrOfStones}
              </button>
            );
          else return null;
        })}
      <br />
      <button
        type="button"
        className={"btn btn-secondary"}
        disabled={true}
        onClick={() => playPit(13)}
        key={13}
      >
        {gameState?.players[1].pits[6].nrOfStones}
      </button>
      <br />
      <button
        type="button"
        className={"btn btn-secondary"}
        disabled={true}
        onClick={() => playPit(6)}
        key={6}
      >
        {gameState?.players[0].pits[6].nrOfStones}
      </button>
      <br />
      {gameState?.players[0].pits.map((pit: Pit): ReactNode => {
        if (pit.index % 7 !== 6)
          return (
            <button
              type="button"
              className={"btn btn-primary"}
              disabled={!gameState?.players[0].hasTurn}
              onClick={() => playPit(pit.index)}
              key={pit.index}
            >
              {pit.nrOfStones}
            </button>
          );
        else return null;
      })}
    </div>
  );
};
