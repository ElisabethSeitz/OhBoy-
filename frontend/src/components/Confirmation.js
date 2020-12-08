import React, { useState } from 'react';
import Button from './Button';
import styled, { css } from 'styled-components/macro';

export default function Confirmation({ question, label, onClick }) {
  const [confirmed, setConfirmed] = useState(false);

  return (
    <>
      <DisplayButtonOrConfirmation />
    </>
  );

  function DisplayButtonOrConfirmation() {
    if (confirmed === false) {
      return (
        <Button name="delete" type="button" onClick={() => setConfirmed(true)}>
          {label}
        </Button>
      );
    }
    if (confirmed === true) {
      return (
        <ConfirmationSection>
          <p className="question">{question}</p>
          <button
            className="yes"
            type="button"
            onClick={(event) => onClick(event)}
          >
            Yes
          </button>
          <button
            className="no"
            type="button"
            onClick={() => setConfirmed(false)}
          >
            No
          </button>
        </ConfirmationSection>
      );
    }
  }
}

const ConfirmationSection = styled.section`
  display: grid;
  grid-template-rows: min-content min-content;
  grid-template-columns: 1fr 1fr;
  padding: 0 var(--size-xxl);
  text-align: center;

  .question {
    grid-row: 1;
    grid-column: span 2;
  }

  .yes,
  .no {
    grid-column: 1;
    grid-row: 2;
    width: 50px;
    height: var(--size-xxl);
    justify-self: right;
    border-radius: var(--size-s);
    background-color: rgba(209, 48, 22, 0.7);
    border: var(--orange-border);
    color: white;
    margin: 0 var(--size-s);
  }

  .no {
    grid-column: 2;
    grid-row: 2;
    justify-self: left;
    background-color: rgba(175, 169, 169, 0.7);
    border: var(--grey-border);
  }
`;
