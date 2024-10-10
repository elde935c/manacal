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
      It is Player{" "}
      {gameState?.players[0].hasTurn
        ? gameState?.players[0].name
        : gameState?.players[1].name}{"s "}
      turn
      <br />
      <br />
      {gameState?.players[1].pits
        .slice(0)
        .reverse()
        .map((pit: Pit): ReactNode => {
          return (
            <button
              type="button"
              className={"btn btn-primary"}
              onClick={() => playPit(pit.index + 7)}
              key={pit.index + 7}
            >
              {pit.nrOfStones}
            </button>
          );
        })}
      <br />
      {gameState?.players[0].pits.map((pit: Pit): ReactNode => {
        return (
          <button
            type="button"
            className={"btn btn-primary"}
            onClick={() => playPit(pit.index)}
            key={pit.index}
          >
            {pit.nrOfStones}
          </button>
        );
      })}
    </div>
  );
};
