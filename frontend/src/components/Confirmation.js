import React, { useState } from 'react';

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
        <button type="button" onClick={() => setConfirmed(true)}>
          {label}
        </button>
      );
    }
    if (confirmed === true) {
      return (
        <div>
          <p>{question}</p>
          <button type="button" onClick={(event) => onClick(event)}>
            Yes
          </button>
          <button type="button" onClick={() => setConfirmed(false)}>
            No
          </button>
        </div>
      );
    }
  }
}
