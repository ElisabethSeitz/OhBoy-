import React from 'react';
import { Link, useHistory } from 'react-router-dom';
import ListItem from '../components/ListItem';
import { GrEdit } from 'react-icons/gr';
import styled from 'styled-components/macro';

export default function Monster({ monster }) {
  const history = useHistory();

  return (
    <ListItem monster={true}>
      <ContentStyled onClick={handleClick}>
        <img src={monster.image} alt="monster" />
        <p>{monster.name}</p>
      </ContentStyled>
      <EditLink to={'/monsters/edit/' + monster.id}>
        <GrEdit />
      </EditLink>
    </ListItem>
  );

  function handleClick() {
    history.push('/monsters/' + monster.id + '/tasks');
  }
}

const ContentStyled = styled.div`
  display: grid;
  grid-template-columns: 1fr 1fr;
  justify-items: center;
  align-items: center;
  font-family: 'Glass Antiqua';
  font-size: var(--size-xl);
`;

const EditLink = styled(Link)`
  justify-self: end;
  padding: 10px;
`;
