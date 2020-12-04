import React from 'react';
import { render } from '@testing-library/react';
import { MemoryRouter as Router } from 'react-router-dom';
import MonsterPage from './MonsterPage';

describe('component test :: MonsterPage', () => {
  it('renders a MonsterPage component and its contents', () => {
    //Given
    const { queryByRole } = render(
      <Router>
        <MonsterPage />
      </Router>
    );

    //When
    const monsterHeadLine = queryByRole('heading', { name: /OhKid!/i });

    //Then
    expect(monsterHeadLine).toBeInTheDocument();
  });
});
