import React from 'react';

const Star = ({ isFavorite, onClick }) => {
  const starClassName = isFavorite ? 'star favorite' : 'star';

  return (
    <span className={starClassName} onClick={onClick}>
      ★
    </span>
  );
};

export default Star;
