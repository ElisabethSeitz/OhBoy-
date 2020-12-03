import React from 'react';
import styled from 'styled-components/macro';
import AddButton from './AddButton';
import { useHistory } from 'react-router-dom';

export default function Footer({ task, monster, add, currentMonsterId }) {
  const history = useHistory();

  return (
    <FooterStyled>
      <DisplayAdd />
      <div className="footer" />
    </FooterStyled>
  );

  function DisplayAdd() {
    if (add) {
      return <AddButton onClick={createAddLink(currentMonsterId)} />;
    }
    return <div></div>;
  }

  function createAddLink(monsterId) {
    if (monster) {
      return () => history.push('/monsters/create');
    }
    if (task) {
      return () => history.push('/monsters/' + monsterId + '/tasks/create');
    } else
      return () => history.push('/monsters/' + monsterId + '/rewards/create');
  }
}

const FooterStyled = styled.div`
  .footer {
    grid-row: 2;
    background-color: #f3f2f0;
    height: 40px;
    border-top: var(--blue-border);
  }
`;
