import React from 'react';
import { requireNativeComponent, Platform, View } from 'react-native';

const WheelPickerView = Platform.select({
  ios: View,
  android: requireNativeComponent<Props>('WheelPicker') as any,
});

type Props = {
  onChange?: (event: any) => void;
  data: Array<string>;
  isCyclic?: boolean;
  selectedItemTextColor?: string;
  selectedItemTextSize?: number;
  indicatorWidth?: number;
  hideIndicator?: boolean;
  indicatorColor?: string;
  itemTextColor?: string;
  itemTextSize?: number;
  selectedItem?: number;
  backgroundColor?: string;
  onItemSelected?: (position: number) => void;
  style?: any;
};

const WheelPicker: React.FC<Props> = (props) => {
  const { onItemSelected } = props;

  const handleItemSelected = (event: any) => {
    if (onItemSelected) {
      onItemSelected(event.nativeEvent.position);
    }
  };

  return <WheelPickerView {...props} onChange={handleItemSelected} />;
};

WheelPicker.defaultProps = {
  style: {
    width: 200,
    height: 150,
  },
};

export default WheelPicker;
