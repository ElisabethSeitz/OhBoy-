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
  padding-bottom: var(--size-l);

  .open {
    justify-self: right;
    background-color: var(--grey-background);
    border: solid var(--grey-font) 0.1em;
    border-radius: 5px 0 0 5px;
    width: 6em;
    height: 2em;
  }

  .done {
    justify-self: left;
    background-color: var(--grey-background);
    border: solid var(--grey-font) 0.1em;
    border-radius: 0 5px 5px 0;
    width: 6em;
    height: 2em;
  }
`;
