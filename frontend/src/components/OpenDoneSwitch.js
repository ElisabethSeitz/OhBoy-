import React, { useState } from 'react';
import Switch from '@material-ui/core/Switch';
import styled, { css } from 'styled-components/macro';

export default function OpenDoneSwitch({
  handleOnClickDONE,
  handleOnClickOPEN,
  itemType,
}) {
  const [state, setState] = useState({
    checked: false,
  });

  const handleChange = (event) => {
    setState({ ...state, [event.target.name]: event.target.checked });
    state.checked === false ? handleOnClickDONE() : handleOnClickOPEN();
  };

  return (
    <OpenDoneSwitchStyled itemType={itemType}>
      <p className="open">open</p>
      <Switch
        checked={state.checked}
        onChange={handleChange}
        color="default"
        name="checked"
        inputProps={{ 'aria-label': 'primary checkbox' }}
      />
      <p className="done">done</p>
    </OpenDoneSwitchStyled>
  );
}

const OpenDoneSwitchStyled = styled.div`
  display: grid;
  grid-template-columns: 1fr min-content 1fr;
  justify-items: center;

  .open,
  .done {
    justify-self: right;
    margin: 8px 0 0 0;
    color: var(--blue-main);

    ${(props) =>
      props.itemType === 'reward' &&
      css`
        color: var(--green-main);
      `}
  }

  .done {
    justify-self: left;
  }
`;
