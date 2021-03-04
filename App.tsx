import React from 'react';
import {
} from 'react-native';
import {WheelPicker} from './WheelPicker';

export interface Props {
  name: string;
  enthusiasmLevel?: number;
}

const Hello: React.FC<Props> = (props) => {
  return (
    <WheelPicker
      data={[
        '1',
        '2',
        '3',
        '4',
        '5',
        '3',
        '6',
        '7']}
      onItemSelected={(position) => {
        console.log(position);
        
      }}
      selectedItemTextFontFamily=""
      itemTextFontFamily=""
    />
  );
};

export default Hello;
