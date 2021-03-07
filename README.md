# react-native-wheel-picker-android
<p>
<img src="https://img.shields.io/npm/v/@duynn100198/react-native-wheel-picker-android.svg" />
<img src="https://img.shields.io/npm/dm/@duynn100198/react-native-wheel-picker-android.svg" />
<img src="https://img.shields.io/npm/dt/@duynn100198/react-native-wheel-picker-android.svg" />
</p>

A simple Wheel Picker for Android

## Installation

npm:
`npm i @duynn100198/react-native-wheel-picker-android`

yarn:
`yarn add @duynn100198/react-native-wheel-picker-android`

# Usage

```tsx
import { WheelPicker } from "@duynn100198/react-native-wheel-picker-android";
import React, { useState } from "react";
import { Button, View, Text, StyleSheet } from "react-native";

const wheelPickerData = [
    "sunday",
    "monday",
    "tuesday",
    "wednesday",
    "thursday",
    "friday"
];

const MyPicker: React.FC = () => {
    const [selectedItem, setSelectedItem] = useState(0)

    const onPress = () => {
        setSelectedItem(3)
    };

    return (
        <View style={styles.container}>
            <Button title={"Select third element"} onPress={onPress} />
            <Text>Selected position: {selectedItem}</Text>
            <WheelPicker
                selectedItem={selectedItem}
                data={wheelPickerData}
                onItemSelected={setSelectedItem}
            />
        </View>
    );
}
const styles = StyleSheet.create({
    container: {
        alignItems: 'center'
    }
});
export default MyPicker;
```
![](./src/assets/pickerAndroid.gif)

## Questions or suggestions?

Feel free to [open an issue](https://github.com/VTNPlusD/react-native-wheel-picker/issues)