interface PropsPit {
  numStones?: number;
  pitKey: number;
}

const PitButton = ({ numStones, pitKey }: PropsPit) => {
  return (
    <button type="button"
     className={"btn btn-primary"}
      key={pitKey}>
      {String(numStones)}
      
    </button>
  );
};

export default PitButton;
