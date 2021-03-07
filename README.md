# react-native-wheel-picker-android
<p>
<img src="http://img.shields.io/npm/v/@duynn110399/react-native-wheel-picker-android.svg" />
<img src="https://img.shields.io/npm/dm/@duynn110399/react-native-wheel-picker-android.svg" />
<img src="https://img.shields.io/npm/dt/@duynn110399/react-native-wheel-picker-android.svg" />
</p>

A simple Wheel Picker for Android

## Installation

npm:
`npm i @duynn100198/react-native-wheel-picker-android`
yarn:
`yarn add @duynn100198/react-native-wheel-picker-android`

# Usage

```tsx
import {
  WheelPicker
} from "@duynn100198/react-native-wheel-picker-android";
import React, { useState } from "react";

const wheelPickerData = [
  "sunday",
  "monday",
  "tuesday",
  "wednesday",
  "thursday",
  "friday"
];

const MyPicker: React.FC = () => {
    const [selectedItem,setSelectedItem] = useState(0)

  const onPress = () => {
    setSelectedItem(3)
  };

  render() {
    return (
      <View style={styles.container}>
        <Button title={"Select third element"} onPress={this.onPress} />
        <Text>Selected position: {selectedItem}</Text>
        <WheelPicker
          selectedItem={selectedItem}
          data={wheelPickerData}
          onItemSelected={setSelectedItem}
        />
      </View>
    );
  }
}

export default MyPicker;
```

## Questions or suggestions?

Feel free to [open an issue](https://github.com/VTNPlusD/react-native-wheel-picker/issues)