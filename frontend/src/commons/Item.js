import React from 'react';
import { useHistory } from 'react-router-dom';
import ListItem from '../components/ListItem';
import { BsStarFill } from 'react-icons/bs';
import styled, { css } from 'styled-components/macro';
import ToggleButton from '../components/ToggleButton';

export default function Item({ itemType, monsterId, editStatus, item }) {
  const history = useHistory();

  function handleEdit() {
    const statusOpen = item.status === 'OPEN';
    if (statusOpen && itemType === 'task') {
      return history.push('/monsters/' + monsterId + '/tasks/edit/' + item.id);
    }
    if (statusOpen && itemType === 'reward') {
      return history.push(
        '/monsters/' + monsterId + '/rewards/edit/' + item.id
      );
    }
  }

  return (
    <ListItem itemType={itemType}>
      <ContentStyled onClick={handleEdit} itemType={itemType}>
        <p className="itemDescription">{item.description}</p>
        <p className="itemScore">
          <BsStarFillStyled />
          {' ' + item.score}
        </p>
      </ContentStyled>
      <ToggleButton
        status={item.status}
        onClick={handleClick}
        itemType={itemType}
      />
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

  .itemDescription {
    margin: 0;
    padding: var(--size-m);
    border-radius: var(--size-s) 0 0 0;
  }

  .itemScore {
    margin: 0;
    padding: var(--size-xs) var(--size-m);
    border-radius: 0 0 0 var(--size-s);
    background-color: var(--blue-main);
    opacity: 0.7;
    color: white;

    ${(props) =>
      props.itemType === 'reward' &&
      css`
        background-color: var(--green-main);
      `}
  }
`;

const BsStarFillStyled = styled(BsStarFill)`
  height: var(--size-m);
  color: white;
`;
