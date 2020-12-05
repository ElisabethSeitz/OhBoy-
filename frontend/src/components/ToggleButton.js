import React from 'react';
import CheckIcon from '@material-ui/icons/Check';
import ReplayIcon from '@material-ui/icons/Replay';
import styled, { css } from 'styled-components/macro';

export default function ToggleButton({ status, ...rest }) {
  const setIcon = status === 'OPEN' ? <CheckIcon /> : <ReplayIcon />;

  return <ToggleButtonStyled {...rest}>{setIcon}</ToggleButtonStyled>;
}

const ToggleButtonStyled = styled.button`
  border-radius: 0 var(--size-s) var(--size-s) 0;
  border: none;
  border-left: var(--blue-border);
  padding: 0;
  background-color: white;
  opacity: 0.6;

  ${(props) =>
    props.itemType === 'reward' &&
    css`
      border-left: var(--green-border);
    `}
`;
