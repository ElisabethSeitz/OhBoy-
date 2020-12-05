import React from 'react';
import { useHistory } from 'react-router-dom';
import ListItem from '../components/ListItem';
import { BsStarFill } from 'react-icons/bs';
import styled from 'styled-components/macro';
import ToggleButton from '../components/ToggleButton';

export default function Item({ itemType, monsterId, editStatus, item }) {
  const history = useHistory();

  function handleEdit() {
    const statusOpen = item.status === 'OPEN';
    return statusOpen && itemType === 'task'
      ? history.push('/monsters/' + monsterId + '/tasks/edit/' + item.id)
      : history.push('/monsters/' + monsterId + '/rewards/edit/' + item.id);
  }

  return (
    <ListItem>
      <ContentStyled onClick={handleEdit}>
        <p className="taskDescription">{item.description}</p>
        <p className="taskScore">
          <BsStarFillStyled />
          {' ' + item.score}
        </p>
      </ContentStyled>
      <ToggleButton status={item.status} onClick={handleClick} />
    </ListItem>
  );

  function handleClick() {
    editStatus(item.id);
  }
}

const ContentStyled = styled.div`
  display: grid;
  grid-template-rows: min-content min-content;
  margin: 2px 2px 2px 2px;
  background-color: rgba(255, 255, 255, 0.6);

  .taskDescription {
    margin: 0;
    padding: var(--size-m);
    border-radius: var(--size-s) 0 0 0;
  }

  .taskScore {
    margin: 0;
    padding: var(--size-xs) var(--size-m);
    border-radius: 0 0 0 var(--size-s);
    background-color: var(--blue-main);
    opacity: 0.7;
    color: white;
  }
`;

const BsStarFillStyled = styled(BsStarFill)`
  height: var(--size-m);
  color: white;
`;
