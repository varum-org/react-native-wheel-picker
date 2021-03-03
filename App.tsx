import React from 'react';
import {
  Button,
  SafeAreaView,
  StatusBar,
  StyleSheet,
  Text,
  View,
} from 'react-native';

export interface Props {
  name: string;
  enthusiasmLevel?: number;
}

const Hello: React.FC<Props> = (props) => {
  const [count, setCount] = React.useState(0);

  const onIncrement = () => setCount(count + 1);
  const onDecrement = () => setCount(count - 1);

  return (
    <>
      <StatusBar barStyle="dark-content" />
      <SafeAreaView>
        <View style={styles.root}>
          <Text style={styles.greeting}>Count: {count}</Text>
          <View style={styles.buttons}>
            <View style={styles.button}>
              <Button
                title="-"
                onPress={onDecrement}
                accessibilityLabel="decrement"
                color="red"
              />
            </View>
            <View style={styles.button}>
              <Button
                title="+"
                onPress={onIncrement}
                accessibilityLabel="increment"
                color="blue"
              />
            </View>
          </View>
        </View>
      </SafeAreaView>
    </>
  );
};

const styles = StyleSheet.create({
  root: {
    alignItems: 'center',
    alignSelf: 'center',
  },
  buttons: {
    flexDirection: 'row',
    minHeight: 70,
    alignItems: 'stretch',
    alignSelf: 'center',
    borderWidth: 5,
  },
  button: {
    flex: 1,
    paddingVertical: 0,
  },
  greeting: {
    color: '#999',
    fontWeight: 'bold',
  },
});

export default Hello;
