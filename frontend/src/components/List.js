import React from 'react';
import styled from 'styled-components/macro';

export default function List({ children }) {
  return <ListStyled>{children}</ListStyled>;
}

const ListStyled = styled.ul`
  overflow: scroll;
  margin: 0;
  padding: 0 var(--size-xl);

  list-style: none;

  display: grid;
  grid-auto-rows: min-content;
  gap: var(--size-l);

  li:last-child:after {
    content: '';
    display: block;
    height: 56px;
  }
`;
