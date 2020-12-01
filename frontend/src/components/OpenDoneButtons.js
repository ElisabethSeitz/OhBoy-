import React from 'react';
import styled from 'styled-components/macro';

export default function OpenDoneButtons({
  handleOnClickOPEN,
  handleOnClickDONE,
}) {
  return (
    <OpenDoneButtonsStyled>
      <button className="open" onClick={handleOnClickOPEN}>
        open
      </button>
      <button className="done" onClick={handleOnClickDONE}>
        done
      </button>
    </OpenDoneButtonsStyled>
  );
}

const OpenDoneButtonsStyled = styled.div`
  display: grid;
  grid-template-columns: 1fr 1fr;

  .open {
    justify-self: right;
    background-color: var(--grey-background);
    border: solid var(--grey-font) 0.1em;
    border-radius: 5px 0 0 5px;
  }

  .done {
    justify-self: left;
    background-color: var(--grey-background);
    border: solid var(--grey-font) 0.1em;
    border-radius: 0 5px 5px 0;
  }
`;
