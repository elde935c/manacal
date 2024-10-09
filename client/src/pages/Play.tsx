import { useMancalaGame } from "../contexts/MancalaGameContext";
import { playGame } from "../services/api";
import { isGameState, Pit} from "../types";

export const Play = () => {
    const { gameState, setGameState } = useMancalaGame();

//     const pits = Player.pits;

    const onSubmit = async () => {
        const result = await playGame(0);

        if (isGameState(result)) {
            setGameState(result);
        } else {
            setAlert(`${result.statusCode} ${result.statusText}`);
        }
    }

    const buttonLabels = ['1', '2', '3']; // Array of labels

    return <div>
        Player 1: {gameState?.players[0].name}<br />
        Player 2: {gameState?.players[1].name}
        <div>
              {buttonLabels.map((label, index) => (
                <button type="button"
                  key={index}
                  label={label}
                  onClick={() => onSubmit()}
                >
                {label}
                </button>
              ))}
            </div>
    </div>
};