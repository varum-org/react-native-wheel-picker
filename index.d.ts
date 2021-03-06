import React from 'react';
import { StyleProp, ViewStyle } from 'react-native';

interface IStyle {
  selectedItemTextColor?: string;
  selectedItemTextSize?: number;
  selectedItemTextFontFamily: string;
  itemTextColor?: string;
  itemTextSize?: number;
  itemTextFontFamily: string;
  indicatorColor?: string;
  hideIndicator?: boolean;
  indicatorWidth?: number;
  backgroundColor?: string;
  style?: StyleProp<ViewStyle>;
}

export interface IPropsWheelPicker extends IStyle {
  data: string[];
  isCyclic?: boolean;
  initPosition?: number;
  selectedItem?: number;
  onItemSelected?: (res: number) => void;
}

export class WheelPicker extends React.Component<IPropsWheelPicker> { }

export interface IPropsDatePicker extends IStyle {
  initDate: Date;
  onDateSelected: (date: Date) => void;
  maximumDate?: Date;
  minimumDate?: Date;
}

export class DatePicker extends React.Component<IPropsDatePicker> {}